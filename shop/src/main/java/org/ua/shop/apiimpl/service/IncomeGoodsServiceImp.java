package org.ua.shop.apiimpl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ua.shop.api.IncomeGoodsService;
import org.ua.shop.api.UserService;
import org.ua.shop.apiimpl.dao.GoodsDao;
import org.ua.shop.apiimpl.dao.IncomeGoodsDao;
import org.ua.shop.dto.IncomeTrans;

import javax.transaction.Transactional;

/**
 * @author Yaroslav.Gryniuk
 */
@Service
public class IncomeGoodsServiceImp implements IncomeGoodsService {
    @Autowired
    private IncomeGoodsDao dao;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional
    public void addIncomeTransAndUpdateCount(IncomeTrans trans) {
        trans.getIncomeGoods()
            .stream()
            .forEach(e -> goodsDao.updateCount(e.getCount(), e.getGood().getId()));
        dao.addIncomeTrans(trans);
    }
}