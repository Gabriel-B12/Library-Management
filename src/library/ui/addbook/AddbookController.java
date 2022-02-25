
package library.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import library.alert.AlertMaker;
import library.data.Carte;
import library.database.DataHelper;
import library.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Gaby
 */
public class AddbookController implements Initializable {

    DatabaseHandler handler;
    
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private JFXTextField isbn;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXTextField anPublicare;
    @FXML
    private JFXTextField nrPag;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    
    private Boolean isInEditMode = Boolean.FALSE;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getInstance();
    }    

    @FXML
    private void addBook(ActionEvent event) {
        String isbn1 = isbn.getText();
        String title1 = title.getText();
        String author1 = author.getText();
        String publisher1 = publisher.getText();
        int anPublicare1 = Integer.parseInt(anPublicare.getText());
        int nrPag1 = Integer.parseInt(nrPag.getText());
        
        Boolean isEmpty = isbn1.isEmpty() || title1.isEmpty() || author1.isEmpty() || publisher1.isEmpty() || anPublicare1<1 || nrPag1<1;
        if(isEmpty){
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }
        if (isInEditMode) {
            handleEditOperation();
            return;
        }
        if (DataHelper.existaCarte(isbn1)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate book id", "Book with same Book ID exists.\nPlease use new ID");
            return;
        }
        
        
        Carte carte = new Carte(1,isbn1,title1,author1,publisher1,anPublicare1,nrPag1);
        boolean result = DataHelper.insertNewBook(carte);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New book added", title1 + " has been added");
            clearEntries();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new book", "Check all the entries and try again");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    private void clearEntries() {
        isbn.clear();
        title.clear();
        author.clear();
        publisher.clear();
        anPublicare.clear();
        nrPag.clear();
    }

    /**
     * Editare carte
     * @param book
     */
    public void editBook(Carte book) {
        isbn.setText(book.getIsbn());
        title.setText(book.getTitlu());
        author.setText(book.getAutor());
        publisher.setText(book.getEditura());
        anPublicare.setText(Integer.toString(book.getAnPublicare()));
        nrPag.setText(Integer.toString(book.getPag()));
        isbn.setEditable(false);
        isInEditMode = Boolean.TRUE;
        
    }
    private void handleEditOperation() {
        Carte book = new Carte(1,isbn.getText(),title.getText(),author.getText(),publisher.getText(),Integer.parseInt(anPublicare.getText()),Integer.parseInt(nrPag.getText()));
        if (handler.updateBook(book)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Success", "Update complete");
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed", "Could not update data");
        }
    }
    
}
