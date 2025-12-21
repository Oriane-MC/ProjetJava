package Modèle;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List;

/**
 * Représente un paquet de cartes (deck) utilisé dans le jeu.
 * Il permet d'ajouter des cartes, d'en retirer et de piocher.
 */
public class Deck implements Serializable {
	
	@Override
	public String toString() {
		return "Deck [cartes=" + cartes + "]";
	}

	//attributs 
	/** Liste des cartes contenues dans ce deck. */
	private List<Carte> cartes; 
	 /** Joueur propriétaire du deck. Peut être null si c'est la pioche du jeu. */
	private Joueur proprietaire; 
	private static final long serialVersionUID = 1L;

	
	// constructeur 
	 /**
     * Constructeur d'un deck générique  pour la pioche principale
     * Le deck est initialement vide.
     */
	public Deck () {
		this.cartes = new ArrayList<>();
		this.proprietaire = null; //null car c la pioche 
	}
	
	/**
     * Constructeur d'un deck appartenant à un joueur.
     * Le deck est initialement vide.
     *
     * @param proprietaire joueur possédant ce deck
     */
    public Deck(Joueur proprietaire) {
        this.cartes = new ArrayList<>();
        this.proprietaire = proprietaire;
    }
	
	
	// Getters
	
    /**
     * @return liste des cartes dans ce deck 
     */
	public List<Carte> getCartes() {
		return this.cartes;
		}
	
	 /**
     * @return joueur propriétaire, ou null si c'est la pioche générale
     */	
    public Joueur getProprietaire() {
    	return this.proprietaire; 
    	}
	
    /**
     * ajoute une carte au deck 
     * @param carte carte à ajouter 
     */
    public void ajouterCarte(Carte carte) {
		if (carte != null) {
			this.cartes.add(carte);
		}
	}
    
 // À ajouter à la fin de ta classe Deck
    public int getTaille() {
        return this.cartes.size();
    }
	
    /**
     * Pioche une carte depuis le deck.
     * On retire et renvoie la dernière carte de la liste.
     * @return la carte piochée, ou null si le deck est vide
     */
    public Carte piocherCarte() {
		if (this.cartes.isEmpty()) {
			return null; // Retourne null si le deck est vide
		}
		// Pioche la dernière carte et l'enlever
		return this.cartes.remove(this.cartes.size() - 1); 
	}
	
   
    
  
}