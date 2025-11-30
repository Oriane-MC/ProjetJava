package Projet;

import java.util.ArrayList; // CORRECTION: Import manquant pour ArrayList
import java.util.Collections;
import java.util.List;

public class Deck {
	//attributs 
	private List<Carte> cartes; // Utiliser List est préférable
	private Joueur proprietaire; // Peut être null si c'est la pioche principale
	
	// constructeur 
	public Deck () {
		this.cartes = new ArrayList<>();
		this.proprietaire = null; // Initialisation du joueur
	}
	
	// Constructeur pour un deck appartenant à un joueur
	public Deck (Joueur proprietaire) {
		this.cartes = new ArrayList<>();
		this.proprietaire = proprietaire;
	}
	
	// Getters
	// Pr lire la main du joueur 
		public List<Carte> getCartes() {
		    return this.cartes;
		}
		
    public Joueur getProprietaire() {
    	return this.proprietaire; 
    	}
	
	// Méthode nécessaire pour Joueur.ajouterCarteDeck()
	
    public void ajouterCarte(Carte carte) {
		if (carte != null) {
			this.cartes.add(carte);
		}
	}
	
	// Méthode nécessaire pour Joueur.piocher()
	
    public Carte piocherCarte() {
		if (this.cartes.isEmpty()) {
			return null; // Retourne null si le deck est vide
		}
		// Pioche la dernière carte (simule le dessus d'une pile ou un comportement aléatoire)
		return this.cartes.remove(this.cartes.size() - 1); 
	}
	
    public void retirerCarte(Carte carte) {
    	this.cartes.remove(carte); 
    	}
    
    /** A MODIFIER PR QUE CA FONCTIONNE
     * Affiche les cartes du Deck avec leurs indices pour le choix.
     */
    public void afficherCartesPourChoix(String titre) {
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
    
	/**
	 * Mélange le deck. Utile pour la pioche principale.
	 */
	public void melanger() {
		Collections.shuffle(this.cartes);
	}
	
	
	
	

	 
}