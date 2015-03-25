package org.ua.shop.apiimpl.dao.impl;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.UserDao;
import org.ua.shop.dto.LogUserAction;
import org.ua.shop.dto.User;

import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class HibernateUserDao implements UserDao {
    @Autowired
    SessionFactory factory;


    @Override
    public boolean registerUser(User user) {
        int id = (int) factory.getCurrentSession().save(user);
        factory.getCurrentSession().flush();
        return id > 0;
    }

    @Override
    public User authorize(String login, String password) {
        List<User> users = factory.getCurrentSession().getNamedQuery("User.authorize").
                setString("login", login).
                setString("password", password).list();
        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public void logUserAction(User user, String action) {
        LogUserAction userAction = new LogUserAction();
        userAction.setUser(user);
        userAction.setDate(LocalDateTime.now());
        userAction.setAction(action);
        factory.getCurrentSession().save(userAction);
    }
}
