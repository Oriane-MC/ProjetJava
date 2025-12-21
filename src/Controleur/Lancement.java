package Controleur;

import Modèle.Partie;
import Modèle.*;
import Modèle.Observateur;
import Vue.*;

public class Lancement {
    public static void main(String[] args) {
        // 1. Créer le modèle (Variante 1 par défaut, pas d'extension)
        Partie partie = new Partie();

        // 2. Créer la vue (l'IHM)
        VueGraphique gui = new VueGraphique(partie);

        // 3. Lier la vue au modèle (Pattern Observer)
        partie.enregistrerObservateur(gui);

        // 4. Afficher l'interface
        gui.setVisible(true);
        
        System.out.println("[SYSTÈME] Jeu initialisé en mode MVC.");
    }
}