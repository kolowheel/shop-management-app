package org.ua.shop.api;

import org.joda.time.LocalDate;
import org.ua.shop.dto.User;
import org.ua.shop.dto.report.GoodReport;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
public interface ReportService {
    BigDecimal calculateTodayRevenueByUser(User user);
    List<GoodReport> generateReport(LocalDate start , LocalDate stop);
}
