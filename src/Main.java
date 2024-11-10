import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "2783641";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test_db";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from driver");
        System.out.println("Существующие водители в базе данных:");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id")+ " " + resultSet.getString("first_name")
                    + " " + resultSet.getString("second_name")+ " " + resultSet.getInt("age"));
        }

        String sqlInsertUser = "insert into driver(first_name,second_name,age)" +
                "values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);

        Scanner scanner = new Scanner(System.in);
        int totalAdded = 0;

        for (int i=0;i<6;i++) {
            System.out.println("Введите имя:");
            String firstName = scanner.nextLine();
            System.out.println("Введите фамилию:");
            String secondName = scanner.nextLine();
            System.out.println("Введите возраст:");
            int age = scanner.nextInt();
            scanner.nextLine();

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);

            totalAdded += preparedStatement.executeUpdate();
        }

        System.out.println("Было добавлено " + totalAdded + " строк");

        System.out.println("Водители, у которых имя начинается на 'Р':");
        String sqlInsertUserName = "select first_name from driver where first_name LIKE 'Р%' ";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sqlInsertUserName);
        ResultSet resultSet2 = preparedStatement2.executeQuery();

        while (resultSet2.next()){
            System.out.println(resultSet2.getString("first_name"));
        }
    }
}