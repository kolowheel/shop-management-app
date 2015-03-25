package org.ua.shop.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Yaroslav.Gryniuk
 */
@NamedQueries({
        @NamedQuery(name = "User.authorize",query = "from User u where u.login = :login and u.password = :password")
})
@Entity
public class User {

    @Id
    private int id;
    String name;
    String login;
    String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
