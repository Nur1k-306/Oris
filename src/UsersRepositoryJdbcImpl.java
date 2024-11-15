import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository{
    private Connection connection;

    private static final String SQL_SELECT_ALL_FROM_DRIVER = "select * from driver";
    private static final String SQL_SELECT_BY_AGE = "select * from driver where age = ?";
    private static final String SQL_SELECT_BY_ID = "select * from driver where id = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO driver (first_name, second_name, age, address, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "delete from driver where id = ?";
    private static final String SQL_SELECT_BY_ADDRESS = "SELECT * FROM driver WHERE address = ?";
    private static final String SQL_SELECT_BY_PHONE = "SELECT * FROM driver WHERE phone_number = ?";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM driver WHERE email = ?";

    public  UsersRepositoryJdbcImpl(Connection connection){
        this.connection = connection;
    }


    @Override
    public List<User> findAllByAge(Integer age) {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_AGE)) {
            preparedStatement.setInt(1, age);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);
            while (resultSet.next()) {
                User user=new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                result.add(user);
            }
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) { // Используем SQL_SELECT_BY_ID
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(User entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1,entity.getFirstname());
            preparedStatement.setString(2,entity.getSecondname());
            preparedStatement.setInt(3,entity.getAge());
            preparedStatement.setString(4, entity.getAddress());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAll(List<User> users) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            for (User user : users) {
                preparedStatement.setString(1,user.getFirstname());
                preparedStatement.setString(2,user.getSecondname());
                preparedStatement.setInt(3,user.getAge());
                preparedStatement.setString(4, user.getAddress());
                preparedStatement.setString(5, user.getPhoneNumber());
                preparedStatement.setString(6, user.getEmail());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findByAddress(String address) {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ADDRESS)) {
            preparedStatement.setString(1, address);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_PHONE)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public List<User> findByEmail(String email) {
        List<User> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
