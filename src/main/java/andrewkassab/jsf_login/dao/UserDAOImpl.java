package andrewkassab.jsf_login.dao;

import andrewkassab.jsf_login.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE username = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

}
