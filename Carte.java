package projetJest;

import java.util.Collections;

public class Carte {
	
	private Couleur couleur;
	private Valeur valeur;
	private Condition condition;
	private boolean extension;   //comment on va faire pour gérer ça ?
	
	
	public Carte(Valeur valeur, Couleur couleur, Condition condition) {
		
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
		this.extension = false ; //pas sur que ce soir la bonne manière de procéder
		
	}
	
	public void estExtension() { //pas sur de cette manière de procéder 
		this.extension = true;
	}
	
	

	
	
	
	

}
