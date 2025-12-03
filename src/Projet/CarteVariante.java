package Projet;

public enum CarteVariante {
	
	CARTE18("valet","coeur"),
	CARTE19("cavalier","coeur"),
	CARTE20("dame","coeur"),
	CARTE21("roi","coeur");
	
	private String valeur ;
	private String couleur;

	
	private CarteVariante(String valeur,String couleur) {
		this.valeur = valeur;
		this.couleur = couleur;
	}
	
	public String toString() {
		String str = new String(this.valeur + "de" + this.couleur);
		return str;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

}
