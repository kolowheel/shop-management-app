package samplefx;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.ua.shop.apiimpl.dao.GoodsDao;
import org.ua.shop.apiimpl.dao.ReportDao;
import org.ua.shop.dto.*;

import javax.transaction.Transactional;


/**
 * @author Yaroslav.Gryniuk
 */
@Component
public class hibernateTest {
    @Autowired
    SessionFactory factory;
    @Autowired
    GoodsDao dao;
    @Autowired
    ReportDao reportDao;


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/configs/context.xml");
        hibernateTest test = (hibernateTest) context.getBean("ht");
        test.doSmt();

    }

    @Transactional
    public void doSmt() {
//        User user = new User();
//        user.setId(12);
//        // factory.getCurrentSession().saveOrUpdate(user);
//        IncomeTrans incomeTrans = new IncomeTrans();
//        incomeTrans.setUser(user);
//        incomeTrans.setDate(new Date());

//
//        Good good = new Good();
//        good.setName("123");
//        good.setCurrentPrice(new BigDecimal("12.222"));
//
//        TransGood transGood = new TransGood();
//        transGood.setGood(good);
//        incomeTrans.setIncomeGoods(Arrays.asList(transGood));
//

//        org.hibernate.Query query = factory.getCurrentSession().getNamedQuery("Good.findByName").setString("name", "%1%");
//        System.out.println((int) factory.getCurrentSession().createSQLQuery("SELECT SUM(PRICE*COUNT) \n" +
//                "FROM TRANSGOOD \n" +
//                "WHERE ID IN (\n" +
//                "\tSELECT OUTCOMEGOODS_ID \n" +
//                "\tFROM OUTCOMETRANS_TRANSGOOD\n" +
//                "\tWHERE OUTCOMETRANS_ID IN (\n" +
//                "\t\tSELECT ID FROM OUTCOMETRANS \n" +
//                "\t\tWHERE USER_ID =14 AND  DATE > CURDATE()))").list().get(0));
//        List<Good> goodList = query.list();
//        System.out.println(goodList.size());
//        System.out.println(goodList.get(0).getName());

        reportDao.getOutcomeGoodsReport(LocalDate.now().minusDays(10),LocalDate.now());
//        factory.getCurrentSession().save(new OutcomeTrans());
    }
}
