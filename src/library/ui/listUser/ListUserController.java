
package library.ui.listUser;

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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.alert.AlertMaker;
import library.data.User;
import library.database.DatabaseHandler;
import library.ui.adduser.AdduserController;

/**
 * FXML Controller class
 *
 * @author Gaby
 */
public class ListUserController implements Initializable {

    ObservableList<User> list = FXCollections.observableArrayList();
    private String qu;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> numeCol;
    @FXML
    private TableColumn<User, String> prenumeCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> mobileCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, Boolean> isAdmin;
    @FXML
    private TableColumn<User, Integer> idCol;
    
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

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeCol.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        isAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    private void loadData() {
        list.clear();

        DatabaseHandler handler = DatabaseHandler.getInstance();
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                String email = rs.getString("email");
                String mobile = rs.getString("mobile");
                String username = rs.getString("username");
                Boolean isAdmin = rs.getBoolean("isAdmin");

                list.add(new User(id,username,nume, prenume, email, mobile,"","",isAdmin));

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        tableView.setItems(list);
    }
    
    @FXML
    private void handleMemberDelete(ActionEvent event) {
        User selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for deletion.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete " + selectedForDeletion.getNume()+" "+selectedForDeletion.getPrenume() + " ?");
        styleAlert(alert);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DatabaseHandler.getInstance().deleteUser(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("User deleted", selectedForDeletion.getNume()+" "+selectedForDeletion.getPrenume()+ " was deleted successfully.");
                list.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getNume()+" "+selectedForDeletion.getPrenume() + " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
    }
    
    @FXML
    private void handleMemberEdit(ActionEvent event) {
        User edit = tableView.getSelectionModel().getSelectedItem();
        if (edit == null) {
            AlertMaker.showErrorMessage("No member selected", "Please select a member for edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/adduser/adduser.fxml"));
            Parent parent = loader.load();

            AdduserController controller = (AdduserController) loader.getController();
            controller.editUser(edit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Member");
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
