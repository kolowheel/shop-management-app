package org.ua.shop.ui.utils;

/**
 * @author Yaroslav.Gryniuk
 */
public class SharedObject<T> {
    T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
