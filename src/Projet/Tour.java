package Projet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tour {
//attributs 
	private List<Joueur> listeJoueurs;
	private int numeroTour;

// constructeur 
	public Tour (List<Joueur> listeJoueurs, int numeroTour) {
		 this.listeJoueurs = new ArrayList<>(listeJoueurs);
	     this.numeroTour = numeroTour;
		
	}
	
	// Getters
    public List<Joueur> getListeJoueurs() {
    	return listeJoueurs; 
    }

    public int getNumeroTour() {
        return numeroTour;
    }
//méthodes 
    
    
	public void determinerOrdreJoueur () {
		 // Tri décroissant selon la carte visible
        joueurs.sort((j1, j2) -> {
            Carte c1 = j1.getCarteVisible();
            Carte c2 = j2.getCarteVisible();

            // Valeur des cartes visibles
            int val1 = (c1.getCouleur() == Carte.Couleur.JOKER) ? 0 : c1.getValeur();
            int val2 = (c2.getCouleur() == Carte.Couleur.JOKER) ? 0 : c2.getValeur();

            // Si valeurs différentes → on compare par valeur décroissante
            if (val1 != val2) {
                return Integer.compare(val2, val1);
            }

            // Sinon on départage par la hiérarchie des couleurs
            int forceCouleur1 = switch (c1.getCouleur()) {
                case PIQUE -> 4;
                case TREFLE -> 3;
                case CARREAU -> 2;
                case COEUR -> 1;
                default -> 0; // Joker
            };

            int forceCouleur2 = switch (c2.getCouleur()) {
                case PIQUE -> 4;
                case TREFLE -> 3;
                case CARREAU -> 2;
                case COEUR -> 1;
                default -> 0;
            };

            // Tri décroissant par couleur si valeurs égales
            return Integer.compare(forceCouleur2, forceCouleur1);
        });
	
	}
	
	
	 /**
     * Affiche l'ordre actuel des joueurs.
     */
	public void afficherOrdreJoueurs() {
        System.out.println("Ordre des joueurs pour le tour " + numeroTour + " :");
        for (int i = 0; i < this.listeJoueurs.size(); i++) {
            Joueur j = this.listeJoueurs.get(i);
            // Réutilise la méthode getCarteVisible()
            Carte carteVisible = j.getCarteVisible(); 
            String carteInfo = (carteVisible != null) ? 
                               carteVisible.getValeur() + " de " + carteVisible.getCouleur() : 
                               "AUCUNE OFFRE";
                               
            System.out.println((i + 1) + ". " + j.getNom() +
                    " (carte visible : " + carteInfo + ")");
        }
    }
	
}
