/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.data;

/**
 *
 * @author Gaby
 */
public class Cerere {
    
    private Integer id;
    private String bookISBN;
    private String bookName;
    private String username;
    private String name;
    private String data;
    private String status;

    public Cerere(Integer id, String bookISBN, String bookName, String username, String name, String data, String status) {
        this.id = id;
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.username = username;
        this.name = name;
        this.data = data;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
