package minibibliotheque;

import javax.swing.*;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Démarrer directement sur l'interface vendeur
            MiniBibliothequeUI app = new MiniBibliothequeUI();
            app.setVisible(true);
        });
    }
}