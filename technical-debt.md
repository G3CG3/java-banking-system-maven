# Technical Notes

## Future Improvements

### Account

#### Design
- Protect transaction history from external modification.

#### Refactoring Opportunities
- Evaluate extracting common reporting logic (`getTotalByType`) if reporting methods continue to grow.

### Bank

- Consider Map<Integer, Account> instead of List<Account>

### Transaction

- Replace static id generation when database is introduced