package org.ua.shop.apiimpl.dao.impl;

import org.h2.engine.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.IncomeGoodsDao;
import org.ua.shop.dto.IncomeTrans;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class HibernateIncomeGoodsDao implements IncomeGoodsDao{
    @Autowired
    private SessionFactory factory;
    @Override
    public void addIncomeTrans(IncomeTrans trans) {
        factory.getCurrentSession().save(trans);
    }
}
