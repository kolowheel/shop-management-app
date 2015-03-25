package org.ua.shop.apiimpl.dao.impl;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.ReportDao;
import org.ua.shop.dto.report.GoodReport;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class ReprotDaoImpl implements ReportDao {
    private static final String revenueBetweenDates =
            "SELECT SUM(PRICE*COUNT)\n" +
                    "FROM TRANSGOOD\n" +
                    "WHERE ID IN\n" +
                    "    ( SELECT OUTCOMEGOODS_ID\n" +
                    "     FROM OUTCOMETRANS_TRANSGOOD\n" +
                    "     WHERE OUTCOMETRANS_ID IN\n" +
                    "         ( SELECT ID\n" +
                    "          FROM OUTCOMETRANS\n" +
                    "          WHERE USER_ID =:user_id\n" +
                    "            AND DATE BETWEEN :start AND :stop))";
    private static final String outcomeReport =
        "SELECT id,barcode,name,priceTable.price,priceTable.count ,(priceTable.count*priceTable.price) total\n" +
                "FROM good ,\n" +
                "  (SELECT sum(transgood.count) COUNT, price, innerTable.good_id\n" +
                "   FROM (transgood\n" +
                "         INNER JOIN good ON transgood.good_id=good.id) ,\n" +
                "     (SELECT good_id\n" +
                "      FROM transgood\n" +
                "      GROUP BY good_id) AS innerTable\n" +
                "   WHERE transgood.good_id = innerTable.good_id and transgood.id in \n" +
                "   (SELECT OUTCOMEGOODS_ID\n" +
                "           FROM OUTCOMETRANS_TRANSGOOD\n" +
                "           WHERE OUTCOMETRANS_ID IN\n" +
                "               (SELECT ID\n" +
                "                FROM OUTCOMETRANS\n" +
                "                WHERE DATE BETWEEN ? AND ?))\n" +
                "   GROUP BY price) priceTable\n" +
                "WHERE priceTable.good_id = id";
    @Autowired
    private SessionFactory factory;
    @Autowired
    private DataSource dataSource;

    @Override
    public BigDecimal calculateRevenueBetweenDatesByUser(int userId, LocalDate start, LocalDate stop) {
        List res = factory.getCurrentSession().createSQLQuery(revenueBetweenDates)
                .setInteger("user_id", userId)
                .setString("start", new Timestamp(start.toDateTimeAtStartOfDay().getMillis()).toString())
                .setString("stop", new Timestamp(stop.toDateTimeAtStartOfDay().getMillis()).toString()).list();
        if (res.size() == 1) {
            return new BigDecimal(String.valueOf(res.get(0)));
        }
        return null;
    }

    @Override
    public List<GoodReport> getOutcomeGoodsReport(LocalDate start, LocalDate stop) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        List<GoodReport> goodReports = null;
        goodReports = template.query(outcomeReport, preparedStatement -> {
            preparedStatement.setTimestamp(1, new Timestamp(start.toDateTimeAtStartOfDay().getMillis()));
            preparedStatement.setTimestamp(2, new Timestamp(stop.toDateTimeAtStartOfDay().getMillis()));
        }, (resultSet, i) -> {
            GoodReport goodReport = new GoodReport();
            goodReport.setBarcode(resultSet.getString("barcode"));
            goodReport.setCount(resultSet.getInt("count"));
            goodReport.setName(resultSet.getString("name"));
            goodReport.setTotal(new BigDecimal(resultSet.getString("total") == null ? "0" : resultSet.getString("total")));
            goodReport.setPrice(new BigDecimal(resultSet.getString("price") == null ? "0" : resultSet.getString("price")));
            goodReport.setId(resultSet.getInt("id"));
            return goodReport;
        });
        return goodReports;
    }
}
