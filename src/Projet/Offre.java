package Projet;

import java.util.ArrayList;
import java.util.List;

public class Offre {

    private Carte carteVisible;
    private Carte carteCachee; 
    private Joueur createur; // Réfère au joueur qui a créer l'offre
    private boolean etat; // true = cartes non prises, false = cartes déjà prises

    public Offre(Carte cCache, Carte cVisible, Joueur j) {
        this.carteCachee = cCache;
        this.carteVisible = cVisible;
        this.createur = j; // Attribution du créateur
        this.etat = true;
    }

    public Carte getCarteVisible() {
        return carteVisible;
    }

    /**
     * @return L'objet Joueur créateur de l'offre.
     */
    public Joueur getCreateur() {
        return createur;
    }

    /**
     * Prend la carte visible si l'offre est encore disponible.
     * @return la carte visible, ou null si déjà prise
     */
    public Carte carteVisiblePrise() {
        if (this.etat) {
            this.etat = false; // On marque l'offre comme prise
            Carte cartePrise = carteVisible;
            this.carteVisible = null;
            return cartePrise;
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
            Carte cartePrise = carteCachee;
            this.carteCachee = null;
            return cartePrise;
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
    
    public ArrayList<Carte> getListeCarte(){
    	ArrayList<Carte> carte = new ArrayList<Carte>();
    	carte.add(carteVisible);
    	carte.add(carteCachee);
    	return carte;
    }
    
    /**
     * Retourne la liste des offres encore disponibles (non prises).
     */
    public static List<Offre> getOffresDisponibles(List<Offre> offres) {

        List<Offre> disponibles = new ArrayList<>();

        for (Offre o : offres) {
            if (o.estDisponible()) {
                disponibles.add(o);
            }
        }

        return disponibles;
    }


}
