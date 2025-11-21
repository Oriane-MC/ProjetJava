package Projet;



public class Carte {
	
	private TypeCarte carte;
	private boolean extension;   //comment on va faire pour gérer ça ?
	
	
	public Carte(TypeCarte typeCarte) {
		this.carte = typeCarte;
		this.extension = false ; //pas sur que ce soir la bonne manière de procéder
		
	}
	
	public void estExtension() { //pas sur de cette manière de procéder 
		this.extension = true;
	}
	
}
