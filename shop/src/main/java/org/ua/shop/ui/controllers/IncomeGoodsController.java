package org.ua.shop.ui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.ua.shop.api.GoodsService;
import org.ua.shop.api.IncomeGoodsService;
import org.ua.shop.api.UserService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.dto.Good;
import org.ua.shop.dto.TransGood;
import org.ua.shop.ui.dialog.FinDialogContext;
import org.ua.shop.ui.dialog.FindByDialogUtils;
import org.ua.shop.ui.dialog.find.FindDialog;
import org.ua.shop.ui.dialog.find.FindService;
import org.ua.shop.ui.dialog.pay.PayDialog;
import org.ua.shop.ui.dto.IncomeGoodsTableCell;
import org.ua.shop.ui.utils.converter.Converter;
import org.ua.shop.ui.utils.handler.barcode.BarCodeHandler;
import org.ua.shop.ui.utils.PopUpUtils;
import org.ua.shop.ui.utils.SharedObject;
import org.ua.shop.ui.utils.handler.focus.FocusListenersManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by wheel on 09.08.14.
 */
//FIXME change to observable value ::: DONE
public class IncomeGoodsController implements Initializable {
    private static Logger log = Logger.getLogger(IncomeGoodsController.class.getName());
    @FXML
    private TableColumn totalColumn;
    @FXML
    private TableColumn perItemPriceColumn;
    @FXML
    private TableColumn orgColumn;
    @FXML
    private TableColumn countColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn codeColumn;
    @FXML
    private TableView table;
    @FXML
    private Label totalLabel;
    @FXML
    private AnchorPane pane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button addByName;
    @FXML
    private Button addById;
    @FXML
    private Button focus;
    @FXML
    private Button pay;
    @FXML
    private Button delete;
    private BigDecimal total = BigDecimal.ZERO;
    private GoodsService goodsService;
    private IncomeGoodsService incomeGoodsService;
    private final ObservableList<IncomeGoodsTableCell> goods = FXCollections.observableArrayList();
    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCellFactories();
        initSaveEditing();
        initCellValueFactories();
        initServices();
        initFocusHandlers();
        initBarcodeHandler();
        table.setItems(goods);

    }

    public void addByNameAction(ActionEvent actionEvent) throws IOException {
        FinDialogContext<Good> context = new FinDialogContext<>();
        context.setFindService(FindService.FIND_BY_NAME_SERVICE);
        context.setEventEventHandler(getOnCloseFindByDialogHandler(context.getSharedObject()));
        FindByDialogUtils.initAndStartFindByDialog(context);
    }

    public void addByCodeAction(ActionEvent actionEvent) {
        FinDialogContext<Good> context = new FinDialogContext<>();
        context.setFindService(FindService.FIND_BY_ID_SERVICE);
        context.setEventEventHandler(getOnCloseFindByDialogHandler(context.getSharedObject()));
        FindByDialogUtils.initAndStartFindByDialog(context);
    }

    public void deleteRowAction(ActionEvent actionEvent) {
        int focusedIndex = table.getFocusModel().getFocusedIndex();
        if (focusedIndex != -1) {
            goods.remove(focusedIndex);
        }
    }

    public void sendIncomeTransAction() {
        Parent parent = PayDialog.createPayDialog(total);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setTitle("Діалог оплати");
        stage.setOnCloseRequest(windowEvent -> {
            incomeGoodsService.addIncomeTransAndUpdateCount(new Converter().convert(userService.getCurrentUserInfo(),goods));
            goods.clear();
        });
        stage.show();
    }

    private void initCellValueFactories() {
        totalColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("total"));
        perItemPriceColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("perItemPrice"));
        orgColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("org"));
        countColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("count"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("name"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<TransGood, String>("code"));
    }

    private EventHandler<WindowEvent> getOnCloseFindByDialogHandler(SharedObject<Good> goodSharedObject){
        return eventHandler -> {
            if (goodSharedObject.getObject() != null) {
                IncomeGoodsTableCell cell = new IncomeGoodsTableCell();
                cell.setCode(String.valueOf(goodSharedObject.getObject().getId()));
                cell.setName(goodSharedObject.getObject().getName());
                goods.add(cell);
            }
        };
    }

    private void initSaveEditing() {
        orgColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<IncomeGoodsTableCell, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<IncomeGoodsTableCell, String> t) {

                        t.getTableView().getItems().get(t.getTablePosition().getRow()).setOrg(t.getNewValue());
                        for (IncomeGoodsTableCell cell : goods) {
                            cell.setOrg(t.getNewValue());
                        }
                        focus.requestFocus();
                    }
                }
        );
        perItemPriceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<IncomeGoodsTableCell, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<IncomeGoodsTableCell, String> t) {
                        IncomeGoodsTableCell cell = t.getRowValue();
                        if (checkIfDigDecimal(t.getNewValue())) {
                            cell.setPerItemPrice(t.getNewValue());
                        } else {
                            PopUpUtils.showBadNumberFormatPopUp();
                        }
                        calculateSetAndUpdateTotal(cell);
                        focus.requestFocus();
                    }
                }
        );
        countColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<IncomeGoodsTableCell, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<IncomeGoodsTableCell, String> t) {
                        IncomeGoodsTableCell cell = t.getRowValue();
                        if (checkIfDigDecimal(t.getNewValue())) {
                            cell.setCount(t.getNewValue());
                        } else {
                            PopUpUtils.showBadNumberFormatPopUp();
                            log.warning("Bad number format");
                        }
                        calculateSetAndUpdateTotal(cell);
                        focus.requestFocus();
                    }
                }
        );
    }

    private void initCellFactories() {
        Callback cellFactory = TextFieldTableCell.<IncomeGoodsTableCell>forTableColumn();
        orgColumn.setCellFactory(cellFactory);
        countColumn.setCellFactory(cellFactory);
        nameColumn.setCellFactory(cellFactory);
        codeColumn.setCellFactory(cellFactory);
        perItemPriceColumn.setCellFactory(cellFactory);
        totalColumn.setCellFactory(cellFactory);
    }

    private boolean checkIfDigDecimal(String newValue) {
        try {
            new BigDecimal(newValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void calculateSetAndUpdateTotal(IncomeGoodsTableCell cell) {
        BigDecimal count = new BigDecimal(cell.getCount());
        BigDecimal perItemPrice = new BigDecimal(cell.getPerItemPrice());
        cell.setTotal(count.multiply(perItemPrice).toString());
        BigDecimal total = goods.stream().map(a -> new BigDecimal(a.getTotal())).reduce((a, b) -> a.add(b)).get();
        totalLabel.setText(total.toString());
        this.total = total;
    }

    private void initServices() {
        userService = ShopAppContext.getInstance().getUserService()  ;
        incomeGoodsService = ShopAppContext.getInstance().getIncomeGoodsService();
        goodsService = ShopAppContext.getInstance().getGoodsService();
    }


    private void initAndStartFindByDialog(FindService service) {
        SharedObject<Good> sharedGood = new SharedObject<>();
        Parent parent = FindDialog.createFindDialog(sharedGood, service);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setTitle("Діалог пошуку");
        stage.setOnCloseRequest(windowEvent -> {
            if (sharedGood.getObject() != null) {
                IncomeGoodsTableCell cell = new IncomeGoodsTableCell();
                cell.setCode(String.valueOf(sharedGood.getObject().getId()));
                cell.setName(sharedGood.getObject().getName());
                goods.add(cell);
            }
        });
        stage.show();

    }


    private void initBarcodeHandler() {
        pane.addEventFilter(KeyEvent.ANY, ShopAppContext.getInstance().createBarcodeHandler(barcode -> {
            log.info(barcode);
            goods.add(new IncomeGoodsTableCell(goodsService.findGoodByBarcode(barcode)));

        }));
    }

    private void initFocusHandlers() {
        focus.setGraphic(new ImageView("/imgs/redB.png"));
        new FocusListenersManager((a) -> {
            if (a) {
                focus.setGraphic(new ImageView("/imgs/greenB.png"));
            } else {
                focus.setGraphic(new ImageView("/imgs/redB.png"));
            }
        }, table, addById, addByName, focus, pay, delete, splitPane);
        pane.visibleProperty().addListener((observableValue, aBoolean, aBoolean2) -> Platform.runLater(table::requestFocus));
    }


}
