package projetJest;

public class Offre {
	
	private Carte carteVisible;
	private Carte carteCachee;
	private Joueur joueur;
	private boolean etat;
	
	
	public Offre(Carte cCache, Carte cVisible, Joueur j) {
		
		this.carteCachee = cCache;
		this.carteVisible = cVisible;
		this.joueur = j;
		this.etat = true;
		
	}
	
	public Carte carteVisiblePrise() {
		if (this.etat) {
			this.etat = false;
			return this.carteVisible;
		}
		else {
			System.out.println("action impossible");
		}
	}

	public Carte carteCacheePrise() {
		if (this.etat) {
			this.etat = false;
			return this.carteCachee;
		}
		else {
			System.out.println("action impossible");
		}
	}	

}
