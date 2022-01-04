
package library.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;
import library.data.Carte;
import library.data.Cerere;
import library.data.Imprumut;
import library.data.User;

/**
 *
 * @author Gaby
 */
public final class DatabaseHandler {
    
    private static DatabaseHandler handler = null;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static Connection con = null;
    private static Statement st = null;
    
    private DatabaseHandler(){
        createConnection();
        setupTabelCarte();
        setupTabelUtilizator();
        setupTabelImprumut();
        setupTabelCerere();
    }
    
    /**
     *
     * @return
     */
    public static DatabaseHandler getInstance(){
        if(handler==null){
            handler = new DatabaseHandler();
        }
        return handler;
    }
    
    void createConnection(){
        try{
            con = DriverManager.getConnection(DB_URL,"root","admin");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    void setupTabelCarte(){
        String TABLE_NAME = "CARTE";
        try{
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+TABLE_NAME+" alredy exists.");
            }else{
                st.execute("create table "+ TABLE_NAME +"("
                    +"  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                    +"  isbn varchar(50),\n"
                    +"  titlu varchar(200),\n"
                    +"  autor varchar(200),\n"
                    +"  editura varchar(100),\n"
                    +"  anPublicare int,\n "
                    +"  nrPag int"
                    +" )");
                
            }
        }catch (SQLException e){
            System.err.println( e.getMessage() + "...... setupDatabase (table CARTE)");
        }finally{
        }
   }
    
    void setupTabelUtilizator(){
        String TABLE_NAME = "UTILIZATOR";
        try{
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+TABLE_NAME+" alredy exists.");
            }else{
                st.execute("create table "+ TABLE_NAME +"("
                    +"  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
                    +"  username varchar(50) ,\n"
                    +"  nume varchar(200),\n"
                    +"  prenume varchar(200),\n"
                    +"  email varchar(100),\n"
                    +"  mobile varchar(20),\n "
                    +"  citit varchar(200),\n "
                    +"  password varchar(50),\n"
                    +"  isAdmin boolean default false"
                    +" )");
                
                st.execute("INSERT INTO UTILIZATOR (username,nume,prenume,email,mobile,password,isAdmin) VALUES ("+
                "'admin',"+
                "'-',"+
                "'-',"+
                "'-',"+
                "'-',"+
                "'admin',"+
                "true"+
                ")");
                
            }
        }catch (SQLException e){
            System.err.println(e.getMessage() +"...... setupDatabase (table UTILIZATOR)");
        }finally{
        }
        
   }
    
    
    void setupTabelImprumut(){
        String TABLE_NAME = "IMPRUMUT";
        try{
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+TABLE_NAME+" alredy exists.");
            }else{
                st.execute("CREATE TABLE "+ TABLE_NAME +"("
                    +"  id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n"
                    +"  bookID int  ,\n"
                    +"  userID int ,\n"
                    +"  dataImprumut timestamp default CURRENT_TIMESTAMP,\n"
                    +"  FOREIGN KEY (bookID) REFERENCES CARTE(id),\n"
                    +"  FOREIGN KEY (userID) REFERENCES UTILIZATOR(id)"
                    +" )");
                
            }
        }catch (SQLException e){
            System.err.println(e.getMessage() +"...... setupDatabase (table IMPRUMUT)");
        }finally{
        }
   }
    
    void setupTabelCerere(){
        String TABLE_NAME = "CERERE";
        try{
            st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+TABLE_NAME+" alredy exists.");
            }else{
                st.execute("CREATE TABLE "+ TABLE_NAME +"("
                    +"  id int NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n"
                    +"  bookID int  ,\n"
                    +"  userID int ,\n"
                    +"  data timestamp default CURRENT_TIMESTAMP,\n"
                    +"  status varchar(100),\n"
                    +"  FOREIGN KEY (bookID) REFERENCES CARTE(id),\n"
                    +"  FOREIGN KEY (userID) REFERENCES UTILIZATOR(id)"
                    +" )");
                
            }
        }catch (SQLException e){
            System.err.println(e.getMessage() +"...... setupDatabase (table CERERE)");
        }finally{
        }
   }
    
    /**
     *1
     * @param book
     * @return
     */
    public boolean deleteBook(Carte book) {
        try {
            String deleteStatement = "DELETE FROM CARTE WHERE ISBN = ?";
            PreparedStatement stmt = con.prepareStatement(deleteStatement);
            stmt.setString(1, book.getIsbn());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public boolean deleteCerere(Cerere c) {
        try {
            String deleteStatement = "DELETE FROM CERERE WHERE id= ?";
            PreparedStatement stmt = con.prepareStatement(deleteStatement);
            stmt.setInt(1, c.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param book
     * @return
     */
    public boolean updateBook(Carte book) {
        try {
            String update = "UPDATE CARTE SET TITLU=?, AUTOR=?, EDITURA=?, ANPUBLICARE=?, NRPAG=? WHERE ISBN=?";
            PreparedStatement stmt = con.prepareStatement(update);
            stmt.setString(1, book.getTitlu());
            stmt.setString(2, book.getAutor());
            stmt.setString(3, book.getEditura());
            stmt.setInt(4, book.getAnPublicare());
            stmt.setInt(5, book.getPag());
            stmt.setString(6, book.getIsbn());
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return false;
    }

    /**
     *
     * @param member
     * @return
     */
    public boolean deleteUser(User member) {
        try {
            String deleteStatement = "DELETE FROM UTILIZATOR WHERE USERNAME = ?";
            PreparedStatement stmt = con.prepareStatement(deleteStatement);
            stmt.setString(1, member.getUsername());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param member
     * @return
     */
    public boolean updateUser(User member) {
        try {
            String update = "UPDATE UTILIZATOR SET NUME=?, PRENUME=?, EMAIL=?, MOBILE=?, ISADMIN=? , PASSWORD=? ,CITIT=? WHERE USERNAME=?";
            PreparedStatement stmt = con.prepareStatement(update);
            stmt.setString(1, member.getNume());
            stmt.setString(2, member.getPrenume());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getMobile());
            stmt.setBoolean(5, member.getIsAdmin());
            stmt.setString(6, member.getPassword());
            stmt.setString(7, member.getCitit());
            stmt.setString(8, member.getUsername());
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public boolean updateCerere(String s1,int s2) {
        try {
            String update = "UPDATE CERERE SET STATUS=? WHERE ID=?";
            PreparedStatement stmt = con.prepareStatement(update);
            stmt.setString(1, s1);
            stmt.setInt(2, s2);
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    /**
     *
     * @param query
     * @return
     */
    public ResultSet execQuery(String query){
        ResultSet result;
        try{
            st = con.createStatement();
            result = st.executeQuery(query);
        }catch (SQLException ex){
            System.out.println("Exception at execQuery:dataHandler"+ex.getLocalizedMessage());
            return null;
        }finally{
        }
        return result;
    }
    
    /**
     *
     * @param qu
     * @return
     */
    public boolean execAction(String qu) {
        try{
            st = con.createStatement();
            st.execute(qu);
            return true;
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Error:"+ex.getMessage(),"Error Occured",JOptionPane.ERROR_MESSAGE);
             System.out.println("Exception at execQuery:dataHandler"+ex.getLocalizedMessage());
             return false;
        } finally {
        }
    }

    /**
     *
     * @return
     */
    public Connection getConnection() {
        return con;
    }
    
    /**
     *
     * @return
     */
    public ObservableList<PieChart.Data> getBookGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = "SELECT COUNT(*) FROM CARTE";
            String qu2 = "SELECT COUNT(*) FROM IMPRUMUT";
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Books (" + count + ")", count));
            }
            rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Issued Books (" + count + ")", count));
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }

    /**
     *
     * @return
     */
    public ObservableList<PieChart.Data> getMemberGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = "SELECT COUNT(*) FROM UTILIZATOR";
            String qu2 = "SELECT COUNT(DISTINCT userID) FROM IMPRUMUT";
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Members(" + count + ")", count));
            }
            rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Active (" + count + ")", count));
            }
            
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
    
    /**
     *
     * @param imp
     * @return
     */
    public boolean deleteImprumut(Imprumut imp) {
        try {
            String deleteStatement = "DELETE FROM IMPRUMUT WHERE id= ?";
            PreparedStatement stmt = con.prepareStatement(deleteStatement);
            stmt.setInt(1, imp.getId());
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    
}
