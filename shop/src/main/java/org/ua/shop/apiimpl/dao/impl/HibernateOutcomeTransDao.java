package org.ua.shop.apiimpl.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.OutcomeGoodsDao;
import org.ua.shop.dto.OutcomeTrans;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class HibernateOutcomeTransDao implements OutcomeGoodsDao {
    @Autowired
    private SessionFactory factory;
    @Override
    public void addOutcomeTrans(OutcomeTrans trans) {
        factory.getCurrentSession().save(trans);
    }
}
