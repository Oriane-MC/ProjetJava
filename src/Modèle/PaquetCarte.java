package Modèle;

import java.io.Serializable;
import java.util.*;

/**
 * Représente un paquet de cartes (pioche) pour le jeu.
 * Permet de piocher, mélanger et gérer les cartes restantes.
 */
public class PaquetCarte implements Serializable {
    
	/** Liste des cartes présentes dans le paquet, utilisée comme pioche */
	private LinkedList<Carte> listPioche;

	/** Identifiant de version pour la sérialisation */
	private static final long serialVersionUID = 1L;

    /**
     * Crée un paquet de cartes à partir d'une liste donnée.
     *
     * @param list liste initiale de cartes
     */
    public PaquetCarte(LinkedList<Carte> list) {
        this.listPioche = list;
    }
    
    /**
     * Pioche la première carte du paquet.
     *
     * @return carte retirée, ou null si le paquet est vide
     */
    public Carte piocher() {
        return this.listPioche.poll();
    }

    /**
     * Retourne le nombre de cartes restantes dans le paquet.
     *
     * @return nombre de cartes
     */
    public int getNombreCartes() {
        return (listPioche != null) ? listPioche.size() : 0;
    }

    /**
     * Retourne la liste des cartes du paquet.
     *
     * @return liste des cartes
     */
    public LinkedList<Carte> getListPioche() {
        return listPioche;
    }

    /**
     * Remplace la liste des cartes du paquet.
     *
     * @param listPioche nouvelle liste de cartes
     */
    public void setListPioche(LinkedList<Carte> listPioche) {
        this.listPioche = listPioche;
    }
    
    /**
     * Mélange les cartes du paquet de manière aléatoire.
     */
    public void melanger() {
        if (listPioche != null) {
            Collections.shuffle(listPioche);
        }
    }

    /**
     * Retourne une représentation textuelle du paquet,
     * indiquant le nombre de cartes restantes.
     *
     * @return description du paquet
     */
    public String toString() {
        return "Pioche (" + getNombreCartes() + " cartes restantes)"; 
    }
}