
package library.ui.adminMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.alert.AlertMaker;
import static library.alert.AlertMaker.showErrorMessage;
import static library.alert.AlertMaker.showSimpleAlert;
import static library.alert.AlertMaker.styleAlert;
import library.data.Cerere;
import static library.database.DataHelper.existaImprumut;
import library.database.DatabaseHandler;
import library.ui.listBook.ListBookController;
import library.ui.listImprumut.ListImprumutController;
import library.ui.listUser.ListUserController;
import library.ui.userMain.UserController;

/**
 *
 * @author Gaby
 */
public class AdminController implements Initializable {

    private DatabaseHandler handler;
    private PieChart bookChart;
    private PieChart memberChart;
    private ObservableList<Cerere> list = FXCollections.observableArrayList();
    @FXML
    private HBox book_info;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text memberMobile;
    @FXML
    private JFXButton btnIssue;
    @FXML
    private Button addUserButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button listUsersButton;
    @FXML
    private Button listBooksButton;
    @FXML
    private Button imprumuturiButton;
    @FXML
    private Button settingsButton;
    @FXML
    private JFXTextField bookISBNInput;
    @FXML
    private HBox user_info;
    @FXML
    private JFXTextField usernameInput;
    @FXML
    private Text userName;
    @FXML
    private StackPane bookInfoContainer;
    @FXML
    private Text bookStatus;
    @FXML
    private StackPane memberInfoContainer;
    @FXML
    private VBox search1;
    @FXML
    private VBox search2;
    @FXML
    private VBox search3;
    @FXML
    private JFXTextField searchBookInput;
    @FXML
    private ChoiceBox<String> choiceBoxBook;
    @FXML
    private JFXTextField searchUserInput;
    @FXML
    private ChoiceBox<String> choiceBoxUser;
    @FXML
    private JFXTextField searchImprumutInput;
    @FXML
    private ChoiceBox<String> choiceBoxImprumut;
    @FXML
    private VBox graphBox;
    @FXML
    private TableView<Cerere> tableViewR;
    @FXML
    private TableColumn<Cerere, Integer> idCol;
    @FXML
    private TableColumn<Cerere, String> isbnCol;
    @FXML
    private TableColumn<Cerere, String> titluCol;
    @FXML
    private TableColumn<Cerere, String> userCol;
    @FXML
    private TableColumn<Cerere, String> numeCol;
    @FXML
    private TableColumn<Cerere, String> dataCol;
    @FXML
    private TableColumn<Cerere, String> statusCol;
    
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFocus();
        handler = DatabaseHandler.getInstance();
        initCol();
        loadTableData();
        JFXDepthManager.setDepth(book_info,2);
        JFXDepthManager.setDepth(user_info,2);
        JFXDepthManager.setDepth(search1,2);
        JFXDepthManager.setDepth(search2,2);
        JFXDepthManager.setDepth(search3,2);
        choiceBoxBook.getItems().addAll("All","ISBN","Title","Author","Publisher");
        choiceBoxBook.setValue("All");
        choiceBoxUser.getItems().addAll("All","Username","Name","Email","Mobile");
        choiceBoxUser.setValue("All");
        choiceBoxImprumut.getItems().addAll("All","ISBN","Book Title","User Name");
        choiceBoxImprumut.setValue("All");
        initGraphs();
    }    

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        titluCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    void setFocus(){
        addUserButton.setFocusTraversable(false);
        addBookButton.setFocusTraversable(false);
        listUsersButton.setFocusTraversable(false);
        listBooksButton.setFocusTraversable(false);
        imprumuturiButton.setFocusTraversable(false);
        settingsButton.setFocusTraversable(false);
    }

    @FXML
    private void loadAddUser(ActionEvent event) {
        loadWindow("/library/ui/adduser/adduser.fxml","Add User");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/library/ui/addbook/addbook.fxml","Add Book");
    }
    
    @FXML
    private void loadSettings(ActionEvent event) {
        loadWindow("/library/ui/settings/settings.fxml","Settings");
    }

    @FXML
    private void loadListUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listUser/listUser.fxml"));
            Parent parent = loader.load();

            ListUserController controller = (ListUserController) loader.getController();
            controller.load("Select * from UTILIZATOR");
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("List Users");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadListBooks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listBook/listBook.fxml"));
            Parent parent = loader.load();

            ListBookController controller = (ListBookController) loader.getController();
            controller.load("Select * from CARTE");
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("List Books");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
    private void loadImprumuturi(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listImprumut/listImprumut.fxml"));
            Parent parent = loader.load();

            ListImprumutController controller = (ListImprumutController) loader.getController();
            String select="SELECT i.id, i.bookID, i.userID,i.dataImprumut,u.username, u.nume,u.prenume, c.titlu,c.isbn FROM IMPRUMUT i\n"
                + "LEFT OUTER JOIN Utilizator u\n"
                + "ON u.id = i.userID\n"
                + "LEFT OUTER JOIN Carte c\n"
                + "ON c.id = i.bookID ";
            controller.load(select);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Imprumuturi");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    void loadWindow(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearBookCache(){
        bookName.setText("");
        bookAuthor.setText("");
        
    }
    void clearMemberCache(){
        userName.setText("");
        memberMobile.setText("");
        
    }
    
    @FXML
    private void loadBookInfo(ActionEvent event) {
        
        clearBookCache();
        String isbn = bookISBNInput.getText();
        if(isbn.isEmpty()){
            bookName.setText("Please enter data first. ");
            return;
        }
        String qu = "SELECT * FROM CARTE WHERE id = '"+ Integer.parseInt(isbn) +"'";
        ResultSet rs = handler.execQuery(qu);
        Boolean flag = false;
        
        try {
            while(rs.next()){
                String bName = rs.getString("titlu");
                String bAuthor = rs.getString("autor");
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                
                flag=true;
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(!flag){
            bookName.setText("No such book available");
        }      
    }
    @FXML
    private void loadUserInfo(ActionEvent event) {
        clearMemberCache();
        String id = usernameInput.getText();
        if(id.isEmpty()){
            userName.setText("Please enter data first. ");
            return;
        }
        String qu = "SELECT * FROM UTILIZATOR WHERE id = '"+ Integer.parseInt(id) +"'";
        ResultSet rs = handler.execQuery(qu);
        Boolean flag = false;
        
        try {
            while(rs.next()){
                String mFname = rs.getString("prenume");
                String mLname = rs.getString("nume");
                String mMobile = rs.getString("mobile");
                
                userName.setText(mLname+" "+mFname);
                memberMobile.setText(mMobile);
                
                flag=true;
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(!flag){
            userName.setText("No user found ");
        }
         
    }



    @FXML
    private void loadIssueOperation(ActionEvent event) {
        loadBookInfo(event);
        loadUserInfo(event);
        
        String userID = usernameInput.getText();
        String bookID = bookISBNInput.getText();
        
        if(userID.isEmpty() || bookID.isEmpty() ){
            showSimpleAlert("Info","Please input data first.");
            return;
        }
        if(bookName.getText().equals("No such book available") || userName.getText().equals("No user found ")){
            showSimpleAlert("Info","Please check data and try again.");
            return;
        }
        if(existaImprumut(Integer.parseInt(bookID),Integer.parseInt(userID))){
            showSimpleAlert("Info","The book with ID "+bookID+ " is already issued to user with ID "+userID);
            return;
        }
            
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Issue Book");
        alert.setContentText("Do you want to issue the book " + bookName.getText()+" to "+userName.getText() + " ?");
        styleAlert(alert);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            String str = "insert into IMPRUMUT(bookID,userID) values( "
                    +"'"+bookISBNInput.getText() +"',"
                    +"'"+usernameInput.getText() +"' )";
            if(handler.execAction(str)){
                showSimpleAlert("Success","Book Isuue complete");
            }else{
                showErrorMessage("Error","Book Issue Failed");
            }
        } else {
            AlertMaker.showSimpleAlert("Issue Book cancelled", "Issue book  process cancelled");
        }
        refreshGraphs();
        
        
    }

    @FXML
    private void loadIssueBtn(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loadIssueOperation(null);
        }
    }

    @FXML
    private void loadSearchBook(ActionEvent event) {

        if (searchBookInput.getText().isEmpty()) {
            AlertMaker.showSimpleAlert("Search Book", "Please input data");
            return;
        }
        String in = searchBookInput.getText();
        String query="";
        if(choiceBoxBook.getValue().equals("All"))
            query="Select * from CARTE where isbn  like '%"+in+"%' or titlu  like '%"+in+"%' or autor  like '%"+in+"%' or editura like '%"+in+"%'";
        if(choiceBoxBook.getValue().equals("ISBN"))
            query="Select * from CARTE where isbn  like '%"+in+"%'";
        if(choiceBoxBook.getValue().equals("Title"))
            query="Select * from CARTE where titlu  like '%"+in+"%'";
        if(choiceBoxBook.getValue().equals("Author"))
            query="Select * from CARTE where autor  like '%"+in+"%'";
        if(choiceBoxBook.getValue().equals("Publisher"))
            query="Select * from CARTE where editura  like '%"+in+"%'";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listBook/listBook.fxml"));
            Parent parent = loader.load();

            ListBookController controller = (ListBookController) loader.getController();
            controller.load(query);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Search");
            stage.setScene(new Scene(parent));
            stage.show();
            
            
            

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void loadSearchUser(ActionEvent event) {
        if (searchUserInput.getText().isEmpty()) {
            AlertMaker.showSimpleAlert("Search User", "Please input data");
            return;
        }
        String in = searchUserInput.getText();
        String query="";
        if(choiceBoxUser.getValue().equals("All"))
            query="Select * from UTILIZATOR where username like '%"+in+"%' or nume  like '%"+in+"%' or prenume  like '%"+in+"%' or email like '%"+in+"%' or mobile  like '%"+in+"%'";
        if(choiceBoxUser.getValue().equals("Username"))
            query="Select * from UTILIZATOR where username like '%"+in+"%'";
        if(choiceBoxUser.getValue().equals("Name"))
            query="Select * from UTILIZATOR where nume  like '%"+in+"%' or prenume like '%"+in+"%'";
        if(choiceBoxUser.getValue().equals("Email"))
            query="Select * from UTILIZATOR where email  like '%"+in+"%'";
        if(choiceBoxUser.getValue().equals("Mobile"))
            query="Select * from UTILIZATOR where mobile  like '%"+in+"%'";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listUser/listUser.fxml"));
            Parent parent = loader.load();

            ListUserController controller = (ListUserController) loader.getController();
            controller.load(query);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Search");
            stage.setScene(new Scene(parent));
            stage.show();
            
            
            

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void loadSearchImprumut(ActionEvent event) {
    
        if (searchImprumutInput.getText().isEmpty()) {
            AlertMaker.showSimpleAlert("Search ", "Please input data");
            return;
        }
        String in = searchImprumutInput.getText();
        String select="SELECT i.id,i.bookID, i.userID,i.dataImprumut,u.username, u.nume,u.prenume, c.titlu,c.isbn FROM IMPRUMUT i\n"
                + "LEFT OUTER JOIN Utilizator u\n"
                + "ON u.id = i.userID\n"
                + "LEFT OUTER JOIN Carte c\n"
                + "ON c.id = i.bookID ";
        String query="";
        if(choiceBoxImprumut.getValue().equals("All"))
            query = select+"where isbn like '%"+in+"%' or titlu  like '%"+in+"%' or username  like '%"+in+"%' or nume  like '%"+in+"%' or prenume  like '%"+in+"%'";
        if(choiceBoxImprumut.getValue().equals("ISBN"))
            query = select+"where isbn  like '%"+in+"%'";
        if(choiceBoxImprumut.getValue().equals("Book Title"))
            query = select+"where titlu  like '%"+in+"%'";
        if(choiceBoxImprumut.getValue().equals("User Name"))
            query = select+"where username  like '%"+in+"%' or nume  like '%"+in+"%' or prenume  like '%"+in+"%'";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/ui/listImprumut/listImprumut.fxml"));
            Parent parent = loader.load();

            ListImprumutController controller = (ListImprumutController) loader.getController();
            controller.load(query);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Search");
            stage.setScene(new Scene(parent));
            stage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void initGraphs() {
        bookChart = new PieChart(handler.getBookGraphStatistics());
        bookChart.setLegendVisible(false);
        bookChart.setMaxHeight(300);
        memberChart = new PieChart(handler.getMemberGraphStatistics());
        memberChart.setLegendVisible(false);
        memberChart.setMaxHeight(300);
        graphBox.getChildren().add(bookChart);
        graphBox.getChildren().add(memberChart);

        
    }
    private void refreshGraphs() {
        bookChart.setData(handler.getBookGraphStatistics());
        memberChart.setData(handler.getMemberGraphStatistics());
    }
    
    private static String formatDateTimeString(Date date) {
        SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        return DATE_FORMAT.format(date);
    }
    

    private void loadTableData(){
        list.clear();
        String select="SELECT a.id,a.data,a.status,u.username, u.nume,u.prenume, c.titlu,c.isbn FROM CERERE a\n"
                + "LEFT OUTER JOIN Utilizator u\n"
                + "ON u.id = a.userID\n"
                + "LEFT OUTER JOIN Carte c\n"
                + "ON c.id = a.bookID where status='Waiting'";
        ResultSet rs = handler.execQuery(select);
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username=rs.getString("username");
                String name = rs.getString("nume")+ " "+rs.getString("prenume");
                String isbn = rs.getString("isbn");
                String bookTitle = rs.getString("titlu");
                Timestamp data = rs.getTimestamp("data");
                String status = rs.getString("status");
                Cerere imp = new Cerere(id, isbn, bookTitle,username,name ,formatDateTimeString(new Date(data.getTime())), status);
                list.add(imp);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        tableViewR.setItems(list);
    }

    @FXML
    private void loadAcceptRequest(ActionEvent event) {
        Cerere selected = tableViewR.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertMaker.showErrorMessage("No request selected", "Please select a request first.");
            return;
        }
        handler.updateCerere("Accepted",selected.getId());
        
        loadTableData();
    }

    @FXML
    private void loadDenyRequest(ActionEvent event) {
        Cerere selected = tableViewR.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertMaker.showErrorMessage("No request selected", "Please select a request first.");
            return;
        }
        handler.updateCerere("Denied",selected.getId());
        
        loadTableData();
    }
    
}
