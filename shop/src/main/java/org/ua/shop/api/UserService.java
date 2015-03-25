package org.ua.shop.api;

import org.ua.shop.dto.User;

/**
 * @author Yaroslav.Gryniuk
 */
public interface UserService {

    boolean registerUser(User user);

    boolean authorize(String login, String password);

    void logout();

    User getCurrentUserInfo();

}
