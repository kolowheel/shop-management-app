package org.ua.shop.apiimpl.service;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.api.UserService;
import org.ua.shop.apiimpl.dao.UserDao;
import org.ua.shop.dto.User;

import javax.transaction.Transactional;

import static org.mindrot.jbcrypt.BCrypt.*;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class UserServiceImpl implements UserService {
    private static Logger logger = Logger.getLogger(UserService.class);
    @Autowired
    private UserDao dao;
    private User currentUser = null;

    @Override
    @Transactional
    public boolean registerUser(User user) {
        user.setPassword(hashpw(user.getPassword(), gensalt()));
        return dao.registerUser(user);
    }

    @Override
    @Transactional
    public boolean authorize(String login, String password) {
        if (currentUser != null) {
            logout();
        }
        User authorize = dao.authorize(login, password);

        boolean res = checkpw(password,authorize.getPassword());

        if (res){
            currentUser = authorize;
            dao.logUserAction(currentUser,"login");
            logger.info("User login , login : " + currentUser.getLogin() + ", name : " + currentUser.getName());
        }
        return res;
    }

    @Transactional
    @Override
    public void logout() {
        if (currentUser != null)     {
            dao.logUserAction(currentUser,"logout");
            logger.info("User logout , login : " + currentUser.getLogin() + ", name : " + currentUser.getName());
        }
        currentUser = null;
    }

    @Transactional
    @Override
    public User getCurrentUserInfo() {
        return currentUser;
    }
}
