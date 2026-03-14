package minibibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    
    public void ajouterLivre(Livre livre) throws SQLException {
        String sql = "INSERT INTO livres (titre, auteur, annee_publication, isbn, disponible) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setInt(3, livre.getAnneePublication());
            pstmt.setString(4, livre.getIsbn());
            pstmt.setBoolean(5, livre.isDisponible());
            
            pstmt.executeUpdate();
        }
    }
    
    public List<Livre> getAllLivres() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres ORDER BY titre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setAnneePublication(rs.getInt("annee_publication"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setDisponible(rs.getBoolean("disponible"));
                
                livres.add(livre);
            }
        }
        
        return livres;
    }
    
    public List<Livre> rechercherLivres(String recherche) throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres WHERE titre LIKE ? OR auteur LIKE ? ORDER BY titre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + recherche + "%");
            pstmt.setString(2, "%" + recherche + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Livre livre = new Livre();
                    livre.setId(rs.getInt("id"));
                    livre.setTitre(rs.getString("titre"));
                    livre.setAuteur(rs.getString("auteur"));
                    livre.setAnneePublication(rs.getInt("annee_publication"));
                    livre.setIsbn(rs.getString("isbn"));
                    livre.setDisponible(rs.getBoolean("disponible"));
                    
                    livres.add(livre);
                }
            }
        }
        
        return livres;
    }
    
    public void supprimerLivre(int id) throws SQLException {
        String sql = "DELETE FROM livres WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}