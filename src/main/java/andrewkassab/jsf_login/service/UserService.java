package andrewkassab.jsf_login.service;

import andrewkassab.jsf_login.dao.UserDAO;
import andrewkassab.jsf_login.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserDAO userDAO;

    public boolean validateUser(String username, String password) {
        User user = userDAO.findUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

}
