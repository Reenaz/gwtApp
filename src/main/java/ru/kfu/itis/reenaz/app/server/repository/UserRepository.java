package ru.kfu.itis.reenaz.app.server.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kfu.itis.reenaz.app.server.entity.User;

/**
 * Created by Reenaz on 23.07.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
