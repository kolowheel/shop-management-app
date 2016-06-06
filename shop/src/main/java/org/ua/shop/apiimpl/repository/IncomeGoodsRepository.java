package org.ua.shop.apiimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ua.shop.dto.IncomeTrans;

/**
 * Created by yaroslav on 06.06.2016.
 */
public interface IncomeGoodsRepository extends JpaRepository<IncomeTrans,Integer>{
}
