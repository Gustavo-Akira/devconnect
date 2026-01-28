# ADR-0003: Unified User Deactivation Strategy

## Status
Accepted

## Context
Previously, the system allowed:
- A `DevProfile` to be deactivated independently
- Authentication status to be inferred or duplicated

This led to inconsistent states, such as:
- Active users with inactive profiles
- Inactive users with active profiles

Additionally, deleting or disabling a profile had implicit authentication consequences.

## Decision
User deactivation is centralized in the `User` domain.

A new `DisableUserUseCase` is introduced, and legacy `DELETE /dev-profiles/{id}` behavior is redirected internally
to disable the associated user.

The endpoint is marked as deprecated and planned to be replaced by `DELETE /users/me`.

## Consequences
### Positive
- Single source of truth for account lifecycle
- Consistent authorization behavior
- Simplified business rules

### Negative
- Transitional routing logic in controller layer
- Temporary coexistence of deprecated endpoints

## Notes
This decision aligns lifecycle management with authentication ownership and prepares the system for endpoint deprecation.
