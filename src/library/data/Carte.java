
package library.data;

/**
 *
 * @author Gaby
 */
public class Carte {
    
    int id;
    String isbn;
    String titlu;
    String autor;
    String editura;
    int anPublicare;
    int pag;

    /** 
     * Constructor Carte
     * @param id id carte
     * @param isbn isbn-ul cartii
     * @param titlu titlul cartii
     * @param autor autorul cartii
     * @param editura editura cartii
     * @param anPublicare anul publicarii
     * @param pag numarul de pagini
     */
    

    public Carte(int id, String isbn, String titlu, String autor, String editura, int anPublicare, int pag) {
        this.id = id;
        this.isbn = isbn;
        this.titlu = titlu;
        this.autor = autor;
        this.editura = editura;
        this.anPublicare = anPublicare;
        this.pag = pag;
    }

    /**
     *
     * @return 
     **/
    public int getId() {
        return id;
    }

    /**
     *
     * @param id id-ul cartii
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     *
     * @return 
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn isbn-ul cartii
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return 
     */
    public String getTitlu() {
        return titlu;
    }

    /**
     *
     * @param titlu titlul cartii
     */
    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    /**
     *
     * @return
     */
    public String getAutor() {
        return autor;
    }

    /**
     *
     * @param autor autorul cartii
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     *
     * @return 
     */
    public String getEditura() {
        return editura;
    }

    /**
     *
     * @param editura editura cartii
     */
    public void setEditura(String editura) {
        this.editura = editura;
    }

    /**
     *
     * @return 
     */
    public int getAnPublicare() {
        return anPublicare;
    }

    /**
     *
     * @param anPublicare
     */
    public void setAnPublicare(int anPublicare) {
        this.anPublicare = anPublicare;
    }

    /**
     *
     * @return
     */
    public int getPag() {
        return pag;
    }

    /**
     *
     * @param pag
     */
    public void setPag(int pag) {
        this.pag = pag;
    }
    
    
    
}
