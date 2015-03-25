package org.ua.shop.ui.dialog.find;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.utils.CloseUtils;
import org.ua.shop.ui.utils.SharedObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Yaroslav.Gryniuk
 */
public class FindByController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TextField goodsName;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    private ObservableList<GoodRepr> goodReprs = FXCollections.observableArrayList();
    private List<Good> findedGoods;
    private SharedObject<Good> good;
    private FindService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<GoodRepr, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<GoodRepr, String>("name"));
        tableView.setItems(goodReprs);
    }


    public void searchAction(ActionEvent actionEvent) {
        goodReprs.clear();
        findedGoods = service.find(goodsName.getText());
        if (findedGoods != null || findedGoods.size() != 0) {
            List<GoodRepr> findedRepr = findedGoods.stream().map(a -> new GoodRepr(a.getName(), a.getId())).collect(Collectors.toList());
            goodReprs.addAll(findedRepr);
        }
    }


    public void saveAction(ActionEvent actionEvent) {
        if (tableView.getFocusModel().getFocusedIndex() != -1) {
            GoodRepr repr = goodReprs.get(tableView.getFocusModel().getFocusedIndex());
            List<Good> goods = findedGoods.stream().filter(good -> good.getId() == repr.getId()).collect(Collectors.toList());
            good.setObject(goods.get(0));
        }
        CloseUtils.close(actionEvent);
    }


    public static class GoodRepr {
        StringProperty name = new SimpleStringProperty();
        IntegerProperty id = new SimpleIntegerProperty();

        public GoodRepr(String name, int id) {
            this.name.set(name);
            this.id.setValue(id);
        }

        public GoodRepr() {
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.setValue(name);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public IntegerProperty idProperty() {
            return id;
        }
    }


    public void setService(FindService service) {
        this.service = service;
    }

    public void setGood(SharedObject<Good> good) {
        this.good = good;
    }

}
