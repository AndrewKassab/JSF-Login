package andrewkassab.jsf_login.dao;

import andrewkassab.jsf_login.model.User;
import org.springframework.stereotype.Repository;

public interface UserDAO {
    User findUserByUsername(String username);
    void saveUser(User user);
}
