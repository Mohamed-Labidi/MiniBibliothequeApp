package minibibliotheque;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class MiniBibliothequeUI extends JFrame {
    private final LivreDAO livreDAO;
    private JTable tableLivres;
    private DefaultTableModel tableModel;
    private JTextField txtRecherche, txtTitre, txtAuteur, txtAnnee, txtISBN;
    
    public MiniBibliothequeUI() {
        livreDAO = new LivreDAO();
        initUI();
        chargerLivres();
    }
    
    private void initUI() {
        setTitle("Gestion Bibliothèque");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Ajouter Livre", createAddTab());
        tabbedPane.addTab("Gérer Livres", createManageTab());
        
        add(tabbedPane);
    }
    
    private JPanel createAddTab() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Titre:"));
        txtTitre = new JTextField();
        panel.add(txtTitre);
        
        panel.add(new JLabel("Auteur:"));
        txtAuteur = new JTextField();
        panel.add(txtAuteur);
        
        panel.add(new JLabel("Année:"));
        txtAnnee = new JTextField();
        panel.add(txtAnnee);
        
        panel.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        panel.add(txtISBN);
        
        panel.add(new JLabel()); // Espace vide
        JButton btnAjouter = createStyledButton("Ajouter Livre");
        btnAjouter.addActionListener(e -> ajouterLivre());
        panel.add(btnAjouter);
        
        return panel;
    }
    
    private JPanel createManageTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Barre de recherche
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.add(new JLabel("Rechercher:"), BorderLayout.WEST);
        
        txtRecherche = new JTextField();
        txtRecherche.addActionListener(e -> rechercherLivres());
        searchPanel.add(txtRecherche, BorderLayout.CENTER);
        
        JButton btnRechercher = createStyledButton("Rechercher");
        btnRechercher.addActionListener(e -> rechercherLivres());
        searchPanel.add(btnRechercher, BorderLayout.EAST);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Tableau
        String[] colonnes = {"ID", "Titre", "Auteur", "Année", "ISBN", "Disponible"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableLivres = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableLivres);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnRafraichir = createStyledButton("Rafraîchir");
        btnRafraichir.addActionListener(e -> chargerLivres());
        
        JButton btnSupprimer = createStyledButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerLivre());
        
        JButton btnQuitter = createStyledButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnRafraichir);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnQuitter);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.LIGHT_GRAY);
        button.setFocusPainted(false);
        return button;
    }
    
    private void chargerLivres() {
        try {
            List<Livre> livres = livreDAO.getAllLivres();
            mettreAJourTable(livres);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rechercherLivres() {
        String recherche = txtRecherche.getText().trim();
        try {
            List<Livre> livres = recherche.isEmpty() ? 
                livreDAO.getAllLivres() : livreDAO.rechercherLivres(recherche);
            mettreAJourTable(livres);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mettreAJourTable(List<Livre> livres) {
        tableModel.setRowCount(0);
        for (Livre livre : livres) {
            tableModel.addRow(new Object[]{
                livre.getId(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getAnneePublication(),
                livre.getIsbn(),
                livre.isDisponible() ? "Oui" : "Non"
            });
        }
    }
    
    private void ajouterLivre() {
        if (txtTitre.getText().trim().isEmpty() || txtAuteur.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Titre et Auteur obligatoires", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int annee = 0;
            if (!txtAnnee.getText().trim().isEmpty()) {
                annee = Integer.parseInt(txtAnnee.getText().trim());
            }
            
            Livre livre = new Livre(
                txtTitre.getText().trim(),
                txtAuteur.getText().trim(),
                annee,
                txtISBN.getText().trim()
            );
            
            livreDAO.ajouterLivre(livre);
            
            // Réinitialiser les champs
            txtTitre.setText("");
            txtAuteur.setText("");
            txtAnnee.setText("");
            txtISBN.setText("");
            
            chargerLivres();
            JOptionPane.showMessageDialog(this, "Livre ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Année doit être un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur base de données: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void supprimerLivre() {
        int row = tableLivres.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(row, 0);
        String titre = (String) tableModel.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer le livre : " + titre + "?", 
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                livreDAO.supprimerLivre(id);
                chargerLivres();
                JOptionPane.showMessageDialog(this, "Livre supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur base de données: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}