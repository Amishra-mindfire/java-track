# Java Track

Practice programs from my Java learning track: small examples of core language
features, plus a console-based **Smart Banking System** assignment.

## Contents

| Folder | Topic | What it shows |
|---|---|---|
| `src/Oops/Inheritance` | Inheritance | `Vehicle` base class extended by `Car` and `Bike` |
| `src/Oops/Abstraction` | Abstraction | `PaymentService` interface (Credit / UPI) and the abstract `BaseSwitch` class |
| `src/Collections` | Collections | `ArrayList`, `HashSet` and other collection basics |
| `src/Generics` | Generics | A generic `BaseError<T>` wrapper used with different data types |
| `src/LambdasAndStreams` | Lambdas & Streams | `forEach`, `map`, `filter` and other stream operations |
| `src/Concurrency` | Concurrency | Two `Runnable` tasks running on separate threads |
| `src/SmartBankingSystem` | Assignment | Console banking app (details below) |

## Smart Banking System

A menu-driven console app for managing customers, accounts and transactions.
All data is kept in memory.

### Features
- Create customers and open accounts
- Make an initial deposit to activate an account, then deposit, withdraw and transfer
- Check balance and view transaction history (failed attempts are recorded too)
- View all customers

### Business rules
- **Minimum balance:** INR 2,000. The initial deposit must be at least this amount,
  and a withdrawal or transfer can't take the balance below it.
- **Account lifecycle:** a new account starts as `PENDING_INITIAL_DEPOSIT` and becomes
  `ACTIVE` after the initial deposit. Only active accounts can transact.
- **Transaction limit:** INR 10,00,000 per transaction; amounts allow up to 2 decimal places.
- **Unique email** per customer; transfers to the same account are rejected.
- **Generated IDs:** customers `CUST0001`, accounts `SB00000001`, transactions `TXN000001`.

All limits and formats live in `util/BankingConstants.java`.

### Structure
```
SmartBankingSystem/
├── Main.java          console menu and output only
├── controller/        BankingController: checks input format, then calls the service
├── validation/        InputValidator: name, email, ID and amount format checks
├── service/           SmartBankingSystem interface + implementation (business rules)
├── entity/            Customer, Account, Transaction
├── enums/             AccountStatus, TransactionType, TransactionStatus
├── exception/         BankingException and its specific subclasses
└── util/              BankingConstants, CurrencyFormatter
```

## Running

**Requirements**
- Smart Banking System: JDK 17 or later.
- The other examples print with `IO.println`, which needs **JDK 25 or later**.

**In VS Code:** open this folder with the Java extensions installed, open any
`Main.java` and click **Run**. Sources are in `src`, and compiled classes go to `bin`
(not committed).

**From the command line** (run from the repository root):

```powershell
javac -d bin (Get-ChildItem -Recurse src/SmartBankingSystem -Filter *.java).FullName
java -cp bin SmartBankingSystem.Main
```

To run another example, compile its folder the same way and use its package name,
for example `java -cp bin Oops.Inheritance.Main`.
