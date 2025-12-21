package Modèle;

import java.io.Serializable;

public class Carte implements Serializable {
	
	private int valeur;
	private String couleur;
	private String condition;
	private boolean extension;   //comment on va faire pour gérer ça ?
	private static final long serialVersionUID = 1L;

	
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
	
	public String toString() {
		if (couleur.equals("joker")) {
			return "joker";
		}
		else {
			return valeur +" de " + couleur ;
		}
	}

	public int getValeur() {
		return valeur;
	}
	
	/** 
     * méthode relatif au pattern Visitor
     * @param v
     * @return
	 * @throws GameException 
     */
    public int accept(Visitor v) throws GameException {
    	return v.visit(this);
    }

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean getExtension() {
		return extension;
	}
	
	public void estExtension() { //pas sur de cette manière de procéder 
		this.extension = true;
	}

	public String toString1() {
	    if (couleur.equalsIgnoreCase("joker")) {
	        return "JOKER";
	    }
	    String texte = valeur + " de " + couleur;
	    if (extension) {
	        texte += " (Ext)"; // Permet de voir visuellement quelles cartes sont des extensions
	    }
	    return texte;
	}
	
}
