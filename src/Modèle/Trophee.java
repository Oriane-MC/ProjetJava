package Modèle;

import java.io.Serializable;
import java.util.*;

/**
 * Représente les trophées d'une partie composé de deux cartes avec leurs conditions.
 * * Chaque trophée définit des règles permettant de déterminer un gagnant
 * à partir des cartes appartenant aux joueurs.
 */
public class Trophee implements Serializable {
	
    /**
     * Liste contenant les deux cartes trophées.
     */
    private LinkedList<Carte> listTrophee;
	
    /**
     * Identifiant de sérialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit un trophée à partir de deux cartes.
     *
     * @param c1 première carte trophée
     * @param c2 seconde carte trophée
     */
    public Trophee (Carte c1, Carte c2) {
        this.listTrophee = new LinkedList<>();
        this.listTrophee.add(c1);
        this.listTrophee.add(c2);
    }
	
    /**
     * Retourne la première carte trophée.
     *
     * @return la première carte trophée
     */
    public Carte getTrophee1() {
        return this.listTrophee.get(0);
    }
	
    /**
     * Retourne la seconde carte trophée.
     *
     * @return la seconde carte trophée
     */
    public Carte getTrophee2() {
        return this.listTrophee.get(1);
    }
	
    /**
     * Retourne une carte trophée selon son indice (1 ou 2).
     *
     * @param i indice du trophée (1 ou 2)
     * @return la carte correspondante ou null si l'indice est invalide
     */
    public Carte getTrophee(int i) {
        if (i == 1 || i == 2) {
            return this.listTrophee.get(i-1);
        }
        return null;
    }
	
    /**
     * Retourne la liste des cartes trophées.
     *
     * @return la liste des trophées
     */
    public LinkedList<Carte> getListTrophee(){
        return this.listTrophee;
    }
	
    /**
     * Modifie la liste des cartes trophées.
     *
     * @param listTrophee nouvelle liste des trophées
     */
    public void setListTrophee(LinkedList<Carte> listTrophee) {
        this.listTrophee = listTrophee;
    }

    /**
     * Détermine les gagnants associés aux trophées de la partie.
     *
     * @param p la partie en cours
     * @return la liste des joueurs gagnants
     * @throws GameException si une condition de trophée est inconnue
     */
    public List<Joueur> determinerGagnant(Partie p) throws GameException {
        List<Joueur> listJ = new ArrayList<>();
    	
        for (Carte t : this.listTrophee) {
            Joueur gagnant = null;
            switch (t.getCondition()) {
                case "BestJest" -> gagnant = gagnantBestJest(p);
                case "Joker" -> gagnant = gagnantJoker(p);
                case "Majority4" -> gagnant = gagnantMajority(p, 4);
                case "Majority3" -> gagnant = gagnantMajority(p, 3);
                case "Majority2" -> gagnant = gagnantMajority(p, 2);
                case "HighestCarreau" -> gagnant = chercherGagnantCouleur(p, "carreau", true);
                case "LowestCarreau" -> gagnant = chercherGagnantCouleur(p, "carreau", false);
                case "HighestPique" -> gagnant = chercherGagnantCouleur(p, "pique", true);
                case "LowestPique" -> gagnant = chercherGagnantCouleur(p, "pique", false);
                case "HighestCoeur" -> gagnant = chercherGagnantCouleur(p, "coeur", true);
                case "LowestCoeur" -> gagnant = chercherGagnantCouleur(p, "coeur", false);
                case "HighestTrefle" -> gagnant = chercherGagnantCouleur(p, "trèfle", true);
                case "LowestTrefle" -> gagnant = chercherGagnantCouleur(p, "trèfle", false);
                case "BestJestNoJoker" -> gagnant = gagnantBestJestNoJoker(p);
                default -> throw new GameException("Condition du trophée inconnue : " + t.getCondition());
            }
            if (gagnant != null) {
                listJ.add(gagnant);
            }
        }
        return listJ;
    }

    /**
     * Méthode utilitaire privée pour chercher le gagnant d'une couleur.
     * Évite la répétition de code et gère les cas où la couleur est absente.
     */
    private Joueur chercherGagnantCouleur(Partie p, String couleur, boolean chercherPlusHaut) {
        Joueur gagnant = null;
        int valeurCible = chercherPlusHaut ? -1 : 100;

        for (Joueur j : p.getJoueur()) {
            for (Carte c : j.getDeckPossede().getCartes()) {
                if (c.getCouleur().equalsIgnoreCase(couleur) || (couleur.equals("trèfle") && c.getCouleur().equalsIgnoreCase("trefle"))) {
                    if (chercherPlusHaut && c.getValeur() > valeurCible) {
                        valeurCible = c.getValeur();
                        gagnant = j;
                    } else if (!chercherPlusHaut && c.getValeur() < valeurCible) {
                        valeurCible = c.getValeur();
                        gagnant = j;
                    }
                }
            }
        }
        return gagnant;
    }

    /**
     * Détermine le joueur ayant la majorité pour une valeur donnée.
     * * @param p la partie en cours
     * @param valeur la valeur de carte cherchée (2, 3 ou 4)
     * @return le joueur gagnant ou null si personne n'a cette valeur
     */
    public Joueur gagnantMajority(Partie p, int valeur) {
        Joueur gagnant = null;
        int maxCompte = 0;
        for (Joueur j : p.getJoueur()) {
            int compte = 0;
            for (Carte c : j.getDeckPossede().getCartes()) {
                if (c.getValeur() == valeur) compte++;
            }
            if (compte > maxCompte && compte > 0) {
                maxCompte = compte;
                gagnant = j;
            }
        }
        return gagnant;
    }

    /**
     * Détermine le joueur possédant le joker.
     *
     * @param p la partie en cours
     * @return le joueur gagnant ou null si le joker n'est pas possédé
     */
    public Joueur gagnantJoker(Partie p) {
        for (Joueur j : p.getJoueur()) {
            for (Carte c : j.getDeckPossede().getCartes()) {
                if (c.getCouleur().equalsIgnoreCase("joker")) return j;
            }
        }
        return null;
    }

    /**
     * Détermine le joueur ayant le meilleur score global (Best Jest).
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     */
    public Joueur gagnantBestJest(Partie p) {
        p.calculerScoreSansTrophees();
        return p.joueurGagnant();
    }

    /**
     * Détermine le joueur ayant le meilleur score global sans avoir le joker.
     *
     * @param p la partie en cours
     * @return le joueur gagnant ou null
     */
    public Joueur gagnantBestJestNoJoker(Partie p) {
        p.calculerScoreSansTrophees();
        Map<Joueur, Integer> scores = p.getScore();
        Joueur gagnant = null;
        int maxScore = -1000;

        for (Joueur j : p.getJoueur()) {
            boolean aJoker = false;
            for (Carte c : j.getDeckPossede().getCartes()) {
                if (c.getCouleur().equalsIgnoreCase("joker")) aJoker = true;
            }
            if (!aJoker && scores.get(j) > maxScore) {
                maxScore = scores.get(j);
                gagnant = j;
            }
        }
        return gagnant;
    }

    /**
     * Retourne une description textuelle du trophée.
     *
     * @return description du trophée
     */
    public String toString() {
        return "Trophees = " + this.getTrophee1() + " / condition : "+ this.getTrophee1().getCondition()+ " et "
                + this.getTrophee2() + " / condition : " + this.getTrophee2().getCondition();
    }
}