package Modèle;

import java.io.Serializable;
import java.util.*;

public class PaquetCarte implements Serializable {
    
    private LinkedList<Carte> listPioche;
    private static final long serialVersionUID = 1L;

    public PaquetCarte(LinkedList<Carte> list) {
        this.listPioche = list;
    }
    
    /**
     * Pioche la première carte du paquet.
     * @return La carte retirée, ou null si vide.
     */
    public Carte piocher() {
        return this.listPioche.poll();
    }

    /**
     * Méthode requise par la classe Partie pour vérifier si le jeu peut continuer.
     * C'est cette méthode qui causait ton erreur de compilation.
     */
    public int getNombreCartes() {
        return (listPioche != null) ? listPioche.size() : 0;
    }

    public LinkedList<Carte> getListPioche() {
        return listPioche;
    }

    public void setListPioche(LinkedList<Carte> listPioche) {
        this.listPioche = listPioche;
    }
    
    /**
     * Mélange les cartes de la pioche de manière aléatoire.
     */
    public void melanger() {
        if (listPioche != null) {
            Collections.shuffle(listPioche);
        }
    }

    @Override
    public String toString() {
        return "Pioche (" + getNombreCartes() + " cartes restantes)"; 
    }
}