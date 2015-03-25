package org.ua.shop.dto;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by wheel on 09.08.14.
 */
@Entity
public class TransGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    BigDecimal price;
    int count;
    @OneToOne(fetch = FetchType.LAZY)
    Good good;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
