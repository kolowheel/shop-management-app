package org.ua.shop.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wheel on 12.08.14.
 */
public class MainUIController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab t1;
    @FXML
    private Tab t2;
    @FXML
    private Tab t3;
    @FXML
    private Tab t4;
    @FXML
    private Tab t5;

    SingleSelectionModel<Tab> selectionModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectionModel = tabPane.getSelectionModel();
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke) {
        switch (ke.getCode()) {
            case F1:
                selectionModel.select(t1);
                break;
            case F2:
                selectionModel.select(t2);
                break;
            case F3:
                selectionModel.select(t3);
                break;
            case F4:
                selectionModel.select(t4);
                break;
            case F5:
                selectionModel.select(t5);
                break;
            default:break;
        }
    }

}
