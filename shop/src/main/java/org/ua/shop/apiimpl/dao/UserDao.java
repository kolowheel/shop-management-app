package org.ua.shop.apiimpl.dao;

import org.ua.shop.dto.User;

/**
 * @author Yaroslav.Gryniuk
 */
public interface UserDao {
    boolean registerUser(User user);

    User authorize(String login, String password);

    void logUserAction(User user,String action);
}
