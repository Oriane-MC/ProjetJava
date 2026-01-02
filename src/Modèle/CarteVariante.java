package Modèle;

import java.io.Serializable;

/**
 * Énumération représentant des variantes de cartes.
 * Chaque variante est caractérisée par une valeur et une couleur.
 */
public enum CarteVariante implements Serializable {

	CARTE18(6,"pique"),
	CARTE19(7,"pique"),
	CARTE20(6,"trèfle"),
	CARTE21(7,"trèfle"),
	CARTE22(6,"carreau"),
	CARTE23(7,"carreau");
	
	/** La valeur numérique de la carte variante (ex : 6, 7) */
	private int valeur;

	/** La couleur de la carte variante (ex : pique, trèfle, carreau) */
	private String couleur;

	/** Identifiant de version pour la sérialisation */
	private static final long serialVersionUID = 1L;

	/**
	 * Construit une variante de carte avec une valeur et une couleur données.
	 *
	 * @param valeur valeur de la carte
	 * @param couleur couleur de la carte
	 */
	private CarteVariante(int valeur,String couleur) {
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	/**
	 * Retourne une représentation textuelle de la variante de carte.
	 *
	 * @return description de la variante
	 */
	public String toString() {
		String str = new String(this.valeur + "de" + this.couleur);
		return str;
	}
	
	/**
	 * Retourne la valeur de la variante de carte.
	 *
	 * @return valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * Retourne la couleur de la variante de carte.
	 *
	 * @return couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}
}
