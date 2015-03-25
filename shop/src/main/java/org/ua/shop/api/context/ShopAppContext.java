package org.ua.shop.api.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.ua.shop.api.*;
import org.ua.shop.dto.Good;
import org.ua.shop.dto.IncomeTrans;
import org.ua.shop.dto.User;
import org.ua.shop.ui.utils.handler.barcode.BarCodeHandler;
import org.ua.shop.ui.utils.handler.barcode.BarCodeHandlerEvent;
import org.ua.shop.ui.utils.handler.barcode.BarcodeHandlerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yaroslav.Gryniuk
 */

public class ShopAppContext {
    private final static ShopAppContext instance = new ShopAppContext();
    private ExecutorService service = Executors.newFixedThreadPool(1);
    @Autowired
    private BarcodeHandlerFactory factory;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    IncomeGoodsService incomeGoodsService;
    @Autowired
    OutcomeGoodsService outcomeGoodsService;
    @Autowired
    ReportService reportService;

    @Bean
    public static ShopAppContext getInstance() {
        return instance;
    }


    private static String getRandomString() {
        return String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));
    }

    private static int getRandomInt() {
        return Math.abs(new Random(System.currentTimeMillis()).nextInt());
    }

    public UserService getUserService() {
//        return new UserService() {
//            @Override
//            public boolean registerUser(User user) {
//                return false;
//            }
//
//            @Override
//            public boolean authorize(String login, String password) {
////                if (login.equals("12") && password.equals("12"))
////                    return true;
////                return false;
//                System.out.println("login");
//                return true;
//            }
//
//            @Override
//            public void logout() {
//                System.out.println("logout");
//            }
//
//            @Override
//            public User getCurrentUserInfo() {
//                return null;
//            }
//        };
        return userService;

    }

    public OutcomeGoodsService getOutcomeGoodsService(){
        return outcomeGoodsService;
    }


    public GoodsService getGoodsService() {
//        return new GoodsService() {
//            @Override
//            public List<Good> findGoodsByName(String name) {
//                Good good = new Good();
//                good.setBarCode(getRandomString());
//                good.setName(getRandomString());
//                good.setId(getRandomInt());
//                good.setCurrentPrice(new BigDecimal(getRandomInt() % 100));
//                return Arrays.asList(good);
//            }
//
//            @Override
//            public Good findGoodByBarcode(String barcode) {
//                Good good = new Good();
//                good.setBarCode(barcode);
//                good.setName(getRandomString());
//                good.setId(getRandomInt());
//                good.setId(12);
//                good.setCurrentPrice(BigDecimal.TEN);
//                return good;
//            }
//
//            @Override
//            public Good findGoodById(int id) {
//                Good good = new Good();
//                good.setBarCode(getRandomString());
//                good.setName(getRandomString());
//                good.setId(getRandomInt());
//                good.setCurrentPrice(new BigDecimal(getRandomInt() % 100));
//                return good;
//            }
//
//            @Override
//            public void saveOrUpdate(Good good) {
//            }
//        };
        return goodsService;
    }

    public IncomeGoodsService getIncomeGoodsService() {
        return incomeGoodsService;
    }

    public BarCodeHandler createBarcodeHandler(BarCodeHandlerEvent eventHandler) {
        return factory.getHandler(eventHandler);
    }

    public ReportService getReportService() {
        return reportService;
    }

    public ExecutorService getExecutorService() {
        return service;
    }

}
