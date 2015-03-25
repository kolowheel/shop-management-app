package org.ua.shop.ui.utils;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by wheel on 10.08.14.
 */
public class PopUpUtils {
    public static void showBadNumberFormatPopUp() {
        Action response = Dialogs.create()
                .title("Поганий формат числа")
                .message("Перевірте правильність вводу числа")
                .showWarning();
    }

    public static void badLoginOrPassword() {
        Action response = Dialogs.create()
                .title("Неправльний логін або пароль")
                .message("Перевірте правильність вводу логіна або пароля")
                .showWarning();
    }

    public static void notFoundByBarcodeDialog() {

    }
}
