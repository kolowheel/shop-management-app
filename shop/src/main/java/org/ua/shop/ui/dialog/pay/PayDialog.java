package org.ua.shop.ui.dialog.pay;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Yaroslav.Gryniuk
 */
public class PayDialog {
    public static Parent createPayDialog(BigDecimal total) {
        try {
            FXMLLoader loader = new FXMLLoader(PayDialog.class.getResource("/dialog/payDialog.fxml"));
            Parent node = loader.load();
            PayController controller = loader.getController();
            controller.setTotal(total);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}