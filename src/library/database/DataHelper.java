/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     *
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
     *
     * @param user
     * @return
     */
    public static boolean insertNewUser(User user) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO UTILIZATOR(username,nume,prenume,email,mobile,password,isAdmin) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getNume());
            statement.setString(3, user.getPrenume());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getMobile());
            statement.setString(6, user.getPassword());
            statement.setBoolean(7, user.getIsAdmin());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage() +"...... DataHelper");
        }
        return false;
    }
    
    /**
     *
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
     *
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
     *
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
