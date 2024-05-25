package application.services;

import domain.Account;
import domain.Transaction;
import domain.User;
import infrastructure.db.Database;

public class AccountService {
    private Database database;

    public AccountService(Database database) {
        this.database = database;
    }

    public boolean hasAnyAccounts(User user) {
        return database.hasUserAnAccount(user);
    }

    public void createAccount(User user) {
        Account account = new Account(user, 0, true, false);

        database.addAccount(account);
    }

    public void addMoney(User user, int amount) {
        Account account = database.getAccount(user);

        account.setBalance(
                account.getBalance() + amount
        );
    }

    public boolean transferMoney(User owner, User receiver, int amount) {
        Account ownerAccount = database.getAccount(owner);

        if (ownerAccount.getBalance() < amount) {
            throw new IllegalArgumentException("У вас не хватает денег");
        }

        Transaction newTransaction = new Transaction(owner, receiver, amount, ownerAccount);
        database.addTransaction(newTransaction);

        ownerAccount.setBalance(ownerAccount.getBalance() - amount);

        Account receiverAccount = database.getAccount(receiver);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        newTransaction.setCompleted(true);

        return true;
    }
}
