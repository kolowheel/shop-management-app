package org.ua.shop.ui.dialog.find;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.utils.SharedObject;

import java.io.IOException;

/**
 * @author Yaroslav.Gryniuk
 */
public class  FindDialog {

    public static Parent createFindDialog(SharedObject<Good> object, FindService service) {
        try {
            FXMLLoader loader = new FXMLLoader(FindDialog.class.getResource("/dialog/findByDialog.fxml"));
            Parent node = loader.load();
            FindByController controller = loader.getController();
            controller.setGood(object);
            controller.setService(service);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
