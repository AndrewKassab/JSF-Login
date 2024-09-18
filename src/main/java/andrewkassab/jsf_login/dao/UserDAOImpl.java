package andrewkassab.jsf_login.dao;

import andrewkassab.jsf_login.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User findUserByUsername(String username) {
        String hql = "FROM User WHERE username = :username";
        return (User) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

}
