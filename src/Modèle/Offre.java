package Modèle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente une offre dans le jeu, composée d'une carte visible et d'une carte cachée.
 * Une offre est créée par un joueur et peut être ramassée par un autre joueur.
 */
public class Offre implements Serializable {
	
	/** Carte visible de l'offre, que les autres joueurs peuvent voir */
	private Carte carteVisible;

	/** Carte cachée de l'offre, que seul le créateur connaît */
	private Carte carteCachee;

	/** Joueur ayant créé cette offre */
	private Joueur createur;

	/** Indique si l'offre est encore disponible pour être prise (true si aucune carte n'a été prise) */
	private boolean disponible;

	/** Identifiant de version pour la sérialisation */
	private static final long serialVersionUID = 1L;

    /**
     * Crée une offre avec une carte cachée, une carte visible et un créateur.
     * L'offre est initialement disponible.
     *
     * @param cCachee carte cachée
     * @param cVisible carte visible
     * @param createur joueur créateur de l'offre
     */
    public Offre(Carte cCachee, Carte cVisible, Joueur createur) {
        this.carteCachee = cCachee;
        this.carteVisible = cVisible;
        this.createur = createur;
        this.disponible = true;
    }

    /**
     * Indique si l'offre est encore disponible (aucune carte n'a été prise).
     *
     * @return true si l'offre est disponible, false sinon
     */
    public boolean estDisponible() {
        return disponible;
    }

    /**
     * Retourne la carte visible de l'offre.
     *
     * @return carte visible
     */
    public Carte getCarteVisible() {
        return carteVisible;
    }

    /**
     * Retourne la carte cachée de l'offre.
     *
     * @return carte cachée
     */
    public Carte getCarteCachee() {
        return carteCachee;
    }

    /**
     * Retourne le joueur qui a créé l'offre.
     *
     * @return créateur de l'offre
     */
    public Joueur getCreateur() {
        return createur;
    }
    
    /**
     * Définit si l'offre est disponible ou non.
     *
     * @param disponible true si l'offre est disponible, false sinon
     */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	/**
	 * Modifie la carte visible de l'offre.
	 *
	 * @param carteVisible nouvelle carte visible
	 */
	public void setCarteVisible(Carte carteVisible) {
		this.carteVisible = carteVisible;
	}

	/**
	 * Modifie la carte cachée de l'offre.
	 *
	 * @param carteCachee nouvelle carte cachée
	 */
	public void setCarteCachee(Carte carteCachee) {
		this.carteCachee = carteCachee;
	}

	/**
	 * Modifie le joueur créateur de l'offre.
	 *
	 * @param createur nouveau créateur
	 */
	public void setCreateur(Joueur createur) {
		this.createur = createur;
	}
	
	/**
     * Ramasse la carte visible de l'offre.
     * L'offre devient indisponible après cette action.
     *
     * @return carte visible si disponible, sinon null
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
     * Ramasse la carte cachée de l'offre.
     * L'offre devient indisponible après cette action.
     *
     * @return carte cachée si disponible, sinon null
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
     * Ramasse la carte cachée de l'offre.
     * L'offre devient indisponible après cette action.
     *
     * @return carte cachée si disponible, sinon null
     */
    public ArrayList<Carte> getListeCarte() {
        ArrayList<Carte> list = new ArrayList<>();
        if (carteVisible != null) list.add(carteVisible);
        if (carteCachee != null) list.add(carteCachee);
        return list;
    }

    /**
     * Retourne une représentation textuelle de l'offre,
     * indiquant l'état de la carte visible et masquant la carte cachée.
     *
     * @return description de l'offre
     */
    public String toString() {
        String v = (carteVisible != null) ? carteVisible.toString() : "Prise";
        return "Offre [Visible: " + v + " | Cachée: ???]";
    }
}