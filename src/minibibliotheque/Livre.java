package minibibliotheque;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String isbn;
    private boolean disponible;
    
    public Livre() {}
    
    public Livre(String titre, String auteur, int anneePublication, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
        this.disponible = true;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    
    public int getAnneePublication() { return anneePublication; }
    public void setAnneePublication(int anneePublication) { 
        this.anneePublication = anneePublication; 
    }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { 
        this.disponible = disponible; 
    }
    
    @Override
    public String toString() {
        return titre + " par " + auteur + " (" + anneePublication + ")";
    }
}