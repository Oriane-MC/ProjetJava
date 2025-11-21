package Projet;

public enum TypeCarte {
	
	CARTE1(0,"joker","BestJest"),
	CARTE2(1,"coeur","Joker"),
	CARTE3(2,"coeur","Joker"),
	CARTE4(3,"coeur","Joker"),
	CARTE5(4,"coeur","Joker"),
	CARTE6(1,"carreau","Majority4"),
	CARTE7(2,"carreau","HighestCarreau"),
	CARTE8(3,"carreau","LowestCarreau"),
	CARTE9(4,"carreau","BestJestAndNoJoker"),
	CARTE10(1,"trèfle","HighestPique"),
	CARTE11(2,"trèfle","LowestCoeur"),
	CARTE12(3,"trèfle","HighestCoeur"),
	CARTE13(4,"trèfle","LowesttPique"),
	CARTE14(1,"pique","HighestTrefle"),
	CARTE15(1,"pique","Majority3"),
	CARTE16(1,"pique","Majority2"),
	CARTE18(18,"d","d"),
	CARTE19(19,"coeur","as"),
	CARTE17(1,"pique","LowestTrefle");

	private int valeur ;
	private String couleur;
	private String condition;
	
	private TypeCarte(int valeur,String couleur, String condition) {
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
	}
	
	public String toString() {
		String str = new String(this.valeur + "de" + this.couleur + " / condition : "+ this.condition);
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
