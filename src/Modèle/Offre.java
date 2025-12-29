package Modèle;

import java.io.Serializable;
import java.util.ArrayList;



public class Offre implements Serializable {
    private Carte carteVisible;
    private Carte carteCachee;
    private Joueur createur;
    private boolean disponible; // true si aucune carte n'a encore été prise
    private static final long serialVersionUID = 1L;

    public Offre(Carte cCachee, Carte cVisible, Joueur createur) {
        this.carteCachee = cCachee;
        this.carteVisible = cVisible;
        this.createur = createur;
        this.disponible = true;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public Carte getCarteVisible() {
        return carteVisible;
    }

    public Carte getCarteCachee() {
        return carteCachee;
    }

    public Joueur getCreateur() {
        return createur;
    }

    /**
     * Ramasse la carte visible. L'offre devient indisponible.
     */
    public Carte carteVisiblePrise() {
        if (disponible) {
            disponible = false;
            Carte temp = carteVisible;
            carteVisible = null; // Elle n'est plus sur la table
            return temp;
        }
        return null;
    }

    /**
     * Ramasse la carte cachée. L'offre devient indisponible.
     */
    public Carte carteCacheePrise() {
        if (disponible) {
            disponible = false;
            Carte temp = carteCachee;
            carteCachee = null; // Elle n'est plus sur la table
            return temp;
        }
        return null;
    }

    /**
     * Retourne les cartes encore présentes dans l'offre (utile pour la fin du tour).
     */
    public ArrayList<Carte> getListeCarte() {
        ArrayList<Carte> list = new ArrayList<>();
        if (carteVisible != null) list.add(carteVisible);
        if (carteCachee != null) list.add(carteCachee);
        return list;
    }

    @Override
    public String toString() {
        String v = (carteVisible != null) ? carteVisible.toString() : "Prise";
        return "Offre [Visible: " + v + " | Cachée: ???]";
    }
}