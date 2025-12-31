package Modèle;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List;

/**
 * Représente le deck d'un joueur.
 * Un deck correspond à l'ensemble des cartes possédées par un joueur.
 */
public class Deck implements Serializable {
	
	private List<Carte> cartes; 
	private Joueur proprietaire; 
	private static final long serialVersionUID = 1L;

	
	/**
	 * Construit un deck vide associé à un joueur.
	 *
	 * @param proprietaire joueur possédant ce deck
	 */

    public Deck(Joueur proprietaire) {
        this.cartes = new ArrayList<>();
        this.proprietaire = proprietaire;
    }
    
    /**
     * Ajoute une carte au deck si elle n'est pas nulle.
     *
     * @param carte carte à ajouter
     */
    public void ajouterCarte(Carte carte) {
		if (carte != null) {
			this.cartes.add(carte);
		}
	}
    
    /**
     * Retourne une représentation textuelle du deck.
     *
     * @return description du deck
     */
    public String toString() {
		return "Deck de "+ this.proprietaire + "[ " + cartes + "]";
	}
    
    /**
     * Retourne la liste des cartes du deck.
     *
     * @return liste des cartes
     */
	public List<Carte> getCartes() {
		return this.cartes;
		}
	
	/**
	 * Retourne le joueur propriétaire du deck.
	 *
	 * @return joueur propriétaire du deck
	 */
    public Joueur getProprietaire() {
    	return this.proprietaire; 
    }
    
    /**
     * Remplace la liste des cartes du deck.
     *
     * @param cartes nouvelle liste de cartes
     */
    public void setCartes(List<Carte> cartes) {
		this.cartes = cartes;
	}

    /**
     * Définit le joueur propriétaire du deck.
     *
     * @param proprietaire nouveau propriétaire du deck
     */
	public void setProprietaire(Joueur proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
     * Retourne le nombre de cartes dans le deck.
     *
     * @return taille du deck
     */
    public int getTaille() {
        return this.cartes.size();
    }
    
    /**
     * Retire et retourne la dernière carte du deck.
     *
     * @return carte retirée, ou null si le deck est vide
     */
    public Carte piocherCarte() {
		if (this.cartes.isEmpty()) {
			return null; // Retourne null si le deck est vide
		}
		// Pioche la dernière carte et l'enlever
		return this.cartes.remove(this.cartes.size() - 1); 
	}  
}