package Controleur;

import Modèle.Partie;
import Vue.*;

public class Lancement {
    public static void main(String[] args) {
        // 1. Modèle
        Partie partie = new Partie();

        // 2. Les Vues
        VueGraphique gui = new VueGraphique(partie);
        VueConsole console = new VueConsole();

        // 3. Enregistrement (Pattern Observer)
        partie.enregistrerObservateur(gui);
        partie.enregistrerObservateur(console);

        // 4. Lancement de l'IHM
        gui.setVisible(true);
        
        // 5. LANCEMENT DU THREAD CONSOLE (Crucial pour ne pas bloquer l'IHM)
        Thread t = new Thread(console);
        t.setDaemon(true); // Ferme la console si on ferme la fenêtre
        t.start();

        System.out.println("[SYSTÈME] Jeu initialisé : Fenêtre + Console actives.");
    }
}