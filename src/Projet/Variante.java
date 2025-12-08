package Projet;

import java.util.Collections;
import java.util.List;

public class Variante {
	
	private String nom; //est que ce serait pas mieux une énumération puisque c'est 
	//nous qui choissisons les variantes (je crois) 
	private boolean utilise;
	
	public Variante(String nom) {
		this.nom = nom;
		this.utilise = false;
	}
	
	 public String getNom() {
	        return nom;
	    }
	 
	 	// Vérifie si la variante a été utilisée
	    public boolean isUtilise() {
	        return utilise;
	    }

	    // Marque la variante comme utilisée
	    public void estUtilise() {
	        this.utilise = true;
	    }
	    
	    
	 /**
     * Inverse l'ordre des joueurs pour le tour passé en paramètre.
     */
	    public void inverseOrdreJoueur(List<Joueur> joueurs) {
	        Collections.reverse(joueurs);
	    }
	
	/**
     * Mélange la liste des joueurs pour déterminer un ordre de départ aléatoire.
     */
	    public void departAleatoire(List<Joueur> joueurs) {
	        Collections.shuffle(joueurs);
	    }
    
    
	    /**
	     * Applique la variante directement sur la liste des joueurs.
	     */
	    public void appliquer(List<Joueur> joueurs) {
	        if (joueurs == null || joueurs.isEmpty() || nom == null) return;

	        switch (nom.toLowerCase()) {
	            case "inversion" -> inverseOrdreJoueur(joueurs);
	            case "départ aléatoire" -> departAleatoire(joueurs);
	            default -> {} // aucune variante
	        }

	        this.utilise = true;
	    }

}
