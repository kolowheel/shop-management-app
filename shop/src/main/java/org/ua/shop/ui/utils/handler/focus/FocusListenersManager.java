package org.ua.shop.ui.utils.handler.focus;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yaroslav.Gryniuk
 */
public class FocusListenersManager {
    FocusDelegate delegate;
    private final Map<Node, Boolean> focusMap = new HashMap<>();

    public FocusListenersManager(FocusDelegate delegate, Node... nodes) {

        this.delegate = delegate;
        for (Node node : nodes) {
            node.focusedProperty().addListener((a, b, c) -> refreshFocus(node, c));
        }
    }

    private void refreshFocus(Node node, Boolean val) {
        focusMap.put(node, val);
        Boolean focusRes = focusMap.values().stream().reduce(false, (a, a2) -> a || a2);
        delegate.setFocus(focusRes);
    }


}
