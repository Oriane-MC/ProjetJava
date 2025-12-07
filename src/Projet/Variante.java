package Projet;

import java.util.Collections;

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
	public void modifOrdreJoueur(Tour tour ) {
		if (tour != null && tour.getListeJoueurs() != null) {
            Collections.reverse(tour.getListeJoueurs());
        }
	}
	
	/**
     * Mélange la liste des joueurs pour déterminer un ordre de départ aléatoire.
     */
    public void melangerOrdreJoueurs(Tour tour) {
        if (tour != null && tour.getListeJoueurs() != null) {
            Collections.shuffle(tour.getListeJoueurs());
        }
    }
    
    
    /**
     * Applique la variante sur un tour donné.
     * @param tour le tour à modifier
     */
    public void appliquer(Tour tour) {
        if (tour == null) return;

        if (nom == null || nom.isBlank()) return;

        switch (nom.toLowerCase()) {
            case "inversion" -> modifOrdreJoueur(tour);
            case "départ aléatoire" -> melangerOrdreJoueurs(tour);
            default -> { /* aucune variante */ }
        }
    }
}
