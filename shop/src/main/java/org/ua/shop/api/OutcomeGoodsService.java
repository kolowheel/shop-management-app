package org.ua.shop.api;

import org.ua.shop.dto.OutcomeTrans;

/**
 * @author Yaroslav.Gryniuk
 */
public interface OutcomeGoodsService {
    void addOutcomeTransAndUpdateCount(OutcomeTrans trans);
}
