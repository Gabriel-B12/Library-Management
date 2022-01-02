
package library.data;

/**
 *
 * @author Gaby
 */
public class Imprumut {
    
    private Integer id;
    private String bookISBN;
    private String bookName;
    private String username;
    private String name;
    private String dataImprumut;
    private Integer days;

    /**
     *
     * @param id
     * @param bookISBN
     * @param bookName
     * @param username
     * @param name
     * @param dataImprumut
     * @param days
     */
    public Imprumut(Integer id, String bookISBN, String bookName, String username, String name, String dataImprumut, Integer days) {
        this.id = id;
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.username = username;
        this.name = name;
        this.dataImprumut = dataImprumut;
        this.days = days;
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
    public String getDataImprumut() {
        return dataImprumut;
    }

    /**
     *
     * @param dataImprumut
     */
    public void setDataImprumut(String dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    /**
     *
     * @return
     */
    public Integer getDays() {
        return days;
    }

    /**
     *
     * @param days
     */
    public void setDays(Integer days) {
        this.days = days;
    }
    
    
        
        

        
}