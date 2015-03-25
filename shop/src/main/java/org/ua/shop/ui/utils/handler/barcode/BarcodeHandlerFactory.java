package org.ua.shop.ui.utils.handler.barcode;

/**
 * @author Yaroslav.Gryniuk
 */

public class BarcodeHandlerFactory {
    private String delay;
    private String suffix;

    public BarCodeHandler getHandler(BarCodeHandlerEvent eventHandler) {
        BarCodeHandler handler = new BarCodeHandler(eventHandler);
        handler.setDelay(Long.parseLong(delay));
        handler.setSuffix(suffix);
        return handler;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
