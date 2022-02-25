
package library.data;

/**
 *
 * @author Gaby
 */
public class User {
    
    /**
     *
     */
    public static User utilizator;
    
    int id;
    String username;
    String nume;
    String prenume;
    String email;
    String mobile;
    String citit;
    String password;
    Boolean isAdmin;

    /**
     * Constructor User
     * @param id
     * @param username
     * @param nume
     * @param prenume
     * @param email
     * @param mobile
     * @param citit
     * @param password
     * @param isAdmin
     */
    public User(int id, String username, String nume, String prenume, String email, String mobile, String citit, String password, Boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.mobile = mobile;
        this.citit = citit;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     */
    public String getCitit() {
        return citit;
    }

    /**
     *
     * @param citit
     */
    public void setCitit(String citit) {
        this.citit = citit;
    }
    
    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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
    public String getNume() {
        return nume;
    }

    /**
     *
     * @param nume
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     *
     * @return
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     *
     * @param prenume
     */
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
   
}
    
    
    

