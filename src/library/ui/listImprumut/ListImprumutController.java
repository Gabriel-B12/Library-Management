/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.listImprumut;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.alert.AlertMaker;
import library.data.Carte;
import library.data.Imprumut;
import library.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Gaby
 */
public class ListImprumutController implements Initializable {
    
    private ObservableList<Imprumut> list = FXCollections.observableArrayList();
    private String qu;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Imprumut> tableView;
    @FXML
    private TableColumn<Imprumut, Integer> idCol;
    @FXML
    private TableColumn<Imprumut, String> bookIDCol;
    @FXML
    private TableColumn<Imprumut, String> bookNameCol;
    @FXML
    private TableColumn<Imprumut, String> holderNameCol;
    @FXML
    private TableColumn<Imprumut, String> issueCol;
    @FXML
    private TableColumn<Imprumut, Integer> daysCol;
    @FXML
    private TableColumn<Imprumut, String> holderNameCol1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
    }
    
    /**
     *
     * @param q
     */
    public void load(String q){
        qu=q;
        loadData();
    }
    
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        holderNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        holderNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        issueCol.setCellValueFactory(new PropertyValueFactory<>("dataImprumut"));
        daysCol.setCellValueFactory(new PropertyValueFactory<>("days"));
        tableView.setItems(list);
    }
    
    private void loadData() {
        list.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int counter = rs.getInt("id");
                String username=rs.getString("username");
                String name = rs.getString("nume")+ " "+rs.getString("prenume");
                String isbn = rs.getString("isbn");
                String bookTitle = rs.getString("titlu");
                Timestamp data = rs.getTimestamp("dataImprumut");
                Integer days = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - data.getTime())) + 1;
                Imprumut imp = new Imprumut(counter, isbn, bookTitle,username,name ,formatDateTimeString(new Date(data.getTime())), days);
                list.add(imp);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param date
     * @return
     */
    public static String formatDateTimeString(Date date) {
        SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        return DATE_FORMAT.format(date);
    }

  

    @FXML
    private void handleReturn(ActionEvent event) {

        Imprumut selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No row selected", "Please select a row for returning option.");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Returning book");
        alert.setContentText("Are you sure want to to return book " + selectedForDeletion.getBookName() + " ?");
        styleAlert(alert);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteImprumut(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("Book returned", selectedForDeletion.getBookName() + " was returned.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed ","Failed to return book with ID"+selectedForDeletion.getId());
            }
        } else {
            AlertMaker.showSimpleAlert("Return cancelled", "Return process cancelled");
        }
    }
    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }
     private  void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/resources/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }
    
}
