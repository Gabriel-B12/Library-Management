
package library.ui.userMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.alert.AlertMaker;
import static library.alert.AlertMaker.showErrorMessage;
import static library.alert.AlertMaker.showSimpleAlert;
import static library.alert.AlertMaker.styleAlert;
import library.data.Carte;
import library.data.Cerere;
import static library.data.User.utilizator;
import static library.database.DataHelper.existaCarte;
import static library.database.DataHelper.existaCerere;
import static library.database.DataHelper.existaImprumut;
import library.database.DatabaseHandler;

/**
 *
 * @author Gaby
 */
public class UserController implements Initializable {
    
    ObservableList<Carte> list = FXCollections.observableArrayList();
    ObservableList<Cerere> list1 = FXCollections.observableArrayList();
    
    @FXML
    private JFXTextField searchBookInput;
    @FXML
    private ChoiceBox<String> choiceBoxBook;
    @FXML
    private TableView<Carte> tableView;
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
    private DatabaseHandler handler;


    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox rightVBox;
    @FXML
    private TableColumn<Carte, Integer> idCol;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private VBox vbox2;
    @FXML
    private HBox mainBox;
    @FXML
    private Text mainText1;
    @FXML
    private Text mainText2;
    @FXML
    private Text mainText3;
    @FXML
    private JFXButton profileButton;
    @FXML
    private JFXButton logoutButton;
    @FXML
    private VBox vbox;
    @FXML
    private JFXTextField bookIDInput;
    @FXML
    private TableView<Cerere> tableViewR;
    @FXML
    private TableColumn<Cerere, Integer> idCol1;
    @FXML
    private TableColumn<Cerere, String> isbnCol1;
    @FXML
    private TableColumn<Cerere, String> titluCol1;
    @FXML
    private TableColumn<Cerere, String> userCol;
    @FXML
    private TableColumn<Cerere, String> numeCol;
    @FXML
    private TableColumn<Cerere, String> dataCol;
    @FXML
    private TableColumn<Cerere, String> statusCol;
    private DatePicker dataR;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        handler = DatabaseHandler.getInstance();
        initCol();
        loadTableData();
        setCalendar();
        setData();
        setData1();

        mainBox.setStyle("-fx-background-color:  #211f30;");
        JFXDepthManager.setDepth(mainBox,2);
        JFXDepthManager.setDepth(vbox,2);

    }
    
    private void setCalendar(){
        dataR=new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(dataR);
        Node popupContent = datePickerSkin.getPopupContent();
        

        rightVBox.getChildren().add(popupContent);
        rightVBox.setSpacing(20); 

    }
    
    private void setData(){
        choiceBoxBook.getItems().addAll("All","ISBN","Title","Author","Publisher");
        choiceBoxBook.setValue("All");
        
        mainText1.setText("Logged as "+utilizator.getUsername());
        
        anchorPane2.setPrefHeight(200);
        String select="SELECT i.id, i.bookID, i.userID,i.dataImprumut,i.dataR, c.titlu,c.autor FROM IMPRUMUT i\n"
                + "LEFT OUTER JOIN Carte c\n"
                + "ON c.id = i.bookID where i.userID="+utilizator.getId();
        ResultSet rs = handler.execQuery(select);
        
        int nr=0;
        try {
            while (rs.next()) {
                nr++;
                int id = rs.getInt("id");
                String bookTitle = rs.getString("titlu");
                String bookAuthor = rs.getString("autor");
                Timestamp data = rs.getTimestamp("dataImprumut");
                Date data1 = rs.getDate("dataR");
                
                
                VBox box = new VBox();
                box.setStyle("-fx-background-color:  #211f30;-fx-font-size: 12pt;");
                Text text = new Text("Book's ID: "+id);
                Text text1 = new Text("Book's Title: "+bookTitle);
                Text text2 = new Text("Author: "+bookAuthor);
                Text text3 = new Text("Data: "+formatDateTimeString(data));
                Text text4 = new Text("Return before: "+formatDateTimeString(data1));
                
                text.setStyle("-fx-fill: #FFFF8D;");
                text1.setStyle("-fx-fill: #FFFF8D;");
                text2.setStyle("-fx-fill: #FFFF8D;");
                text3.setStyle("-fx-fill: #FFFF8D;");
                text4.setStyle("-fx-fill: #FFFF8D;");
                
                box.getChildren().add(text);
                box.getChildren().add(text1);
                box.getChildren().add(text2);
                box.getChildren().add(text3);
                box.getChildren().add(text4);

              
                box.setPadding(new Insets(1,5,0,5));
                JFXDepthManager.setDepth(box,2);
                VBox.setMargin(box,new Insets(20,20,0,20));
        
                vbox2.getChildren().add(box);
            }
            
            int pref=145;
            if(nr>2){
                pref*=nr;
                anchorPane2.setPrefHeight(pref);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        mainText2.setText("Carti nereturnate: "+nr);
    }
    private static String formatDateTimeString(Date date) {
        SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("dd-MM-yyyy");
        return DATE_FORMAT.format(date);
    }
    
    
    
    private void setData1(){
        
        vbox1.getChildren().clear();
        int prefHeight=100;
        
        if(utilizator.getCitit()==null){
            mainText3.setText("Carti citite: 0");
            return;
        }
        String[] arr = utilizator.getCitit().split(" ");    
        arr = new HashSet<String>(Arrays.asList(arr)).toArray(new String[0]);
        int found=0;
        for (String arr1 : arr) {
            
            
            String select = "Select * from CARTE where id=" + Integer.parseInt(arr1);
            ResultSet rs = handler.execQuery(select);
            
            try {
                if(rs.next()){
                    found++;
                    VBox box = new VBox();
                    box.setStyle("-fx-background-color:  #211f30;-fx-font-size: 12pt;");
                    
                    String bookTitle = rs.getString("titlu");
                    String bookAuthor = rs.getString("autor");
                    String bookPublisher = rs.getString("editura");
                
                    Text text1 = new Text("Book's Title: "+bookTitle);
                    Text text2 = new Text("Author: "+bookAuthor);
                    Text text3 = new Text("Publisher: "+bookPublisher);
                    
                    text1.setStyle("-fx-fill: #FFFF8D;");
                    text2.setStyle("-fx-fill: #FFFF8D;");
                    text3.setStyle("-fx-fill: #FFFF8D;");
            
                    box.getChildren().add(text1);
                    box.getChildren().add(text2);
                    box.getChildren().add(text3);
                    
                    box.setPadding(new Insets(1,5,0,5));
                    JFXDepthManager.setDepth(box,2);
                    VBox.setMargin(box,new Insets(20,20,0,20));
                    vbox1.getChildren().add(box);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            box.getChildren().add(new JFXTextArea());
            
        }
        prefHeight*=found;
        if(found<=2)
           anchorPane1.setPrefHeight(200);
        else
            anchorPane1.setPrefHeight(prefHeight);
        mainText3.setText("Carti citite: "+found);
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        loadWindow("/library/ui/settings/settings.fxml","Settings");
    }
    

    
    private void loadWindow(String loc, String title){
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
        list.clear();
        ResultSet rs = handler.execQuery(query);
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
        if(list.isEmpty())
            tableView.setPlaceholder(new Label("No book found!"));
        tableView.setItems(list);
    }
    
    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titluCol.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        edituraCol.setCellValueFactory(new PropertyValueFactory<>("editura"));
        anCol.setCellValueFactory(new PropertyValueFactory<>("anPublicare"));
        pagCol.setCellValueFactory(new PropertyValueFactory<>("pag"));
        
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol1.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        titluCol1.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        numeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void loadOut(ActionEvent event) {
        Stage stage = (Stage) mainText1.getScene().getWindow();
        stage.close();
        loadWindow("/library/ui/login/login.fxml","Login");
    }

    @FXML
    private void loadRequest(ActionEvent event) {
        String bookID = bookIDInput.getText();
        
        if(bookID.isEmpty() ){
            showSimpleAlert("Info","Please input data first.");
            return;
        }
        if(!existaCarte(Integer.parseInt(bookID))){
            showSimpleAlert("Info","Please check data and try again (wrong book id).");
            return;
        }
        
        if(existaCerere(Integer.parseInt(bookID),utilizator.getId())){
            showSimpleAlert("Info","The request is already sent.");
            return;
        }
          
        if(!existaImprumut(Integer.parseInt(bookID),utilizator.getId())){
           showSimpleAlert("Info","Nu exista niciun impumut care include datele cerute!");
           return;
        }
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Request Book");
        alert.setContentText("Do you want to send the request?");
        styleAlert(alert);
        Optional<ButtonType> answer = alert.showAndWait();
        
        LocalDate data=dataR.getValue();

        if (answer.get() == ButtonType.OK) {
            String str = "insert into CERERE(bookID,userID,data,status) values( "
                    +"'"+ bookID +"',"
                    +"'"+ utilizator.getId() +"',"
                    +"'"+ data.toString()+"',"
                    +"'Waiting' )";
            if(handler.execAction(str)){
                showSimpleAlert("Success","Book request complete");
            }else{
                showErrorMessage("Error","Book request failed");
            }
        } else {
            AlertMaker.showSimpleAlert("Book request cancelled", "Book request process cancelled");
        }
        loadTableData();
    }

    private void loadTableData(){
        list1.clear();
        String select="SELECT a.id,a.data,a.status,u.username, u.nume,u.prenume, c.titlu,c.isbn FROM CERERE a\n"
                + "LEFT OUTER JOIN Utilizator u\n"
                + "ON u.id = a.userID\n"
                + "LEFT OUTER JOIN Carte c\n"
                + "ON c.id = a.bookID where userID='"+utilizator.getId()+"'";
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
                list1.add(imp);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        tableViewR.setItems(list1);
    }

    @FXML
    private void loadDeleteRequest(ActionEvent event) {
        Cerere selected = tableViewR.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertMaker.showErrorMessage("No request selected", "Please select a request first.");
            return;
        }
        
        Boolean result = DatabaseHandler.getInstance().deleteCerere(selected);
        if (result) {
            AlertMaker.showSimpleAlert("Request deleted", "Request for book "+selected.getBookName() + " was deleted successfully.");
            list1.remove(selected);
        } else {
            AlertMaker.showSimpleAlert("Failed","Failed to delete request wit ID "+selected.getId() + " !");
        }
        loadTableData();
        
    }

    @FXML
    private void loadAddRead(ActionEvent event) {
        Carte selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book first.");
            return;
        }
        utilizator.setCitit(utilizator.getCitit()+" "+selected.getId());
        
        handler.updateUser(utilizator);
        setData1();
    }

  
    
    

    
}
