package org.ua.shop.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.ua.shop.api.GoodsService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.utils.PopUpUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by wheel on 09.08.14.
 */
public class AddGoodController implements Initializable {
    private static Logger log = Logger.getLogger(AddGoodController.class.getName());
    @FXML
    private TextField goodName;
    @FXML
    private TextField goodPrice;
    @FXML
    private TextField goodBarCode;
    @FXML
    private TextField count;
    @FXML
    private TextField id;

    @FXML
    private AnchorPane pane;

    private GoodsService goodsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goodsService = ShopAppContext.getInstance().getGoodsService();
    }

    public void addGoodAction() {
        Good good = new Good();
        try {
            fillGood(good);
            System.out.println(good.toString());
            goodsService.saveOrUpdate(good);
        } catch (Exception e) {
            PopUpUtils.showBadNumberFormatPopUp();
            log.warning("Wrong format");
        }
        log.info("Emiting add new good");
    }

    private void fillGood(Good good) {
        good.setId(id.getText().trim().isEmpty() ? 0 : Integer.parseInt(id.getText()));
        good.setBarCode(goodBarCode.getText().trim().isEmpty() ? null : goodBarCode.getText());
        good.setName(goodName.getText().trim().isEmpty() ? null : goodName.getText());
        good.setCount(count.getText().trim().isEmpty() ? 0 : Integer.valueOf(count.getText()));
        good.setCurrentPrice(goodPrice.getText().trim().isEmpty() ? null : new BigDecimal(goodPrice.getText().replace(",", ".")));
    }


}
