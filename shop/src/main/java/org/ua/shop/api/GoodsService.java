package org.ua.shop.api;

import org.ua.shop.dto.Good;

import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
public interface GoodsService {
    List<Good> findGoodsByName(String name);

    Good findGoodByBarcode(String barcode);

    Good findGoodById(int id);

    void saveOrUpdate(Good good);

    List<Good> getAllGoods();
}
