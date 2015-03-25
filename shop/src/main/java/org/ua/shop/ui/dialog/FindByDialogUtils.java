package org.ua.shop.ui.dialog;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.dialog.find.FindDialog;
import org.ua.shop.ui.dialog.find.FindService;
import org.ua.shop.ui.dto.OutcomeGoodTableCell;
import org.ua.shop.ui.utils.SharedObject;

/**
 * Created by wheel on 23.08.14.
 */
public class FindByDialogUtils {
    public static void initAndStartFindByDialog(FinDialogContext context) {
        SharedObject<Good> sharedGood = context.getSharedObject();
        Parent parent = FindDialog.createFindDialog(sharedGood, context.getFindService());
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setTitle("Діалог пошуку");
        stage.setOnCloseRequest(context.getEventEventHandler());
        stage.show();

    }
}
