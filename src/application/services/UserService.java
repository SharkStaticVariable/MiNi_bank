package application.services;

import domain.User;
import infrastructure.db.Database;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void createUser(String fullName, int age, String phoneNumber, String address, String documentNumber, String email) {
        if (age < 14) throw new IllegalArgumentException("Ты слишком молодой, приходи, когда получишь паспорт");

        if (
                !((phoneNumber.length() == 12 && phoneNumber.startsWith("+7"))
                        || (phoneNumber.length() == 11 && phoneNumber.startsWith("8")))
        ) {
            throw new IllegalArgumentException("Неверный номер телефона");
        }
        User createdUser = new User(fullName, age, phoneNumber, address, documentNumber, email);

        database.addUser(createdUser);
    }

    public void printUsers() {
        database.printUsers();
    }

    public boolean isUserExists(String fullName) {

        return database.isUserExists(fullName);
    }

    public void getUserInfo(String fullName) {

    }

    public boolean isUserRecenzentHisCountry(String userFullName, String userCountryRecenzent) {
        User user = database.getUser(userFullName);

        String address = user.getAddress().toLowerCase();

        return address.contains(userCountryRecenzent.toLowerCase());
    }

    public User getUser(String userFullName) {
        return database.getUser(userFullName);
    }
}
