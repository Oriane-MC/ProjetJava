package Modèle;

import java.io.Serializable;

/**
 * Enumération représentant toutes les cartes de bases du jeu.
 *
 * Chaque carte est définie par une valeur, une couleur et une condition
 * utilisée pour déterminer les trophées
 */

public enum TypeCarte implements Serializable {
	
	CARTE1(0,"joker","BestJest"),
	CARTE2(1,"coeur","Joker"),
	CARTE3(2,"coeur","Joker"),
	CARTE4(3,"coeur","Joker"),
	CARTE5(4,"coeur","Joker"),
	CARTE6(1,"carreau","Majority4"),
	CARTE7(2,"carreau","HighestCarreau"),
	CARTE8(3,"carreau","LowestCarreau"),
	CARTE9(4,"carreau","BestJestNoJoker"),
	CARTE10(1,"trèfle","HighestPique"),
	CARTE11(2,"trèfle","LowestCoeur"),
	CARTE12(3,"trèfle","HighestCoeur"),
	CARTE13(4,"trèfle","LowestPique"),
	CARTE14(1,"pique","HighestTrefle"),
	CARTE15(2,"pique","Majority3"),
	CARTE16(3,"pique","Majority2"),
	CARTE17(4,"pique","LowestTrefle");

	/**
	 * Valeur numérique de la carte.
	 */
	private int valeur ;
	/**
	 * Couleur de la carte (coeur, carreau, trèfle, pique, joker).
	 */
	private String couleur;
	/**
	 * Condition associée à la carte pour la détermination des trophées.
	 */
	private String condition;
	/**
	 * Identifiant de sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construit un type de carte avec une valeur, une couleur et une condition.
	 *
	 * @param valeur valeur numérique de la carte
	 * @param couleur couleur de la carte
	 * @param condition condition associée au trophée
	 */
	private TypeCarte(int valeur,String couleur, String condition) {
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
	}
	
	/**
	 * Retourne une représentation textuelle simple de la carte.
	 * Le joker est affiché différemment des autres cartes.
	 * 
	 * @return chaîne représentant la valeur et la couleur de la carte
	 */	
	public String toString() {
		if (this == CARTE1 ) {
			return "joker";
		}
		else {
			String str = new String(this.valeur + "de" + this.couleur);
			return str;
		}
	}

	/**
	 * Retourne la valeur de la carte.
	 *
	 * @return la valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}

	/**
	 * Retourne la couleur de la carte.
	 *
	 * @return la couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur de la carte.
	 *
	 * @return la condition de la carte
	 */
	public String getCondition() {
		return condition;
	}
}
