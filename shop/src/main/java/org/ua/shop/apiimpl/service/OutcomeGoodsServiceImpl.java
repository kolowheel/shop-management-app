package org.ua.shop.apiimpl.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.api.OutcomeGoodsService;
import org.ua.shop.api.UserService;
import org.ua.shop.apiimpl.dao.GoodsDao;
import org.ua.shop.apiimpl.dao.OutcomeGoodsDao;
import org.ua.shop.dto.OutcomeTrans;

import javax.transaction.Transactional;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class OutcomeGoodsServiceImpl implements OutcomeGoodsService {
    private static final Logger logger = Logger.getLogger(OutcomeGoodsService.class);
    @Autowired
    private OutcomeGoodsDao dao;
    @Autowired
    private GoodsDao goodsDao;


    @Override
    @Transactional
    public void addOutcomeTransAndUpdateCount(OutcomeTrans trans) {

        trans.getOutcomeGoods().stream().forEach(e -> goodsDao.updateCount(-e.getCount(), e.getGood().getId()));
        dao.addOutcomeTrans(trans);
    }
}
