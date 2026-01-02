package Controleur;

import Modèle.Partie;
import Vue.*;

/**
 * Classe de lancement de l'application.
 * 
 * Cette classe initialise la partie, crée les vues graphique et console,
 * enregistre ces vues comme observateurs du modèle, puis lance l'interface utilisateur.
 * Elle sert de point d'entrée principal de l'application.
 */
public class Lancement {
	
	/**
     * Point d'entrée de l'application.
     * 
     * @param args arguments passés en ligne de commande (non utilisés ici)
     */
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
        
        // 5. LANCEMENT DU THREAD CONSOLE
        Thread t = new Thread(console);
        t.setDaemon(true); // Ferme la console si on ferme la fenêtre
        t.start();

        System.out.println("[SYSTÈME] Jeu initialisé : Fenêtre + Console actives.");
    }
}