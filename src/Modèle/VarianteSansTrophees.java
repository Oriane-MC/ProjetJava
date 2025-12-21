package Modèle;

import java.io.Serializable;
import java.util.Random;

public class VarianteSansTrophees implements Variante,  Serializable {
	
	private String nom;
	private boolean utilise;
	private static final long serialVersionUID = 1L;

	public VarianteSansTrophees() {
		this.nom = "Partie Sans Trophees";
		this.utilise = false;
	}
	
	public void estUtilise() {
		this.utilise = true;
	}
	
	public Joueur appliquerVariante(Partie p) {
		if (utilise == true) {
			p.setTrophees(null);
			System.out.println("Pas de trophées pour cette partie (car variante apliquée)");
		}
		return null;
	}
	
	public String toString() {
		return nom;
	}
	

}
