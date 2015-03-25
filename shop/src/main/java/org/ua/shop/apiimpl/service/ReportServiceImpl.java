package org.ua.shop.apiimpl.service;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ua.shop.api.ReportService;
import org.ua.shop.apiimpl.dao.ReportDao;
import org.ua.shop.dto.User;
import org.ua.shop.dto.report.GoodReport;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao dao;


    @Override
    public BigDecimal calculateTodayRevenueByUser(User user) {
        return dao.calculateRevenueBetweenDatesByUser(user.getId(), LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Override
    public List<GoodReport> generateReport(LocalDate start, LocalDate stop) {
        return dao.getOutcomeGoodsReport(start, stop);
    }


}
