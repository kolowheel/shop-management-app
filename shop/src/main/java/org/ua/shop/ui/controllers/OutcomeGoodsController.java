package org.ua.shop.ui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.ua.shop.api.GoodsService;
import org.ua.shop.api.OutcomeGoodsService;
import org.ua.shop.api.UserService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.dialog.find.FindDialog;
import org.ua.shop.ui.dialog.find.FindService;
import org.ua.shop.ui.dialog.pay.PayDialog;
import org.ua.shop.ui.dto.OutcomeGoodTableCell;
import org.ua.shop.ui.utils.PopUpUtils;
import org.ua.shop.ui.utils.SharedObject;
import org.ua.shop.ui.utils.converter.Converter;
import org.ua.shop.ui.utils.handler.focus.FocusListenersManager;


import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Yaroslav.Gryniuk
 */
public class OutcomeGoodsController implements Initializable {
    @FXML
    TableColumn codeColumn;
    @FXML
    TableColumn nameColumn;
    @FXML
    TableColumn countColumn;
    @FXML
    TableColumn perItemPriceColumn;
    @FXML
    TableColumn totalColumn;
    @FXML
    TableView tableView;
    @FXML
    AnchorPane pane;
    @FXML
    Button payButton;
    @FXML
    Button deleteButton;
    @FXML
    Button addByIdButton;
    @FXML
    Button addByNameButton;
    @FXML
    Button focusButton;
    @FXML
    GoodsService goodsService;
    @FXML
    Label totalLabel;
    BigDecimal total;
    final ObservableList<OutcomeGoodTableCell> goods = FXCollections.observableArrayList();
    OutcomeGoodsService outcomeGoodsService;
    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCellFactories();
        initSaveEditing();
        initBarcodeHandler();
        initServices();
        initCellValueFactories();
        initFocusHandler();
        tableView.setItems(goods);
        initTotalListener();

    }

    private void initTotalListener() {
        goods.addListener((ListChangeListener<OutcomeGoodTableCell>) change -> {
            updateTotal();
        });
    }

    private void initCellFactories() {
        Callback cellFactory = TextFieldTableCell.<OutcomeGoodTableCell>forTableColumn();
        countColumn.setCellFactory(cellFactory);
    }

    private void initCellValueFactories() {
        totalColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("total"));
        perItemPriceColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("perItemPrice"));
        countColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("count"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("name"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<OutcomeGoodTableCell, String>("code"));
    }

    private void initSaveEditing() {
        countColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<OutcomeGoodTableCell, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<OutcomeGoodTableCell, String> t) {
                        OutcomeGoodTableCell cell = t.getRowValue();
                        if (checkIfDigDecimal(t.getNewValue())) {
                            cell.setCount(t.getNewValue());
                        } else {
                            PopUpUtils.showBadNumberFormatPopUp();
                        }
                        tableView.requestFocus();
                    }
                }
        );
    }

    private void initServices() {
        userService = ShopAppContext.getInstance().getUserService();
        goodsService = ShopAppContext.getInstance().getGoodsService();
        outcomeGoodsService = ShopAppContext.getInstance().getOutcomeGoodsService();
    }


    private void initBarcodeHandler() {
        pane.addEventFilter(KeyEvent.ANY, ShopAppContext.getInstance().createBarcodeHandler(barcode -> {
            Good good = goodsService.findGoodByBarcode(barcode);
            if (good != null) {
                addGoodIntoTableView((new OutcomeGoodTableCell(good)));
            } else {
                PopUpUtils.notFoundByBarcodeDialog();
            }
        }));
    }

    private void addGoodIntoTableView(OutcomeGoodTableCell outcomeGoodTableCell) {
        List<OutcomeGoodTableCell> findedCell = goods.stream().filter(o -> o.getCode().equals(outcomeGoodTableCell.getCode())).collect(Collectors.toList());
        if (findedCell.size() >= 1) {
            OutcomeGoodTableCell cell = findedCell.get(0);
            cell.setCount(String.valueOf(Integer.parseInt(cell.getCount()) + 1));
        } else {
            outcomeGoodTableCell.countProperty().addListener((e, e1, e2) -> updateTotal());
            goods.add(outcomeGoodTableCell);
        }
    }

    private void updateTotal() {
        if (goods.size() != 0) {
            BigDecimal total = goods.stream().map(a -> new BigDecimal(a.getTotal())).reduce((a, b) -> a.add(b)).get();
            totalLabel.setText(total.toString());
            this.total = total;
        }
    }

    private void initFocusHandler() {
        focusButton.setGraphic(new ImageView("/imgs/redB.png"));
        new FocusListenersManager((a) -> {
            if (a) {
                focusButton.setGraphic(new ImageView("/imgs/greenB.png"));
            } else {
                focusButton.setGraphic(new ImageView("/imgs/redB.png"));
            }
        }, tableView, addByIdButton, addByNameButton, focusButton, payButton, deleteButton);
        pane.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) -> Platform.runLater(tableView::requestFocus));
    }

    private boolean checkIfDigDecimal(String newValue) {
        try {
            new BigDecimal(newValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void addByIdAction(ActionEvent actionEvent) {
        initAndStartFindByDialog(FindService.FIND_BY_ID_SERVICE);
    }

    public void payAction(ActionEvent actionEvent) {
        Parent parent = PayDialog.createPayDialog(total);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setTitle("Діалог оплати");
        stage.setOnCloseRequest(windowEvent -> {
            outcomeGoodsService.addOutcomeTransAndUpdateCount(new Converter().convert(userService.getCurrentUserInfo(), goods));
            goods.clear();
        });
        stage.show();

    }

    public void deleteRowAction(ActionEvent actionEvent) {
        int focusedIndex = tableView.getFocusModel().getFocusedIndex();
        if (focusedIndex != -1) {
            goods.remove(focusedIndex);
        }
    }

    //FIXME use utils
    public void addByNameAction(ActionEvent actionEvent) {
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
                addGoodIntoTableView(new OutcomeGoodTableCell(sharedGood.getObject()));
            }
        });
        stage.show();

    }

}
