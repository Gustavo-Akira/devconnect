# ADR-0002: Apply Strangler Pattern for Authentication Migration

## Status
Accepted

## Context
The system contains existing production data where authentication information lives partially in `dev_profile_entity`
and partially (or not at all) in the new `users` table.

A full cut-over would risk breaking existing consumers and legacy data access paths.

## Decision
Adopt the **Strangler Pattern** to migrate authentication concerns incrementally.

The strategy consists of:
1. Introducing the `User` domain and persistence without breaking existing flows
2. Adding fallback logic where necessary (login, reads)
3. Introducing database migration scripts to backfill `users` from `dev_profile_entity`
4. Gradually removing fallbacks once data consistency is guaranteed
5. Finally removing legacy fields from `DevProfile`

No breaking API changes are introduced until consumers are fully migrated.

## Consequences
### Positive
- Zero-downtime migration
- Backward compatibility preserved
- Clear checkpoints to remove legacy logic

### Negative
- Temporary increase in complexity
- Short-lived technical duplication

## Notes
Fallback logic and transitional code are explicitly considered **temporary** and removed once migration completes.
