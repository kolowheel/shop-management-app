package org.ua.shop.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ua.shop.api.context.ShopAppContext;

/**
 * Created by wheel on 09.08.14.
 */
public class Starter extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/loginForm.fxml";
        AnchorPane rootPane = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage.setTitle("Shop management app");
        stage.setScene(new Scene(rootPane));

        stage.show();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/configs/context.xml");
        ShopAppContext appContext = (ShopAppContext) context.getBean("shopAppContext");
        launch(args);
    }
}
