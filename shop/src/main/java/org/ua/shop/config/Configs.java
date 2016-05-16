package org.ua.shop.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.ua.shop.ui.utils.handler.barcode.BarcodeHandlerFactory;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yaroslav on 16.05.2016.
 */
@Configuration
public class Configs {

    @Bean(name = "barcodeHandler")
    public BarcodeHandlerFactory barcodeHandlerFactory(
        @Value("${barcode.delay}")String delay,
        @Value("${barcode.suffix}")String suffix) {
        BarcodeHandlerFactory barcodeHandlerFactory = new BarcodeHandlerFactory();
        barcodeHandlerFactory.setDelay(Long.parseLong(delay));
        barcodeHandlerFactory.setSuffix(suffix);
        return barcodeHandlerFactory;
    }

    @Bean(name = "dataSource")
    public DataSource primaryDataSource(
        @Value("${jdbc.url}") String url,
        @Value("${jdbc.user}") String user,
        @Value("${jdbc.pass}") String pass,
        @Value("${jdbc.driverClassName}") String driver) {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        return dataSource;
    }


    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory(
        DataSource dataSource,
        @Value("${hibernate.dialect}") String dialect) throws IOException {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("org.ua.shop.dto");
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.dialect", dialect);
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.afterPropertiesSet();
        return localSessionFactoryBean.getObject();
    }

    @Bean
    public HibernateTransactionManager transactionManager(
        @Qualifier("sessionFactory") SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor transactionManager() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


}
