package org.ua.shop.apiimpl.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.api.UserService;
import org.ua.shop.apiimpl.dao.UserDao;
import org.ua.shop.dto.User;

import javax.transaction.Transactional;

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
        return dao.registerUser(user);
    }

    @Override
    @Transactional
    public boolean authorize(String login, String password) {
        if (currentUser != null) {
            logout();
        }
        currentUser = dao.authorize(login, password);
        boolean res = currentUser != null;

        if (res){
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
