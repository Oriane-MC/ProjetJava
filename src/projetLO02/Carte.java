package projetLO02;

import java.util.Collections;



public class Carte {
	
	private Couleur couleur;
	private Valeur valeur;
	private Condition condition;
	private boolean extension;   //comment on va faire pour gérer ça ?
	
	
	public Carte(private Valeur valeur, private  Couleur couleur, private Condition condition, private boolean extension) {
		
		this.valeur = valeur;
		this.couleur = couleur;
		this.condition = condition;
		this.extension = false ; //pas sur que ce soir la bonne manière de procéder
		
	}
	
	// Getters pour accéder aux propriétés de la carte
    public Couleur getCouleur() {
        return couleur;
    }

    public Valeur getValeur() {
        return valeur;
    }

    public Condition getCondition() {
        return condition;
    }
    
    public boolean isExtension() {
        return extension;
    }
    
    public void setExtension(boolean estExtension) { 
		this.extension = estExtension;
	}
			

}
