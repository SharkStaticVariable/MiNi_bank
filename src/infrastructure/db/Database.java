package infrastructure.db;

import domain.Account;
import domain.Transaction;
import domain.User;
import lib.ArrayListCustom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Database implements Serializable {
    private ArrayListCustom<User> users;
    private ArrayListCustom<Transaction> transactions;
    private ArrayListCustom<Account> accounts;

    public void setUsers(ArrayListCustom<User> users) {
        this.users = users;
    }

    public void setTransactions(ArrayListCustom<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setAccounts(ArrayListCustom<Account> accounts) {
        this.accounts = accounts;
    }

    public Database() {
        users = new ArrayListCustom<>(10);
        accounts = new ArrayListCustom<>(10);
        transactions = new ArrayListCustom<>(1000);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        saveDB();
    }

    public void saveDB() {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream("db.serialization")
                )
        ) {
            oos.writeObject(users);
            oos.writeObject(transactions);
            oos.writeObject(accounts);
        } catch (IOException exception) {
            System.out.println("Не удалось сохранить данные " + exception.getMessage());
        }
    }

    public void addUser(User user) {
        /*for (User u : users) */

        for (int i = 0; i < users.getSize(); i++) {
            if (
                    user.equals(
                            users.get(i)
                    )
            ) {
                throw new IllegalArgumentException("Такой пользователь уже есть");
            }
        }

        users.add(user);
        saveDB();
    }

    public void printUsers() {
        for (int i = 0; i < users.getSize(); i++) {
            System.out.println(users.get(i));
        }


    }

    public User getUser(String fullName) throws IllegalArgumentException {
        for (int i = 0; i < users.getSize(); i++) {
            if (users
                    .get(i)
                    .getFullName()
                    .equals(fullName)
            ) {
                return users.get(i);
            }
        }

        throw new IllegalArgumentException("Такого пользователя не существует");
    }

    public Account getAccount(User user) throws IllegalArgumentException {
        for (int i = 0; i < accounts.getSize(); i++) {
            if (
                    accounts.get(i)
                            .getOwner()
                            .equals(user)
            ) {
                return accounts.get(i);
            }
        }

        throw new IllegalArgumentException("Такого аккаунта не существует");
    }

    public boolean isUserExists(String fullName) {
        for (int i = 0; i < users.getSize(); i++) {
            if (users
                    .get(i)
                    .getFullName()
                    .equals(fullName)
            ) {
                return true;
            }
        }

        return false;
    }

    public boolean hasUserAnAccount(User user) {
        for (int i = 0; i < accounts.getSize(); i++) {
            if (
                    accounts
                            .get(i)
                            .getOwner()
                            .equals(user)
            ) {
                return true;
            }
        }
        return false;
    }

    public void addTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
        saveDB();

    }

    public void printTransactions(User owner) {
        for (int i = 0; i < transactions.getSize(); i++) {
            if (transactions.get(i)
                    .getOwner().equals(owner)) {
                System.out.println(transactions.get(i));
            }
        }

    }
}
