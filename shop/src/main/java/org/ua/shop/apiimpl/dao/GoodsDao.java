package org.ua.shop.apiimpl.dao;

import org.ua.shop.dto.Good;

import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
public interface GoodsDao {
    void addOrUpdate(Good good);

    List<Good> findGoodsByName(String name);

    Good findGoodByBarcode(String barcode);

    Good findGoodById(int id);

    void updateCount(int count, int id);

    List<Good> getAllGoods();
}
