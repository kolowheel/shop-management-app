package org.ua.shop.dto;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Yaroslav.Gryniuk
 */
@NamedQueries({
        @NamedQuery(name = "Good.findByName",query = "from Good g where g.name like :name"),
        @NamedQuery(name = "Good.findByBarcode" , query = "from Good g where g.barCode like :barcode"),
})
@Entity
public class Good {
    @Id
    private int id;
    private String name;
    private BigDecimal currentPrice;
    private int count;
    @Column(unique = true)
    private String barCode;


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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                ", count=" + count +
                ", barCode='" + barCode + '\'' +
                '}';
    }
}
