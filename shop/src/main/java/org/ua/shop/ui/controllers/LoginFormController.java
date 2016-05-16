package org.ua.shop.ui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.ua.shop.api.UserService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.ui.utils.PopUpUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yaroslav.Gryniuk
 */
public class LoginFormController implements Initializable {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;

    private UserService service;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = ShopAppContext.getInstance().getUserService();
    }

    public void login(ActionEvent actionEvent) {
        if (service.authorize(loginField.getText(), passField.getText())) {
            loadMainUI(((Node) (actionEvent.getSource())).getScene().getWindow(), loginField.getText());
        } else {
            PopUpUtils.badLoginOrPassword();
        }
    }

    private void loadMainUI(Window window, String userName) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("mainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Shop management up( " + userName + " )");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(windowEvent -> {
                loginField.setText("");
                passField.setText("");
                service.logout();
                stage.close();
                ((Stage) window).show();
            });;
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.hide();
    }


}
