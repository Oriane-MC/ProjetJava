package projetLO02;

import projetLO02.Carte;

public class Offre {
	
	private Carte carteVisible;
	private Carte carteCachee;
	private Joueur joueur;
	private boolean etat;
	
	
	public Offre(Carte cCache, Carte cVisible, Joueur j) {
		
		this.carteCachee = cCache;
		this.carteVisible = cVisible;
		// this.joueur = j; // Retiré pour correspondre à l'appel
		this.etat = true;
		
	}
	/**
	 * Permet de prendre la carte visible de l'offre.
	 * Ne peut être effectuée qu'une seule fois.
	 * @return La Carte visible, ou null si l'offre a déjà été prise.
	 */
	public Carte carteVisiblePrise() {
		if (this.etat) {
			this.etat = false;
			return this.carteVisible;
		}
		else {
			System.out.println("Action impossible : L'offre a déjà été prise.");
			return null; // CORRECTION 2: Doit retourner null car la méthode retourne un objet Carte
		}
	}

	
	/**
	 * Permet de prendre la carte cachée de l'offre.
	 * Ne peut être effectuée qu'une seule fois.
	 * @return La Carte cachée, ou null si l'offre a déjà été prise.
	 */
	public Carte carteCacheePrise() {
		if (this.etat) {
			this.etat = false;
			return this.carteCachee;
		}
		else {
			System.out.println("Action impossible : L'offre a déjà été prise.");
			return null; // CORRECTION 3: Doit retourner null car la méthode retourne un objet Carte
		}
	}
	
	/**
	 * Retourne la carte visible sans changer l'état de l'offre.
	 */
	public Carte getCarteVisible() {
	    return this.carteVisible;
	}
	
	/**
	 * Vérifie si l'offre est toujours disponible.
	 */
	public boolean estDisponible() {
	    return this.etat;
	}


}

	