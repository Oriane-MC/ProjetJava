package Vue;

import java.util.Scanner;
import Modèle.Observateur;
import Modèle.Partie;
import Modèle.Joueur;
import Modèle.Partie.EtatPartie;

public class VueConsole implements Observateur, Runnable { // AJOUT de Runnable
    private Scanner scanner = new Scanner(System.in);
    private Partie partie;

    @Override
    public void update(Partie p) {
        this.partie = p; // On garde une référence pour le Thread
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

    // Le code ci-dessous tourne dans un fil séparé (Thread)
    @Override
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

    private void afficherMenuOffre(Partie p) {
        if (p.getCartesEnAttente().size() < 2) return;
        System.out.println("Console : 0 pour " + p.getCartesEnAttente().get(0) + ", 1 pour " + p.getCartesEnAttente().get(1));
        int choix = lireEntier(0, 1);
        p.validerChoixOffre(p.getCartesEnAttente().get(choix));
    }

    private void afficherMenuRamassage(Partie p) {
        System.out.println("Console : Index du joueur cible ?");
        int indexCible = lireEntier(0, p.getJoueur().size() - 1);
        System.out.println("Console : 0 (Cachée), 1 (Visible) ?");
        int type = lireEntier(0, 1);
        p.ramasserAction(p.getJoueur().get(indexCible), type == 1);
    }

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