
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

    /** 
     * Constructor Cerere
     * @param id
     * @param bookISBN
     * @param bookName
     * @param username
     * @param name
     * @param data
     * @param status
     */
    public Cerere(Integer id, String bookISBN, String bookName, String username, String name, String data, String status) {
        this.id = id;
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.username = username;
        this.name = name;
        this.data = data;
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getBookISBN() {
        return bookISBN;
    }

    /**
     *
     * @param bookISBN
     */
    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    /**
     *
     * @return
     */
    public String getBookName() {
        return bookName;
    }

    /**
     *
     * @param bookName
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
}
