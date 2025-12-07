package Projet;

public enum CarteVariante {
	
	CARTE18(6,"coeur"),
	CARTE19(7,"coeur"),
	CARTE20(6,"pique"),
	CARTE21(7,"pique"),
	CARTE22(6,"trèfle"),
	CARTE23(7,"trèfle"),
	CARTE24(6,"carreau"),
	CARTE25(7,"carreau");
	
	
		
	
	private int valeur ;
	private String couleur;
	
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
