# Technical Notes

## Future Improvements

### Account

- Revisit the Account API after introducing the Spring Boot service layer.
- Evaluate extracting common reporting logic if transaction analysis continues to grow.

---

### Bank

- Consider replacing `List<Account>` with `Map<Integer, Account>` for faster account lookups.

---

### Transaction

- Replace static ID generation after database integration.

---

### Architecture

- Extract transfer logic into a dedicated `TransferService`.
- Review service responsibilities after Spring Boot migration.

### Validation

- Consider rejecting `Double.NaN` and infinite values for monetary operations.
- Consider validating that `Bank.addAccount()` rejects null accounts with a meaningful exception.