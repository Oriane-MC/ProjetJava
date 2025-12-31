package Modèle;

import java.io.Serializable;

/**
 * Représente une carte du jeu.
 * Une carte possède une valeur, une couleur et éventuellement une condition spéciale.
 * Elle peut être marquée comme extension et participe au pattern Visitor.
 */
public class Carte implements Serializable {
	
	private int valeur;
	private String couleur;
	private String condition;
	private boolean extension;
	private static final long serialVersionUID = 1L;

	/**
	 * Construit une carte avec sa valeur, sa couleur et sa condition.
	 * La carte est considérée comme une extension si la condition vaut "None".
	 *
	 * @param valeur valeur de la carte
	 * @param couleur couleur de la carte
	 * @param condition condition associée à la carte
	 */
	public Carte(int valeur, String couleur, String condition) {
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
		
		if (condition.equals("None")){
			this.extension = true;
		}
		else {
		this.extension = false ; 
		}
	}
	
	/**
	 * Retourne la valeur de la carte.
	 *
	 * @return valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}
	
	/**
	 * Accepte un visiteur selon le pattern Visitor.
	 *
	 * @param v visiteur appliqué à la carte
	 * @return résultat du traitement du visiteur
	 * @throws GameException si une erreur survient lors du traitement
	 */
    public int accept(Visitor v) throws GameException {
    	return v.visit(this);
    }
    
    /**
     * Modifie la valeur de la carte.
     *
     * @param valeur nouvelle valeur
     */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * Retourne la couleur de la carte.
	 *
	 * @return couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Modifie la couleur de la carte.
	 *
	 * @param couleur nouvelle couleur
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	/**
	 * Retourne la condition associée à la carte.
	 *
	 * @return condition de la carte
	 */
	public String getCondition() {
		return condition;
	}
	
	/**
	 * Modifie la condition de la carte.
	 *
	 * @param condition nouvelle condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Indique si la carte est une extension.
	 *
	 * @return true si la carte est une extension, false sinon
	 */
	public boolean getExtension() {
		return extension;
	}
	
	/**
	 * Définit la carte comme étant une extension.
	 */
	public void estExtension() { 
		this.extension = true;
	}

	/**
	 * Retourne une représentation textuelle détaillée de la carte.
	 * Indique visuellement si la carte est une extension.
	 *
	 * @return description détaillée de la carte
	 */
	public String toString() {
		if (couleur.equals("joker")) {
			return "joker";
		}
		String texte = valeur + " de " + couleur;
		if (extension) {
			texte += " (Ext)"; // Permet de voir visuellement quelles cartes sont des extensions
			return texte;
		}
		return texte;
		
	}
	
}
