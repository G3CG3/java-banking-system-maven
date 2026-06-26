# Technical Notes

## Future Improvements

### Account

- Protect transaction history from external modification.
- Revisit transfer implementation after Spring Boot service layer is introduced.
- Evaluate extracting common reporting logic (`getTotalByType`) if reporting methods continue to grow.
- Return defensive copies of transaction collections instead of exposing internal mutable state.

### Bank

- Consider Map<Integer, Account> instead of List<Account>

### Transaction

- Replace static id generation when database is introduced