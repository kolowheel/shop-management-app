package org.ua.shop.api;

import org.ua.shop.dto.IncomeTrans;

/**
 * @author Yaroslav.Gryniuk
 */
public interface IncomeGoodsService {
    void addIncomeTransAndUpdateCount(IncomeTrans trans);
}
