package util;
import application.services.AccountService;
import application.services.TransactionService;
import application.services.UserService;
import domain.Account;
import domain.Transaction;
import domain.User;
import infrastructure.db.Database;
import lib.ArrayListCustom;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Menu {
    private static Database database = new Database();
    private static AccountService accountService = new AccountService(database);
    private static UserService userService = new UserService(database);
    private static TransactionService transactionService = new TransactionService(database);

    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void runApplication() {
        System.out.println("Выберите пункт из меню:");
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Получить список всех пользователей");
        System.out.println("3. Создать счет");
        System.out.println("4. Пополнить счет");
        System.out.println("5. Перевести деньги между счетами");
        System.out.println("6. Вывести все мои транзакции ");

        try {
            String input;
            do {
                input = reader.readLine();
                try {
                    switch (input) {
                        case "1": {
                            createUser();
                            break;
                        }
                        case "2": {
                            printUsers();
                            break;
                        }
                        case "3": {
                            createAccount();
                            break;
                        }
                        case "4": {
                            addMoney();
                            break;
                        }
                        case "5": {
                            transerMoney();
                            break;
                        }
                        case "6": {
                            printTransactions();
                            break;
                        }
                        default: {
                            System.out.println("неверная команда");
                        }
                    }
                    System.out.println("Выберите пункт из меню:");
                    System.out.println("1. Добавить пользователя");
                    System.out.println("2. Получить список всех пользователей");
                    System.out.println("3. Создать счет");
                    System.out.println("4. Пополнить счет");
                    System.out.println("5. Перевести деньги между счетами");
                    System.out.println("6. Вывести все мои транзакции ");
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            while (input != "-1");

            if (input == "-1") {
                System.out.println("Работа программы успешно завершена");
            }
        }
         catch (IOException exception)
         {
            System.out.println(exception.getMessage());
        }
    }

    public static void loadDB() {
        try (
                var objectInputStream = new java.io.ObjectInputStream(
                        Files.newInputStream(Path.of("db.serialization"))
                )
        ) {
            final var users = (ArrayListCustom<User>) objectInputStream.readObject();
            final var transactions = (ArrayListCustom<Transaction>) objectInputStream.readObject();
            final var accounts = (ArrayListCustom<Account>) objectInputStream.readObject();

            database.setAccounts(accounts);
            database.setUsers(users);
            database.setTransactions(transactions);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить базу данных " + e.getMessage());
        }
    }

    public static void createUser() throws IOException {
        //имя
        System.out.println("Введите имя");
        String name = reader.readLine();
        //возраст
        System.out.println("Введите возраст");
        int age = Integer.parseInt(reader.readLine());
        //номер телефона
        System.out.println("Введите номер телефона");
        String phoneNumber = reader.readLine();
        //адрес
        System.out.println("Введите адрес");
        String address = reader.readLine();
        //паспорт
        System.out.println("Введите номер паспорта");
        String passportNumber = reader.readLine();
        //эл. почта
        System.out.println("Введите электронную почту ");
        String email = reader.readLine();

        userService.createUser(name, age, phoneNumber, address, passportNumber, email);
    }

    public static void createAccount() throws IOException {
        System.out.println("Введите имя владельца счета");
        String userFullName = reader.readLine();
        boolean isUserExists = userService.isUserExists(userFullName);

        if (!isUserExists)
        {
            System.out.println("Такого пользователя еще нет");
            return;
        }

        System.out.println("Налоговым рецензентом какой стран(-ы) является владелец счета?");
        String userCountryRecenzent = reader.readLine();
        boolean isUserRecenzentHisCountry = userService.isUserRecenzentHisCountry(userFullName, userCountryRecenzent);

        if (!isUserRecenzentHisCountry)
        {
            System.out.println("Владелец счета не является рецензентом этой страны");
            return;
        }

        User user = userService.getUser(userFullName);
        boolean hasAnyAccounts = accountService.hasAnyAccounts(user);

        if (hasAnyAccounts)
        {
            System.out.println("У этого пользователя уже есть счет");
            return;
        }

        accountService.createAccount(user);
        System.out.println("Счет создан успешно");
        return;
    }

    public static void printUsers() {
        userService.printUsers();
    }

    public static void addMoney() throws IOException {
        System.out.println("Введите имя владельца счета");
        String fullName = reader.readLine();

        User user = userService.getUser(fullName);

        System.out.println("Введите сумму, которую хотите внести на счет");
        int amount = Integer.parseInt(reader.readLine());

        if (amount <= 0)
        {
            throw new IllegalArgumentException("Сумма должна быть больше 0");
        }

        accountService.addMoney(user, amount);
    }

    public static void transerMoney() throws IOException {
        System.out.println("Введите имя владельца счета");
        String ownerFullName = reader.readLine();
        User owner = userService.getUser(ownerFullName);

        System.out.println("Введите имя получателя");
        String receiverFullName = reader.readLine();
        User receiver = userService.getUser(receiverFullName);

        System.out.println("Введите сумму, которую хотите перевести");
        int amount = Integer.parseInt(reader.readLine());

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше 0");
        }

        accountService.transferMoney(owner, receiver, amount);
    }

    public static void printTransactions() throws IOException {
        System.out.println("Введите имя владельца счета");

        String fullName = reader.readLine();
        User owner = userService.getUser(fullName);

        transactionService.printTransactions(owner);
    }
}
