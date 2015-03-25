package org.ua.shop.ui.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.ua.shop.dto.Good;

import java.math.BigDecimal;

/**
 * @author Yaroslav.Gryniuk
 */
public class OutcomeGoodTableCell {
    StringProperty code = new SimpleStringProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty count = new SimpleStringProperty();
    StringProperty perItemPrice = new SimpleStringProperty();
    StringProperty total = new SimpleStringProperty();
    StringProperty barcode = new SimpleStringProperty();

    public OutcomeGoodTableCell() {
    }

    public OutcomeGoodTableCell(Good good) {
        setCode(String.valueOf(good.getId()));
        setName(good.getName());
        setPerItemPrice(good.getCurrentPrice().toString());
        setTotal(good.getCurrentPrice().toString());
        setBarcode(good.getBarCode());
        setCount("1");
    }

    public String getBarcode() {
        return barcode.get();
    }

    public StringProperty barcodeProperty() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode.set(barcode);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCount() {
        return count.get();
    }

    public StringProperty countProperty() {
        return count;
    }

    public void setCount(String count) {
        if (count != null && perItemPrice != null) {
            setTotal(new BigDecimal(count).multiply(new BigDecimal(getPerItemPrice())).toString());
        }
        this.count.set(count);
    }

    public String getPerItemPrice() {
        return perItemPrice.get();
    }

    public StringProperty perItemPriceProperty() {
        return perItemPrice;
    }

    public void setPerItemPrice(String perItemPrice) {
        this.perItemPrice.set(perItemPrice);
    }

    public String getTotal() {
        return total.get();
    }

    public StringProperty totalProperty() {
        return total;
    }

    public void setTotal(String total) {

        this.total.set(total);
    }
}
