package Modèle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class VariantePremierJoueurAleatoire implements Variante, Serializable {
	
	private String nom;
	private boolean utilise;
	private static final long serialVersionUID = 1L;

	
	public VariantePremierJoueurAleatoire() {
		this.nom = "Premier Joueur Aleatoire Par Tour";
		this.utilise = false;
	}
	
	public void estUtilise() {
		this.utilise = true;
	}
	
	public Joueur appliquerVariante(Partie p) {
		ArrayList<Joueur> listJoueur = p.getJoueur();
		
		if (utilise == true) {
			Random r = new Random();
			Joueur j = listJoueur.get(r.nextInt(listJoueur.size()));
			return j;
		}
		else {
			return null;
		}
	}
	
	public String toString() {
		return nom;
	}
	
}
