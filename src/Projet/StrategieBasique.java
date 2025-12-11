package Projet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *Stratégie Basique pour un joueur virtuel.
 *Pour la création de l'offre la logique est la suivante :
 * - Visible = la plus basse (pour ne pas attirer les autres)
 * - Cachée = la plus forte (pour garder une chance)
 * 
 * Lors de la prise :
 * -Choisit une offre au hasard parmi les offres valides
 * -Prend aléatoirement la carte visible ou la carte cachée.
 * -Si la carte visible est prise, elle est affichée dans la console.
 */

	public class StrategieBasique implements Strategie {
		
		
		/**
	     * Crée l'offre du joueur selon la logique basique.
	     * 
	     * @param joueur le joueur qui crée l'offre
	     * @param pioche le paquet de cartes à partir duquel on pioche
	     * @param offresAdversaires les offres des autres joueurs (non utilisées ici)
	     * @return l'offre créée ou null si la pioche est insuffisante
	     */
		public Offre choisirMonOffre( Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
	    	  // Piocher 2 cartes pour créer l'offre
	        Carte c1 = pioche.piocher();
	        Carte c2 = pioche.piocher();
	        
	        
	        if (c1 == null || c2 == null) {
	            System.out.println("Pioche insuffisante pour créer une offre.");
	            return null;
	        }
	     // Trier pour connaître la faible et la forte
	        Carte faible, forte;
	        if (c1.getValeur() <= c2.getValeur()) {
	            faible = c1;
	            forte = c2;
	        } else {
	            faible = c2;
	            forte = c1;
	        }

	        // Logique  :
	        // Visible = faible    | Cachée = forte
	        Offre offre = new Offre(forte, faible, joueur);
	        joueur.setOffre(offre);

	        System.out.println(joueur.getNom() + " (basique) a crée une offre, sa carte visible est  :" + faible);

	        return offre;
	    }

	        
	    
		/**
	     * Décide quelle offre adverse prendre.
	     * 
	     * <p>Choisit une offre au hasard parmi les offres valides et prend aléatoirement la carte
	     * visible ou cachée.</p>
	     * 
	     * @param offres la liste des offres adverses
	     * @param joueur le joueur qui doit choisir
	     * @return le joueur dont la carte a été prise, ou null si aucune offre n'est disponible
	     */
	    @Override
	    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {

	        if (offres == null || offres.isEmpty()) return null;

	        // Récupérer uniquement les offres valides
	        List<Offre> valides = new ArrayList<>();
	        for (Offre o : offres) {
	            if (o != null && o.getCarteVisible() != null) {
	                valides.add(o);
	            }
	        }
	        if (valides.isEmpty()) return null;

	     // Choisir une offre au hasard
	        Offre cible = valides.get(new Random().nextInt(valides.size()));

	        // Choisir aléatoirement la carte visible ou cachée
	        Carte prise;
	        boolean prendreVisible = new Random().nextBoolean(); // true = visible, false = cachée
	        if (prendreVisible) {
	            prise = cible.carteVisiblePrise();
	            // On affiche  la visible
	            if (prise != null) {
	                System.out.println(joueur.getNom() + " (basique) prend chez " 
	                    + cible.getCreateur().getNom() + " la carte visible : " + prise);
	            }
	        } else {
	            prise = cible.carteCacheePrise();
	            System.out.println(joueur.getNom() + " (basique) prend chez " 
		                + cible.getCreateur().getNom() + " la carte cachée. ");
		        
	        }

	        // Ajouter la carte au deck du joueur
	        if (prise != null) {
	            joueur.getDeckPossede().ajouterCarte(prise);
	        } 

	        // Retourner le joueur dont la carte a été prise
	        return cible.getCreateur();
	        
	    }
	    

	    /**
	     * Retourne le nom de la stratégie pour l'affichage.
	     * @return Le nom de la stratégie.
	     */
	    
	    
	    
	    public String toString() {
	    	return "Stratégie Basique";
	    }
	
}
