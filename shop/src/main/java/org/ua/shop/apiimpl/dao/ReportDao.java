package org.ua.shop.apiimpl.dao;

import org.joda.time.LocalDate;
import org.ua.shop.dto.User;
import org.ua.shop.dto.report.GoodReport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
public interface ReportDao {
    BigDecimal calculateRevenueBetweenDatesByUser(int userId, LocalDate start, LocalDate stop);

    List<GoodReport> getOutcomeGoodsReport(LocalDate start, LocalDate stop);
}
