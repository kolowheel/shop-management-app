package org.ua.shop.apiimpl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.api.GoodsService;
import org.ua.shop.apiimpl.dao.GoodsDao;
import org.ua.shop.dto.Good;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao dao;

    @Override
    @Transactional
    public void saveOrUpdate(Good good) {
        dao.addOrUpdate(good);
    }

    @Override
    @Transactional
    public List<Good> getAllGoods() {
        return dao.getAllGoods();
    }

    @Override
    @Transactional
    public List<Good> findGoodsByName(String name) {
        return dao.findGoodsByName(name);
    }

    @Override
    @Transactional
    public Good findGoodByBarcode(String barcode) {
        return dao.findGoodByBarcode(barcode);
    }

    @Override
    @Transactional
    public Good findGoodById(int id) {
        return dao.findGoodById(id);
    }
}
