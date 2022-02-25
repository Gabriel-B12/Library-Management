
package library.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.data.Carte;
import library.data.User;

/**
 *
 * @author Gaby
 */
public class DataHelper {

    /**
     * Metoda verifica daca exista un anumit user in data de baza
     * @param id
     * @return
     */
    public static boolean existaUser(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM UTILIZATOR WHERE username=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * Metoda folosita pentru a insera un user nou in baza de date
     * @param user
     * @return
     */
    public static boolean insertNewUser(User user) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO UTILIZATOR(username,nume,prenume,email,mobile,password,isAdmin,citit) VALUES(?,?,?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getNume());
            statement.setString(3, user.getPrenume());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getMobile());
            statement.setString(6, user.getPassword());
            statement.setBoolean(7, user.getIsAdmin());
            statement.setString(8, user.getCitit());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * Metoda verifica daca exista o anumita carte in baza de date
     * @param id
     * @return
     */
    public static boolean existaCarte(int id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM CARTE WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * Metoda verifica daca exista o anumita carte in baza de date
     * @param id
     * @return
     */
    public static boolean existaCarte(String id) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM CARTE WHERE isbn=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * metoda verifica daca exista un anumit imprumut in baza de date
     * @param bookID
     * @param userID
     * @return
     */
    public static boolean existaImprumut(int bookID,int userID) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM IMPRUMUT WHERE bookID=? and userId=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setInt(1, bookID);
            stmt.setInt(2, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * Metoda verifica daca exista o anumita cerere in baza de date
     * @param bookID
     * @param userID
     * @return
     */
    public static boolean existaCerere(int bookID,int userID) {
        try {
            String checkstmt = "SELECT COUNT(*) FROM CERERE WHERE bookID=? and userId=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setInt(1, bookID);
            stmt.setInt(2, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     * Metoda folosita pentru a insera o carte noua
     * @param carte
     * @return
     */
    public static boolean insertNewBook(Carte carte) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO CARTE(isbn,titlu,autor,editura,anPublicare,nrPag) VALUES(?,?,?,?,?,?)");
            statement.setString(1, carte.getIsbn());
            statement.setString(2, carte.getTitlu());
            statement.setString(3, carte.getAutor());
            statement.setString(4, carte.getEditura());
            statement.setInt(5, carte.getAnPublicare());
            statement.setInt(6, carte.getPag());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
        
}
