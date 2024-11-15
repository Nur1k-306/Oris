import java.util.List;
import java.util.Optional;

public interface CrudRepository <T>{
    List<T> findAll();
    Optional<T> findById(Long id);
    void save(User entity);
    void removeById(Long id);
}
