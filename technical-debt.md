# Technical Notes

## Future Improvements

### Account

- Revisit transfer implementation after Spring Boot service layer is introduced.
- Evaluate extracting common reporting logic (`getTotalByType`) if reporting methods continue to grow.

### Bank

- Consider Map<Integer, Account> instead of List<Account>

### Transaction

- Replace static id generation when database is introduced