package sr.unasat.form.api.interfac;
import java.util.List;

public interface EntityRepository<T> {
    void save(T entity);
    void update(T entity);
    List<T> getAll();
    void deleteById(int id);
    T findById(int id);
}
