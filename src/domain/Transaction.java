package domain;

import java.io.Serializable;

public class Transaction implements Serializable {
    private final User owner;
    private User reciever;
    private int amount;
    private boolean isCompleted;
    private Account account;

    public Transaction(User owner, User reciever, int amount, Account account) {
        this.owner = owner;
        this.reciever = reciever;
        this.amount = amount;
        this.account = account;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "owner=" + owner +
                ", reciever=" + reciever +
                ", amount=" + amount +
                ", isCompleted=" + isCompleted +
                ", account=" + account +
                '}';
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }


    public User getOwner() {
        return owner;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Размер траназкции не может быть отрицательным");

        this.amount = amount;
    }

    public  Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }
}
