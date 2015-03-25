package org.ua.shop.ui.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.ua.shop.dto.Good;

/**
 * Created by wheel on 09.08.14.
 */
public class IncomeGoodsTableCell {
    private StringProperty code = new SimpleStringProperty("0");
    private StringProperty name = new SimpleStringProperty("0");
    private StringProperty count = new SimpleStringProperty("0");
    private StringProperty org = new SimpleStringProperty("0");
    private StringProperty perItemPrice = new SimpleStringProperty("0");
    private StringProperty total = new SimpleStringProperty("0");

    public IncomeGoodsTableCell(){}
    public IncomeGoodsTableCell(Good good){
        setCode(String.valueOf(good.getCount()));
        setName(good.getName());
    }
    public StringProperty codeProperty() {
        return code;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty countProperty() {
        return count;
    }

    public StringProperty orgProperty() {
        return org;
    }

    public StringProperty perItemPriceProperty() {
        return perItemPrice;
    }

    public StringProperty totalProperty() {
        return total;
    }


    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.setValue(code);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public String getCount() {
        return count.get();
    }

    public void setCount(String count) {
        this.count.setValue(count);
    }

    public String getOrg() {
        return org.get();
    }

    public void setOrg(String org) {
        this.org.setValue(org);
    }

    public String getPerItemPrice() {
        return perItemPrice.get();
    }

    public void setPerItemPrice(String perItemPrice) {
        this.perItemPrice.setValue(perItemPrice);
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(String total) {
        this.total.setValue(total);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncomeGoodsTableCell cell = (IncomeGoodsTableCell) o;

        if (code != null ? !code.equals(cell.code) : cell.code != null) return false;
        if (count != null ? !count.equals(cell.count) : cell.count != null) return false;
        if (name != null ? !name.equals(cell.name) : cell.name != null) return false;
        if (org != null ? !org.equals(cell.org) : cell.org != null) return false;
        if (perItemPrice != null ? !perItemPrice.equals(cell.perItemPrice) : cell.perItemPrice != null) return false;
        if (total != null ? !total.equals(cell.total) : cell.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (org != null ? org.hashCode() : 0);
        result = 31 * result + (perItemPrice != null ? perItemPrice.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IncomeGoodsTableCell{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", org='" + org + '\'' +
                ", perItemPrice='" + perItemPrice + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
