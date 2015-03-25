package org.ua.shop.ui.dialog.pay;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.ua.shop.ui.utils.CloseUtils;


import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yaroslav.Gryniuk
 */
public class PayController implements Initializable {
    private BigDecimal total;
    @FXML
    private Label totalLabel;
    @FXML
    private TextField inputSum;
    @FXML
    private Label diffLabel;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button payButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> inputSum.requestFocus());
        initChangeHandler();
    }

    private void initChangeHandler() {
        inputSum.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            try {
                diffLabel.setText(new BigDecimal(inputSum.getText()).subtract(total).toString());
                if (keyEvent.getCode().equals(KeyCode.ENTER)){
                    payButton.fireEvent(new ActionEvent());
                }
            } catch (NumberFormatException e) {
            }
        });

    }


    public void setTotal(BigDecimal total) {
        this.total = total;
        totalLabel.setText(total.toString());
    }

    public void payAction(ActionEvent actionEvent) {
        CloseUtils.close(actionEvent);
    }
}
