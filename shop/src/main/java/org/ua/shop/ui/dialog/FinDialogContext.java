package org.ua.shop.ui.dialog;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.ua.shop.ui.dialog.find.FindService;
import org.ua.shop.ui.utils.SharedObject;

/**
 * Created by wheel on 23.08.14.
 */
public class FinDialogContext<T> {
    SharedObject<T> sharedObject = new SharedObject<>();
    FindService findService;
    EventHandler<WindowEvent> eventEventHandler;

    public SharedObject<T> getSharedObject() {
        return sharedObject;
    }

    public void setSharedObject(SharedObject<T> sharedObject) {
        this.sharedObject = sharedObject;
    }

    public FindService getFindService() {
        return findService;
    }

    public void setFindService(FindService findService) {
        this.findService = findService;
    }

    public EventHandler<WindowEvent> getEventEventHandler() {
        return eventEventHandler;
    }

    public void setEventEventHandler(EventHandler<WindowEvent> eventEventHandler) {
        this.eventEventHandler = eventEventHandler;
    }
}
