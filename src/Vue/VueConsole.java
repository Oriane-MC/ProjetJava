package Vue;

import java.util.Scanner;
import Modèle.Observateur;
import Modèle.Partie;
import Modèle.Joueur;
import Modèle.Partie.EtatPartie;

/**
 * Vue console du jeu.
 * <p>
 * Cette classe implémente l'interface Observateur pour être notifiée
 * des changements dans le modèle Partie. Elle implémente également
 * Runnable pour gérer la saisie utilisateur dans un thread séparé.
 */
public class VueConsole implements Observateur, Runnable { 
    
    /** Scanner pour lire les entrées utilisateur depuis la console. */
	private Scanner scanner = new Scanner(System.in);
	
    /** Référence à la partie en cours. */
    private Partie partie;

    /**
     * Méthode appelée lorsque le modèle notifie un changement.
     * Affiche le dernier message et, si la partie est terminée, les scores finaux.
     *
     * @param p la partie mise à jour
     */
    public void update(Partie p) {
        this.partie = p;
        System.out.println("\n" + "=".repeat(30));
        System.out.println("[CONSOLE] " + p.getDernierMessage());
        System.out.println("=".repeat(30));

        if (p.getEtat() == EtatPartie.FIN) {
            System.out.println("\n--- SCORES FINAUX ---");
            p.getScore().forEach((joueur, score) -> {
                System.out.println(joueur.getNom() + " : " + score + " points");
            });
        }
    }

    /**
     * Boucle du thread console.
     *
     * Tant que le thread tourne, attend les actions du joueur humain
     * et affiche les menus appropriés selon l'état de la partie.
     */
    public void run() {
        while (true) {
            // On ne demande une saisie QUE si c'est le tour d'un humain
            if (partie != null && partie.getJoueurActuel() != null 
                && !(partie.getJoueurActuel() instanceof Modèle.Virtuel)) {
                
                if (partie.getEtat() == EtatPartie.OFFRES) {
                    afficherMenuOffre(partie);
                } 
                else if (partie.getEtat() == EtatPartie.RAMASSAGE) {
                    afficherMenuRamassage(partie);
                }
            }
            try { Thread.sleep(500); } catch (InterruptedException e) {} 
        }
    }

    /**
     * Affiche le menu pour le choix de l'offre et valide la carte choisie.
     *
     * @param p la partie en cours
     */
    private void afficherMenuOffre(Partie p) {
        if (p.getCartesEnAttente().size() < 2) return;
        System.out.println("Console : 0 pour " + p.getCartesEnAttente().get(0) + ", 1 pour " + p.getCartesEnAttente().get(1));
        int choix = lireEntier(0, 1);
        p.validerChoixOffre(p.getCartesEnAttente().get(choix));
    }

    /**
     * Affiche le menu pour le ramassage et effectue l'action choisie.
     *
     * @param p la partie en cours
     */
    private void afficherMenuRamassage(Partie p) {
        System.out.println("Console : Index du joueur cible ?");
        int indexCible = lireEntier(0, p.getJoueur().size() - 1);
        System.out.println("Console : 0 (Cachée), 1 (Visible) ?");
        int type = lireEntier(0, 1);
        p.ramasserAction(p.getJoueur().get(indexCible), type == 1);
    }

    /**
     * Lit un entier compris entre min et max depuis la console.
     * Redemande tant que la valeur entrée n'est pas valide.
     *
     * @param min valeur minimale acceptée
     * @param max valeur maximale acceptée
     * @return l'entier saisi par l'utilisateur
     */
    private int lireEntier(int min, int max) {
        int choix = -1;
        while (choix < min || choix > max) {
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) { System.out.print("Nombre invalide : "); }
        }
        return choix;
    }
}