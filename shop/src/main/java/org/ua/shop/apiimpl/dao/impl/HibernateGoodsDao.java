package org.ua.shop.apiimpl.dao.impl;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.GoodsDao;
import org.ua.shop.dto.Good;

import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class HibernateGoodsDao implements GoodsDao {
    @Autowired
    SessionFactory factory;


    @Override
    public void addOrUpdate(Good good) {
        if (good.getId() != 0) {
            Good goodFromDB = findGoodById(good.getId());
            if (goodFromDB == null) {
                fac().save(good);
            } else {
                if (good.getBarCode() != null) {
                    goodFromDB.setBarCode(good.getBarCode());
                }
                if (good.getCurrentPrice() != null) {
                    goodFromDB.setCurrentPrice(good.getCurrentPrice());
                }
                if (good.getName() != null) {
                    goodFromDB.setName(good.getName());
                }
            }
        } else {
            fac().saveOrUpdate(good);
        }
    }

    @Override
    public List<Good> findGoodsByName(String name) {
        return fac().getNamedQuery("Good.findByName").setString("name", "%" + name + "%").list();
    }

    @Override
    public Good findGoodByBarcode(String barcode) {
        return (Good) fac().getNamedQuery("Good.findByBarcode").setString("barcode", "barcode").list().get(0);
    }

    @Override
    public Good findGoodById(int id) {
        return (Good) fac().get(Good.class, id);
    }

    @Override
    public void updateCount(int count, int id) {
        Good good = findGoodById(id);
        good.setCount(good.getCount() + count);
    }

    @Override
    public List<Good> getAllGoods() {
        return fac().createCriteria(Good.class).list();
    }


    private Session fac() {
        return factory.getCurrentSession();
    }
}
