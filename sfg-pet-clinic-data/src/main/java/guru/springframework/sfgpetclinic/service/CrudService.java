package guru.springframework.sfgpetclinic.service;

import java.util.Set;

public interface CrudService<T, K> {

    Set<T> findAll();

    T findById(K id);

    T save(T object);

    void delete(T object);

    void deleteById(K id);

}
