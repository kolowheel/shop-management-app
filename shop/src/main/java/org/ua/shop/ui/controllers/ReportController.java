package org.ua.shop.ui.controllers;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.LocalDate;
import org.ua.shop.api.ReportService;
import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.apiimpl.dao.ReportDao;
import org.ua.shop.dto.report.GoodReport;
import org.ua.shop.ui.dto.OutcomeGoodTableCell;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by wheel on 24.08.14.
 */
public class ReportController implements Initializable {
    @FXML
    private Label totalLabel;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn barcodeColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn countColumn;
    @FXML
    private TableColumn totalColumn;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView tableView;
    private ObservableList<GoodReport> reportGoods = FXCollections.observableArrayList();
    private ReportService reportService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initService();
        initCellValueFactory();
        tableView.setItems(reportGoods);
    }

    private void initService() {
        reportService = ShopAppContext.getInstance().getReportService();
    }

    private void initCellValueFactory() {
        idColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, Integer>("id"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, String>("barcode"));
        countColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, Integer>("count"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, BigDecimal>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<GoodReport, BigDecimal>("total"));
    }


    public void generateReportAction(ActionEvent actionEvent) {
        reportGoods.clear();
        LocalDate start = new LocalDate(startDate.getValue().getYear(), startDate.getValue().getMonthValue(), startDate.getValue().getDayOfMonth());
        LocalDate end = new LocalDate(endDate.getValue().getYear(), endDate.getValue().getMonthValue(), endDate.getValue().getDayOfMonth());
        List<GoodReport> report = reportService.generateReport(start, end);
        reportGoods.addAll(report);
        BigDecimal total = reportGoods.stream().map(GoodReport::getTotal).reduce(BigDecimal::add).get();
        totalLabel.setText(total.toString());
    }

    public void saveAsExelAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Exel", "*.xls");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.setInitialFileName("shop-report-" + LocalDate.now().toString());
        buildXslReport(fileChooser.showSaveDialog(null));

    }

    private void buildXslReport(File file) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("report");
        int rowCount = 0;
        for (GoodReport report : reportGoods) {
            HSSFRow row = sheet.createRow(rowCount++);
            int columnCount = 0;
            row.createCell(columnCount++).setCellValue(report.getId());
            row.createCell(columnCount++).setCellValue(report.getBarcode());
            row.createCell(columnCount++).setCellValue(report.getName());
            row.createCell(columnCount++).setCellValue(report.getCount());
            row.createCell(columnCount++).setCellValue(report.getPrice().doubleValue());
            row.createCell(columnCount++).setCellValue(report.getTotal().doubleValue());
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
