package org.ua.shop.dto;


import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Entity
public class OutcomeTrans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @OneToOne(fetch = FetchType.LAZY)
    User user;
    @OneToMany(cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    List<TransGood> outcomeGoods;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    LocalDateTime date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TransGood> getOutcomeGoods() {
        return outcomeGoods;
    }

    public void setOutcomeGoods(List<TransGood> goods) {
        this.outcomeGoods = goods;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
