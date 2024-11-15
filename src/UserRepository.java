import java.util.List;

public interface UserRepository extends CrudRepository{
    List<User> findAllByAge(Integer age);
    void saveAll(List<User> users);
    List<User> findByAddress(String address);
    List<User> findByPhoneNumber(String phone);
    List<User> findByEmail(String email);
}
