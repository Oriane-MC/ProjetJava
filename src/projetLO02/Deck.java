package projetLO02;

import java.util.ArrayList; // CORRECTION: Import manquant pour ArrayList
import java.util.Collections;
import java.util.List;

public class Deck {
	//attributs 
	private List<Carte> cartes; // Utiliser List est préférable
	private Joueur joueur; // Peut être null si c'est la pioche principale
	
	// constructeur 
	public Deck () {
		this.cartes = new ArrayList<>();
		this.joueur = null; // Initialisation du joueur
	}
	
	// Constructeur pour un deck appartenant à un joueur
	public Deck (Joueur joueur) {
		this.cartes = new ArrayList<>();
		this.joueur = joueur;
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
	
	/**
	 * Mélange le deck. Utile pour la pioche principale.
	 */
	public void melanger() {
		Collections.shuffle(this.cartes);
	}
	
	/**
	 * Initialise le deck (utile pour la pioche principale).
	 */
	public void initialiserDeckStandard() {
		// Logique pour ajouter toutes les cartes standard ici (à implémenter)
	}
	 
}