
package library.ui.userMain;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
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
import library.data.Carte;
import static library.data.User.utilizator;
import library.database.DatabaseHandler;

/**
 *
 * @author Gaby
 */
public class UserController implements Initializable {
    
    ObservableList<Carte> list = FXCollections.observableArrayList();
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        handler = DatabaseHandler.getInstance();
        initCol();
        setCalendar();
        setData();
        setData1();

        mainBox.setStyle("-fx-background-color:  #211f30;");
        JFXDepthManager.setDepth(mainBox,2);

    }
    
    private void setCalendar(){
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();

        rightVBox.getChildren().add(popupContent);
        rightVBox.setSpacing(20); 

    }
    
    private void setData(){
        choiceBoxBook.getItems().addAll("All","ISBN","Title","Author","Publisher");
        choiceBoxBook.setValue("All");
        
        mainText1.setText("Logged as "+utilizator.getUsername());
        
        anchorPane2.setPrefHeight(200);
        String select="SELECT i.id, i.bookID, i.userID,i.dataImprumut, c.titlu,c.autor FROM IMPRUMUT i\n"
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
                Integer days = Math.toIntExact(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - data.getTime())) + 1;
                
                VBox box = new VBox();
                box.setStyle("-fx-background-color:  #211f30;-fx-font-size: 12pt;");
            
                Text text1 = new Text("Book's Title: "+bookTitle);
                Text text2 = new Text("Author: "+bookAuthor);
                Text text3 = new Text("Data: "+formatDateTimeString(data));
                
                text1.setStyle("-fx-fill: #FFFF8D;");
                text2.setStyle("-fx-fill: #FFFF8D;");
                text3.setStyle("-fx-fill: #FFFF8D;");
                
                
                box.getChildren().add(text1);
                box.getChildren().add(text2);
                box.getChildren().add(text3);

              
                box.setPadding(new Insets(1,5,0,5));
                JFXDepthManager.setDepth(box,2);
                VBox.setMargin(box,new Insets(20,20,0,20));
        
                vbox2.getChildren().add(box);
            }
            
            int pref=75;
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
        SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        return DATE_FORMAT.format(date);
    }
    
    private void setData1(){
        
        int prefHeight=75;
        
        if(utilizator.getCitit()==null){
            mainText3.setText("Carti citite: 0");
            return;
        }
        String[] arr = utilizator.getCitit().split(" ");    

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
                
                    Text text1 = new Text("Book's Title: "+bookTitle);
                    text1.setStyle("-fx-fill: #FFFF8D;");
            
                    Text text2 = new Text("Author      :"+bookAuthor);
                    text2.setStyle("-fx-fill: #FFFF8D;");
            
                    box.getChildren().add(text1);
                    box.getChildren().add(text2);
                    
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
    }

    @FXML
    private void loadOut(ActionEvent event) {
        Stage stage = (Stage) mainText1.getScene().getWindow();
        stage.close();
        loadWindow("/library/ui/login/login.fxml","Login");
    }

    
    

    
}
