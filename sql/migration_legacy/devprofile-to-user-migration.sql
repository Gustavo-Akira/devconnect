/*
------------------------------------------------------------------------------
 Migration: Backfill users table from dev_profile_entity
 Author: Gustavo Akira
 Context:
   - Legacy auth data still exists in dev_profile_entity
   - New auth source of truth is the users table
   - This script backfills missing users and links dev_profile_entity.user_id
   - No columns are deleted
   - Script is idempotent and safe to run multiple times

 Tables involved:
   - users
   - dev_profile_entity

 IMPORTANT:
   - This script must be executed BEFORE removing legacy fallback code
   - It does NOT remove legacy columns
------------------------------------------------------------------------------
*/

BEGIN;

-- ============================================================================
-- STEP 0 - AUDIT: how many profiles still don't have a valid user
-- ============================================================================

-- Expected BEFORE migration: value > 0
-- Expected AFTER migration: value = 0
SELECT COUNT(*) AS profiles_without_user
FROM dev_profile_entity dp
LEFT JOIN users u ON u.id = dp.user_id
WHERE u.id IS NULL;


-- ============================================================================
-- STEP 1 - SAFETY CHECK: duplicated emails in dev_profile_entity
-- ============================================================================

-- If this query returns rows, STOP the migration and resolve manually
SELECT email, COUNT(*) AS occurrences
FROM dev_profile_entity
WHERE email IS NOT NULL
GROUP BY email
HAVING COUNT(*) > 1;


-- ============================================================================
-- STEP 2 - CREATE USERS FOR LEGACY PROFILES (IDEMPOTENT)
-- ============================================================================

INSERT INTO users (email, password, is_active)
SELECT
    dp.email,
    dp.password,
    dp.is_active
FROM dev_profile_entity dp
LEFT JOIN users u ON u.email = dp.email
WHERE u.id IS NULL
  AND dp.email IS NOT NULL
ON CONFLICT (email) DO NOTHING;


-- ============================================================================
-- STEP 3 - LINK DEV PROFILES TO USERS
-- ============================================================================

UPDATE dev_profile_entity dp
SET user_id = u.id
FROM users u
WHERE dp.user_id IS NULL
  AND dp.email = u.email;


-- ============================================================================
-- STEP 4 - POST-MIGRATION VALIDATION
-- ============================================================================

-- Expected result: 0
SELECT COUNT(*) AS still_missing_user
FROM dev_profile_entity dp
LEFT JOIN users u ON u.id = dp.user_id
WHERE u.id IS NULL;


-- ============================================================================
-- STEP 5 - SAMPLE DATA CHECK (OPTIONAL)
-- ============================================================================

SELECT
    dp.id      AS dev_profile_id,
    dp.email   AS profile_email,
    dp.user_id AS user_id,
    u.email    AS user_email
FROM dev_profile_entity dp
JOIN users u ON u.id = dp.user_id
LIMIT 20;

COMMIT;

/*
------------------------------------------------------------------------------
 ROLLBACK (MANUAL, IF EVER NEEDED):

   UPDATE dev_profile_entity
   SET user_id = NULL;

   -- Users can be removed only if necessary and with caution
   -- DELETE FROM users WHERE email IN (SELECT email FROM dev_profile_entity);

------------------------------------------------------------------------------
*/
