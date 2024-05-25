package application.services;

import domain.User;
import infrastructure.db.Database;

public class TransactionService {
    private Database database;

    public TransactionService(Database database) {
        this.database = database;
    }

    public void printTransactions(User owner) {
        database.printTransactions(owner);
    }
}
