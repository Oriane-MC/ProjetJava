package Projet;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tour de jeu au cours d'une partie.
 * <p>
 * Cette classe gère :
 * <ul>
 *     <li>Les joueurs participant au tour</li>
 *     <li>Le numéro du tour</li>
 *     <li>L’ordre dans lequel les joueurs vont jouer</li>
 * </ul>
 * L'ordre est déterminé selon la carte visible que chaque joueur a mise dans son offre.
 * Les règles de priorité des cartes sont :
 * <ul>
 *     <li>Valeur la plus élevée en premier</li>
 *     <li>En cas d'égalité, priorité à la couleur selon la hiérarchie :
 *         <b>Pique > Trèfle > Carreau > Coeur > Joker</b>
 *     </li>
 * </ul>
 */
public class Tour {

   // ATTRIBUTS 
    private List<Joueur> listeJoueurs;  // Liste des joueurs participant au tour
    private int numeroTour; // Numéro du tour courant 

    // Constructeur
    /**
     * Constructeur d'un tour de jeu
     *
     * @param listeJoueurs  liste des joueurs participant au tour
     * @param numeroTour    numéro du tour actuel
     */
    public Tour(List<Joueur> listeJoueurs, int numeroTour) {
        this.listeJoueurs = new ArrayList<>(listeJoueurs); 
        this.numeroTour = numeroTour;
    }

    // Getters
    
    /**
     * @return liste des joueurs participant au tour 
     */
    public List<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    /**
     * @return numéro du tour
     */
    public int getNumeroTour() {
        return numeroTour;
    }

    /**
     * Détermine l'ordre des joueurs selon la carte visible de leur offre.
     * Règles de tri  :
     * - La carte visible la plus forte joue en premier
     * - En cas d'égalité, tri selon la hiérarchie des couleurs : Pique > Trèfle > Carreau > Coeur > Joker
     *Si un joueur n'a pas d'offre, il est considéré comme ayant une carte visible nulle.
     */
    public void determinerOrdreJoueurs() {
    	//sort modif la liste automatiquement :) i hope
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
     * * @param c carte dont la couleur doit être évaluée
     * @return un entier représentant la "force" de la couleur
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
     * Affiche l'ordre actuel des joueurs pour ce tour .
     * Affiche le nom du joueur et sa carte visible.
     * Format :  NomJoueur (carte visible : 8 de Pique)
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
