package andrewkassab.jsf_login.dao;

import andrewkassab.jsf_login.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findUserByUsername(String username);
    void saveUser(User user);
}
