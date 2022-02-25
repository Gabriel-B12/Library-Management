
package library.ui.listBook;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.alert.AlertMaker;
import library.database.DatabaseHandler;
import library.data.Carte;
import library.ui.addbook.AddbookController;

/**
 *
 * @author Gaby
 */
public class ListBookController implements Initializable {

    ObservableList<Carte> list = FXCollections.observableArrayList();
    private String qu;

    @FXML
    private StackPane rootPane;
    @FXML
    private TableView<Carte> tableView;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableColumn<Carte, String> isbnCol;
    @FXML
    private TableColumn<Carte, String> titluCol;
    @FXML
    private TableColumn<Carte, String> autorCol;
    @FXML
    private TableColumn<Carte, String> edituraCol;
    @FXML
    private TableColumn<Carte, Integer> anCol;
    @FXML
    private TableColumn<Carte, Integer> pagCol;
    @FXML
    private TableColumn<Carte, Integer> idCol;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
    }
    
    /**
     * Metoda care primeste query-ul
     * @param query
     */
    public void load(String query){
        qu=query;
        loadData();
    }
    
    /**
     *
     */
    public void setEditable(){
        ContextMenu value=null;
        tableView.setContextMenu(value);

        
    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titluCol.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        edituraCol.setCellValueFactory(new PropertyValueFactory<>("editura"));
        anCol.setCellValueFactory(new PropertyValueFactory<>("anPublicare"));
        pagCol.setCellValueFactory(new PropertyValueFactory<>("pag"));
    }
 
    private void loadData() {
        list.clear();

        DatabaseHandler handler = DatabaseHandler.getInstance();
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String titlu = rs.getString("titlu");
                String autor = rs.getString("autor");
                String editura = rs.getString("editura");
                int an = rs.getInt("anPublicare");
                int pag = rs.getInt("nrPag");
                list.add(new Carte(id,isbn, titlu, autor, editura, an,pag));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        tableView.setItems(list);
    }

    
    @FXML
    private void handleBookDeleteOption(ActionEvent event) {

        Carte selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete the book " + selectedForDeletion.getTitlu() + " ?");
        styleAlert(alert);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteBook(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getTitlu() + " was deleted successfully.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getTitlu() + " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
    }
    
    @FXML
    private void handleBookEditOption(ActionEvent event) {
        Carte selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/addbook/addbook.fxml"));
            Parent parent = loader.load();

            AddbookController controller = (AddbookController) loader.getController();
            controller.editBook(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.show();

            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }

    
    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/resources/dark-theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }

    
}
