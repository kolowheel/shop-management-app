package org.ua.shop.dto;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Entity
public class IncomeTrans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    @PrimaryKeyJoinColumn
    private List<TransGood> incomeGoods;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime date;
    @Column
    private String provider;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TransGood> getIncomeGoods() {
        return incomeGoods;
    }

    public void setIncomeGoods(List<TransGood> incomeGoods) {
        this.incomeGoods = incomeGoods;
    }


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
