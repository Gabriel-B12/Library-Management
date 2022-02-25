
package library.ui.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.alert.AlertMaker;
import static library.data.User.utilizator;
import library.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Gaby
 */
public class SettingsController implements Initializable {

    @FXML
    private AnchorPane mainContainer;
    @FXML
    private JFXTextField nume;
    @FXML
    private JFXTextField prenume;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private StackPane rootPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nume.setText(utilizator.getNume());
        prenume.setText(utilizator.getPrenume());
        email.setText(utilizator.getEmail());
        mobile.setText(utilizator.getMobile());
        username.setText(utilizator.getUsername());
        password.setText(utilizator.getPassword());

        
//        username.setEditable(false);
//        password.setEditable(false);
        
        nume.setFocusTraversable(false);
        prenume.setFocusTraversable(false);
        email.setFocusTraversable(false);
        mobile.setFocusTraversable(false);
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        saveButton.setFocusTraversable(false);
        
    }    

    @FXML
    private void saveAction(ActionEvent event) {
        
        utilizator.setUsername(username.getText());
        utilizator.setNume(nume.getText());
        utilizator.setPrenume(prenume.getText());
        utilizator.setEmail(email.getText());
        utilizator.setMobile(mobile.getText());
        utilizator.setPassword(password.getText());
        
        if (DatabaseHandler.getInstance().updateUser(utilizator)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Success", "Member data updated.");
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed", "Cant update member.");
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        Stage stage = (Stage) nume.getScene().getWindow();
        stage.close();
    }
    
}
