
package library.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.data.User;
import library.database.DatabaseHandler;

/**
 *
 * @author Gaby
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(() -> {
            DatabaseHandler.getInstance();
        }).start();
        
    }    

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        if(uname.isEmpty() || pword.isEmpty()){
            label.setTextFill(Color.TOMATO);
            label.setText("Enter data in all fields");
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
            return;
        }
        
        try{
            String check = "select * from UTILIZATOR where username = ? and password = ?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(check);
            stmt.setString(1,uname);
            stmt.setString(2,pword);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                label.setTextFill(Color.TOMATO);
                label.setText("Enter Correct Username/Password");
                username.getStyleClass().add("wrong-credentials");
                password.getStyleClass().add("wrong-credentials");
                return;
            }else{
                int id = rs.getInt("id");
                String s1 = rs.getString("username");
                String s2 = rs.getString("nume");
                String s3 = rs.getString("prenume");
                String s4 = rs.getString("email");
                String s5 = rs.getString("mobile");
                String s6 = rs.getString("citit");
                String s7 = rs.getString("password");
                Boolean s8 = rs.getBoolean("isAdmin");
                User.utilizator=new User(id,s1,s2,s3,s4,s5,s6,s7,s8);
                closeStage();
                if(s8)
                    loadMain1();
                else
                    loadMain2();
            }    
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }
    
    private void closeStage(){
        ((Stage) username.getScene().getWindow()).close();
    }
    
    void loadMain1() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/library/ui/adminMain/admin.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    void loadMain2() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/library/ui/userMain/user.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
     
}
