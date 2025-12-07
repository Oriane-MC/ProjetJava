package Projet;

import java.util.ArrayList; 
import java.util.List;

/**
 * Représente un paquet de cartes (deck) utilisé dans le jeu.
 * Il permet d'ajouter des cartes, d'en retirer et de piocher.
 */
public class Deck {
	
	//attributs 
	/** Liste des cartes contenues dans ce deck. */
	private List<Carte> cartes; 
	 /** Joueur propriétaire du deck. Peut être null si c'est la pioche du jeu. */
	private Joueur proprietaire; 
	
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
	
    /**
     * Pioche une carte depuis le deck.
     * On retire et renvoie la dernière carte de la liste.
     * @return la carte piochée, ou null si le deck est vide
     */
    public Carte piocherCarte() {
		if (this.cartes.isEmpty()) {
			return null; // Retourne null si le deck est vide
		}
		// Pioche la dernière carte (simule le dessus d'une pile ou un comportement aléatoire)
		return this.cartes.remove(this.cartes.size() - 1); 
	}
	
    /**
     * Retire une carte spécifique du deck si elle s'y trouve.
     * @param carte carte à retirer
     */
    public void retirerCarte(Carte carte) { // ES CE QUE C VRMNT UTILE ???
    	this.cartes.remove(carte); 
    	}
    
    /**
     * Affiche toutes les cartes du deck avec un indice
     * pour permettre au joueur d'en choisir une.
     * Exemple d'affichage :
     * --- Main du joueur ---
     *   [1] 7 de Pique
     *   [2] Roi de Coeur
     * @param titre titre affiché avant les cartes 
     */
    public void afficherCartesPourChoix(String titre) { // ES CE QUE C VRMNT UTILE ???
        if (cartes.isEmpty()) {
            System.out.println("Deck " + titre + " vide.");
            return;
        }
        System.out.println("--- " + titre + " ---");
        for (int i = 0; i < cartes.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + cartes.get(i).toString());
        }
        System.out.println("-------------------------------------------");
    }
	 
}