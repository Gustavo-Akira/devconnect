# ADR-0001: Separate Authentication Concerns from DevProfile Domain

## Status
Accepted

## Context
Historically, the `DevProfile` domain contained authentication-related fields such as `email`, `password`, and `isActive`,
and also inherited from a shared persistence superclass used by `User`.

This coupling caused several issues:
- Tight coupling between profile and authentication concerns
- Inconsistent lifecycle management (e.g. active profile with inactive user)
- Persistence constraints forcing domain workarounds (nulls, fallbacks)
- Difficulty evolving authentication independently from profile data

As part of an architectural evolution, authentication responsibilities are being migrated to a dedicated `User` domain and table.

## Decision
Authentication concerns (email, password, activation status) are removed from the `DevProfile` domain and persistence model.
A dedicated `User` aggregate becomes the single source of truth for authentication and account lifecycle.

`DevProfile` now references `User` only via `userId`.

The API composition of `DevProfile + User` is handled at the controller layer via DTOs, preserving backward compatibility for consumers.

## Consequences
### Positive
- Clear separation of concerns between Profile and Authentication
- Simplified domain invariants
- Cleaner persistence model without inheritance misuse
- Enables independent evolution of authentication features

### Negative
- Requires temporary orchestration at controller level
- Migration effort required for existing data

## Notes
This decision enables future removal of legacy fallbacks and simplifies authorization and deletion flows.
