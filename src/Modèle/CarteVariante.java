package Modèle;

import java.io.Serializable;

public enum CarteVariante implements Serializable {

	CARTE18(6,"pique"),
	CARTE19(7,"pique"),
	CARTE20(6,"trèfle"),
	CARTE21(7,"trèfle"),
	CARTE22(6,"carreau"),
	CARTE23(7,"carreau");
	
	
		
	
	private int valeur ;
	private String couleur;
	private static final long serialVersionUID = 1L;

	
	private CarteVariante(int valeur,String couleur) {
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	public String toString() {
		String str = new String(this.valeur + "de" + this.couleur);
		return str;
	}
	

	public int getValeur() {
		return valeur;
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

}
