import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainRepository {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "2783641";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db";

    public static void main(String[] args) throws Exception {
        Connection connection= DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1 - Показать всех водителей");
            System.out.println("2 - Найти водителей по возрасту");
            System.out.println("3 - Найти водителей по ID");
            System.out.println("4 - Добавить нового водителя");
            System.out.println("5 - Удалить водителя по ID");
            System.out.println("6 - Добавить несколько новых водителей");
            System.out.println("7 - Найти водителей по адрессу");
            System.out.println("8 - Найти водителей по номеру телефона");
            System.out.println("9 - Найти водителей по email");
            System.out.println("0 - Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Список всех водителей:");
                    List<User> users = userRepository.findAll();
                    users.forEach(user -> {
                        System.out.print("ID: " + user.getId() +
                                ", Имя: " + user.getFirstname() +
                                ", Фамилия: " + user.getSecondname() +
                                ", Возраст: " + user.getAge());

                        if (user.getAddress() != null) {
                            System.out.print(", Адрес: " + user.getAddress());
                        }
                        if (user.getPhoneNumber() != null) {
                            System.out.print(", Телефон: " + user.getPhoneNumber());
                        }
                        if (user.getEmail() != null) {
                            System.out.print(", Email: " + user.getEmail());
                        }
                        System.out.println();
                    });
                    break;

                case 2:
                    System.out.println("Введите возраст для поиска водителя:");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    List<User> usersByAge = userRepository.findAllByAge(age);
                    if (usersByAge.isEmpty()) {
                        System.out.println("Водители с возрастом " + age + " не найдены.");
                    } else {
                        System.out.println("Пользователи с возрастом " + age + ":");
                        usersByAge.forEach(user ->
                                System.out.println("ID: " + user.getId() +
                                        ", Имя: " + user.getFirstname() +
                                        ", Фамилия: " + user.getSecondname() +
                                        ", Возраст: " + user.getAge()));
                    }
                    break;

                case 3:
                    System.out.println("Введите ID водителя для поиска:");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    Optional<User> userById = userRepository.findById(id);
                    userById.ifPresentOrElse(
                            user -> System.out.println("ID: " + user.getId() +
                                    ", Имя: " + user.getFirstname() +
                                    ", Фамилия: " + user.getSecondname() +
                                    ", Возраст: " + user.getAge()),
                            () -> System.out.println("Водитель с ID " + id + " не найден."));
                    break;

                case 4:
                    System.out.println("Введите имя нового водителя:");
                    String firstName = scanner.nextLine();
                    System.out.println("Введите фамилию нового водителя:");
                    String secondName = scanner.nextLine();
                    System.out.println("Введите возраст нового водителя:");
                    int newUserAge = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите адрес нового водителя:");
                    String address = scanner.nextLine();
                    System.out.println("Введите номер телефона нового водителя:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Введите email нового водителя:");
                    String email = scanner.nextLine();
                    User newUser = new User(null, firstName, secondName, newUserAge, address, phoneNumber, email);
                    userRepository.save(newUser);
                    System.out.println("Водитель добавлен: " + newUser.getFirstname() + " " + newUser.getSecondname());
                    break;

                case 5:
                    System.out.println("Введите ID водителя для удаления:");
                    Long removeId = scanner.nextLong();
                    scanner.nextLine();
                    userRepository.removeById(removeId);
                    System.out.println("Водитель с ID " + removeId + " был удален.");
                    break;

                case 6:
                    System.out.println("Введите количество водителя для добавления:");
                    int count = scanner.nextInt();
                    scanner.nextLine();
                    List<User> newUsers = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        System.out.println("Введите имя водителя " + (i + 1) + ":");
                        String firstNameAll = scanner.nextLine();
                        System.out.println("Введите фамилию водителя " + (i + 1) + ":");
                        String secondNameAll = scanner.nextLine();
                        System.out.println("Введите возраст водителя " + (i + 1) + ":");
                        int ageAll = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Введите адрес водителя " + (i + 1) + ":");
                        String addressAll = scanner.nextLine();
                        System.out.println("Введите номер телефона водителя " + (i + 1) + ":");
                        String phoneNumberAll = scanner.nextLine();
                        System.out.println("Введите email водителя " + (i + 1) + ":");
                        String emailAll = scanner.nextLine();
                        newUsers.add(new User(null, firstNameAll, secondNameAll, ageAll, addressAll, phoneNumberAll, emailAll));
                    }

                    userRepository.saveAll(newUsers);
                    System.out.println("Добавлены " + count + " новых водителей.");
                    break;

                case 7:
                    System.out.println("Введите адрес для поиска водителя:");
                    String addressSearch = scanner.nextLine();
                    List<User> usersByAddress = userRepository.findByAddress(addressSearch);
                    if (usersByAddress.isEmpty()) {
                        System.out.println("Водитель с адресом " + addressSearch + " не найден.");
                    } else {
                        System.out.println("Водитель с адресом " + addressSearch + ":");
                        usersByAddress.forEach(user ->
                                System.out.println("ID: " + user.getId() + ", Имя: " + user.getFirstname() +
                                        ", Фамилия: " + user.getSecondname() + ", Возраст: " + user.getAge() +
                                        ", Адрес: " + user.getAddress()));
                    }
                    break;

                case 8:
                    System.out.println("Введите номер телефона для поиска водителя:");
                    String phoneSearch = scanner.nextLine();
                    List<User> usersByPhone = userRepository.findByPhoneNumber(phoneSearch);
                    if (usersByPhone.isEmpty()) {
                        System.out.println("Водитель с номером телефона " + phoneSearch + " не найден.");
                    } else {
                        System.out.println("Водитель с номером телефона " + phoneSearch + ":");
                        usersByPhone.forEach(user ->
                                System.out.println("ID: " + user.getId() + ", Имя: " + user.getFirstname() +
                                        ", Фамилия: " + user.getSecondname() + ", Возраст: " + user.getAge() +
                                        ", Телефон: " + user.getPhoneNumber()));
                    }
                    break;

                case 9:
                    System.out.println("Введите email для поиска водителя:");
                    String emailSearch = scanner.nextLine();
                    List<User> usersByEmail = userRepository.findByEmail(emailSearch);
                    if (usersByEmail.isEmpty()) {
                        System.out.println("Водитель с email " + emailSearch + " не найден.");
                    } else {
                        System.out.println("Водитель с email " + emailSearch + ":");
                        usersByEmail.forEach(user ->
                                System.out.println("ID: " + user.getId() + ", Имя: " + user.getFirstname() +
                                        ", Фамилия: " + user.getSecondname() + ", Возраст: " + user.getAge() +
                                        ", Email: " + user.getEmail()));
                    }
                    break;

                case 0:
                    System.out.println("Выход из программы.");
                    connection.close();
                    return;

                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    break;

            }
        }
    }
}
