package org.ua.shop.ui.utils.handler.barcode;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Created by wheel on 13.08.14.
 */
public class BarCodeHandler implements EventHandler<KeyEvent> {
    private BarCodeHandlerEvent eventHandler;
    private StringBuilder builder = new StringBuilder();
    private String suffix = "J";
    private long prevCharTime = 0;
    private long delay = 100L;
    private String firstChar = "";

    public BarCodeHandler(BarCodeHandlerEvent eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            long curCharTime = System.currentTimeMillis();
            long curDelay = curCharTime - prevCharTime;
            prevCharTime = curCharTime;
            if (curDelay < delay) {
                if (keyEvent.getText().equals(suffix)) {
                    eventHandler.handle(builder.toString());
                    builder.toString();
                    builder = new StringBuilder();
                } else {
                    if (keyEvent.getCode().isDigitKey() || keyEvent.getCode().isLetterKey()) {
                        builder.insert(0, firstChar).append(keyEvent.getText());
                    }
                }
            } else {
                builder = new StringBuilder();
            }
        }
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
