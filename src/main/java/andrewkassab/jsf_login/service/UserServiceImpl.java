package andrewkassab.jsf_login.service;

import andrewkassab.jsf_login.dao.UserDAO;
import andrewkassab.jsf_login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public boolean authenticate(String username, String password) {
        Optional<User> user = userDAO.findUserByUsername(username);
        return user.filter(u -> u.getPassword().equals(password)).isPresent();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

}
