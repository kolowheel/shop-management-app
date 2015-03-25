package org.ua.shop.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.ua.shop.api.GoodsService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.dto.Good;
import org.ua.shop.dto.report.GoodReport;
import org.ua.shop.ui.dialog.find.FindDialog;
import org.ua.shop.ui.dialog.find.FindService;
import org.ua.shop.ui.dto.OutcomeGoodTableCell;
import org.ua.shop.ui.utils.SharedObject;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by wheel on 23.08.14.
 */
public class GoodsDBController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn barcodeColumn;
    @FXML
    private TableColumn countColumn;
    private ObservableList<Good> goods = FXCollections.observableArrayList();
    private GoodsService goodsService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCellValueFactory();
        goodsService = ShopAppContext.getInstance().getGoodsService();
        tableView.setItems(goods);
    }

    private void initCellValueFactory() {
        idColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, Integer>("id"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("barCode"));
        countColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, Integer>("count"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, BigDecimal>("currentPrice"));
    }


    public void getAllGoodsAction(ActionEvent actionEvent) {
        goods.clear();
        goods.addAll(goodsService.getAllGoods());
    }

    public void findByIdAction(ActionEvent actionEvent) {
        initAndStartFindByDialog(FindService.FIND_BY_ID_SERVICE);
    }

    public void findByNameAction(ActionEvent actionEvent) {
        initAndStartFindByDialog(FindService.FIND_BY_NAME_SERVICE);
    }

    private void initAndStartFindByDialog(FindService service) {
        SharedObject<Good> sharedGood = new SharedObject<>();
        Parent parent = FindDialog.createFindDialog(sharedGood, service);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setTitle("Діалог пошуку");
        stage.setOnCloseRequest(windowEvent -> {
            if (sharedGood.getObject() != null) {
                goods.clear();
                goods.addAll(sharedGood.getObject());
            }
        });
        stage.show();

    }
}
