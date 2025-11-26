package Projet;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un tour de jeu.
 */
public class Tour {
    // Attributs
    private List<Joueur> listeJoueurs;
    private int numeroTour;

    // Constructeur
    public Tour(List<Joueur> listeJoueurs, int numeroTour) {
        this.listeJoueurs = new ArrayList<>(listeJoueurs);
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
     * - La carte visible la plus forte commence
     * - En cas d'égalité, tri selon la hiérarchie des couleurs : Pique > Trèfle > Carreau > Coeur > Joker
     */
    public void determinerOrdreJoueurs() {
        listeJoueurs.sort((j1, j2) -> {
            Carte c1 = (j1.getOffre() != null) ? j1.getOffre().getCarteVisible() : null;
            Carte c2 = (j2.getOffre() != null) ? j2.getOffre().getCarteVisible() : null;

            // Valeur des cartes visibles (Joker = 0)
            int val1 = (c1 != null && c1.getCouleur() != Carte.Couleur.JOKER) ? c1.getValeur() : 0;
            int val2 = (c2 != null && c2.getCouleur() != Carte.Couleur.JOKER) ? c2.getValeur() : 0;

            // Comparer les valeurs (décroissant)
            if (val1 != val2) return Integer.compare(val2, val1);

            // Egalité : comparer par hiérarchie des couleurs
            int force1 = getForceCouleur(c1);
            int force2 = getForceCouleur(c2);

            return Integer.compare(force2, force1);
        });
    }

    /**
     * Retourne la force de la couleur selon les règles : Pique > Trèfle > Carreau > Coeur > Joker
     */
    private int getForceCouleur(Carte c) {
        if (c == null) return 0;
        return switch (c.getCouleur()) {
            case PIQUE -> 4;
            case TREFLE -> 3;
            case CARREAU -> 2;
            case COEUR -> 1;
            default -> 0; // Joker
        };
    }

    /**
     * Affiche l'ordre actuel des joueurs et leur carte visible
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
