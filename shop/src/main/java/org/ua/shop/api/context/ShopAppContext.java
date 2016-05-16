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
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IncomeGoodsService incomeGoodsService;
    @Autowired
    private OutcomeGoodsService outcomeGoodsService;
    @Autowired
    private ReportService reportService;

    @Bean
    public static ShopAppContext getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;

    }

    public OutcomeGoodsService getOutcomeGoodsService(){
        return outcomeGoodsService;
    }


    public GoodsService getGoodsService() {
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
