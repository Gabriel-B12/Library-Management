
package library.ui.adduser;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.alert.AlertMaker;
import library.data.User;
import library.database.DataHelper;
import library.database.DatabaseHandler;

/**
 *
 * @author Gaby
 */
public class AdduserController implements Initializable {

    DatabaseHandler handler;
    @FXML
    private StackPane rootPane;
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
    private CheckBox isAdmin;
    
    private Boolean isInEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       handler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void saveAction(ActionEvent event) {
        String nume1 = nume.getText();
        String prenume1 = prenume.getText();
        String email1 = email.getText();
        String mobile1 = mobile.getText();
        String username1 = username.getText();
        String password1 = password.getText();
        Boolean isAdmin1 = isAdmin.isSelected();

        Boolean flag = nume1.isEmpty() || prenume1.isEmpty() || email1.isEmpty() || mobile1.isEmpty() || username1.isEmpty() || password1.isEmpty();

        if (flag) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }
        if (isInEditMode) {
            handleUpdateUser();
            return;
        }
        
         if (DataHelper.existaUser(username1)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate member id", "Member with same id exists.\nPlease use new ID");
            return;
        }
        User user = new User(1,username1, nume1, prenume1, email1,mobile1,"",password1,isAdmin1);
        boolean result = DataHelper.insertNewUser(user);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New member added", username1 + " has been added");
            clearEntries();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new member", "Check you entries and try again.");
        }
//        Stage stage = (Stage) rootPane.getScene().getWindow();
//        stage.close();
        
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        Stage stage = (Stage) nume.getScene().getWindow();
        stage.close();
    }
    private void clearEntries() {
        nume.clear();
        prenume.clear();
        mobile.clear();
        email.clear();
        username.clear();
        password.clear();
        isAdmin.setSelected(false);
    }

    /**
     *
     * @param member
     */
    public void editUser(User member) {
        nume.setText(member.getNume());
        prenume.setText(member.getPrenume());
        email.setText(member.getEmail());
        mobile.setText(member.getMobile());
        username.setText(member.getUsername());
        password.setText("12345678");
        isAdmin.setSelected(member.getIsAdmin());
        
        username.setEditable(false);
        password.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }
    private void handleUpdateUser() {
        User member = new User(1,username.getText(), nume.getText(), prenume.getText(), email.getText(), mobile.getText(),"",password.getText(),isAdmin.isSelected());
        if (DatabaseHandler.getInstance().updateUser(member)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Success", "Member data updated.");
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed", "Cant update member.");
        }
    }
    
}
