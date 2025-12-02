package Projet;



public class Carte {
	
	private int valeur;
	private String couleur;
	private String condition;
	private boolean extension;   //comment on va faire pour gérer ça ?
	
	
	public Carte(int valeur, String couleur, String condition) {
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
		this.extension = false ; //pas sur que ce soir la bonne manière de procéder	
	}
	
	public String toString() {
		return valeur +" de " + couleur + " / condition : "+ condition + " / extension="
				+ extension;
	}

	public int getValeur() {
		return valeur;
	}
	
	/** 
     * méthode relatif au pattern Visitor
     * @param v
     * @return
     */
    public int accept(Visitor v) {
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

	
}
