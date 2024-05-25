package domain;

import java.io.Serializable;

public class Account implements Serializable {
    private User owner;
    private int balance;
    private boolean isActive;
    private boolean isLocked;

    public Account(User owner, int balance, boolean isActive, boolean isLocked) {
        this.owner = owner;
        this.balance = balance;
        this.isActive = isActive;
        this.isLocked = isLocked;
    }

    public Account() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Баланс не может быть отрицательным");

        this.balance = balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
