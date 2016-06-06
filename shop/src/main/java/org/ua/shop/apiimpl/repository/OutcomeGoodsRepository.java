package org.ua.shop.apiimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ua.shop.dto.OutcomeTrans;

/**
 * Created by yaroslav on 06.06.2016.
 */
public interface OutcomeGoodsRepository extends JpaRepository<OutcomeTrans,Integer> {
}
