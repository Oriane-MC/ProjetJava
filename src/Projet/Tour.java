package Projet;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tour de jeu.
 * Détermine l'ordre des joueurs pour prendre des cartes selon la carte visible de leur offre.
 */
public class Tour {

    // Liste des joueurs participant au tour
    private List<Joueur> listeJoueurs;

    // Numéro du tour
    private int numeroTour;

    // Constructeur
    public Tour(List<Joueur> listeJoueurs, int numeroTour) {
        this.listeJoueurs = new ArrayList<>(listeJoueurs); // copie pour sécurité
        this.numeroTour = numeroTour;
    }

    // Getters
    public List<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public int getNumeroTour() {
        return numeroTour;
    }

    /**
     * Détermine l'ordre des joueurs selon la carte visible de leur offre.
     * Règles :
     * - La carte visible la plus forte joue en premier
     * - En cas d'égalité, tri selon la hiérarchie des couleurs : Pique > Trèfle > Carreau > Coeur > Joker
     */
    public void determinerOrdreJoueurs() {
        listeJoueurs.sort((j1, j2) -> {

            Carte c1 = (j1.getOffre() != null) ? j1.getOffre().getCarteVisible() : null;
            Carte c2 = (j2.getOffre() != null) ? j2.getOffre().getCarteVisible() : null;

            // Valeur des cartes visibles (Joker = 0)
            int val1 = (c1 != null && !c1.getCouleur().equalsIgnoreCase("joker")) ? c1.getValeur() : 0;
            int val2 = (c2 != null && !c2.getCouleur().equalsIgnoreCase("joker")) ? c2.getValeur() : 0;

            // Tri par valeur décroissante
            if (val1 != val2) return Integer.compare(val2, val1);

            // Tri par couleur si valeurs égales
            int force1 = getForceCouleur(c1);
            int force2 = getForceCouleur(c2);

            return Integer.compare(force2, force1); // force décroissante
        });
    }

    /**
     * Retourne la force d'une couleur selon les règles : 
     * Pique > Trèfle > Carreau > Coeur > Joker
     */
    private int getForceCouleur(Carte c) {
        if (c == null) return 0;

        String couleur = c.getCouleur().toLowerCase();
        return switch (couleur) {
            case "pique" -> 4;
            case "trèfle" -> 3;
            case "carreau" -> 2;
            case "coeur" -> 1;
            case "joker" -> 0;
            default -> 0;
        };
    }


    /**
     * Affiche l'ordre actuel des joueurs pour ce tour.
     * Affiche le nom du joueur et sa carte visible.
     */
    public void afficherOrdreJoueurs() {
        System.out.println("Ordre des joueurs pour le tour " + numeroTour + " :");

        for (int i = 0; i < listeJoueurs.size(); i++) {
            Joueur j = listeJoueurs.get(i);

            Carte carteVisible = (j.getOffre() != null) ? j.getOffre().getCarteVisible() : null;
            String carteInfo = (carteVisible != null) ? 
                               carteVisible.getValeur() + " de " + carteVisible.getCouleur() : 
                               "AUCUNE OFFRE";

            System.out.println((i + 1) + ". " + j.getNom() + " (carte visible : " + carteInfo + ")");
        }
    }
}
