package Projet;

public class Offre {

    private Carte carteVisible;
    private Carte carteCachee; 
    private boolean etat; // true = cartes non prises, false = cartes déjà prises

    public Offre(Carte cCache, Carte cVisible, Joueur j) {
        this.carteCachee = cCache;
        this.carteVisible = cVisible;
        this.etat = true;
    }

    public Carte getCarteVisible() {
        return carteVisible;
    }

    /**
     * Prend la carte visible si l'offre est encore disponible.
     * @return la carte visible, ou null si déjà prise
     */
    public Carte carteVisiblePrise() {
        if (this.etat) {
            this.etat = false; // On marque l'offre comme prise
            return this.carteVisible;
        } else {
            System.out.println("Action impossible : carte déjà prise !");
            return null; // On retourne null si la carte n'est pas disponible
        }
    }

    /**
     * Prend la carte cachée si l'offre est encore disponible.
     * @return la carte cachée, ou null si déjà prise
     */
    public Carte carteCacheePrise() {
        if (this.etat) {
            this.etat = false;
            return this.carteCachee;
        } else {
            System.out.println("Action impossible : carte déjà prise !");
            return null; // Correction : retourne null si déjà prise
        }
    }

    /**
     * Vérifie si l'offre est encore disponible
     */
    public boolean estDisponible() {
        return this.etat;
    }

}
