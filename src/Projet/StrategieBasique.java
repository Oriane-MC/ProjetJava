package Projet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Logique : 
 * Visible = la plus basse (pour ne pas attirer les autres)

Cachée = la plus forte (pour garder une chance)

Lors de la prise :
→ prend la carte visible la plus haute qu’il voit
→ sinon prend un hasard
 */

	public class StrategieBasique implements Strategie {
		
		
		// Décider quelle offre faire, quel carte je mets cacher et quel carte je mets visible 
	    public Offre choisirMonOffre( Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
	    	  // Piocher 2 cartes pour créer l'offre
	        Carte c1 = pioche.piocher();
	        Carte c2 = pioche.piocher();
	        
	        
	        if (c1 == null || c2 == null) {
	            System.out.println("Pioche insuffisante pour créer une offre.");
	            return null;
	        }
	     // Trier pour connaître faible et forte
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

	        System.out.println(joueur.getNom() + " (basique) a crée une offre, sa carte visibl est  :" + faible);

	        return offre;
	    }

	        
	    
	    /**
	     * Choisit l’offre adverse à prendre.
	     * Renvoie la CARTE prise (visible).
	     */
	    @Override
	    public Carte prendreOffreAdversaire(List<Offre> offres, Joueur joueur) {

	        if (offres == null || offres.isEmpty()) return null;

	        // Récupérer uniquement les offres valides
	        List<Offre> valides = new ArrayList<>();
	        for (Offre o : offres) {
	            if (o != null && !o.estDisponible()) {
	                valides.add(o);
	            }
	        }
	        if (valides.isEmpty()) return null;

	        // Chercher la meilleure carte visible, celle avc la valeur max 
	        Offre meilleure = valides.stream()
	                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
	                .orElse(null);

	        Offre retenue;
	        if (meilleure != null) {
	            retenue = meilleure;
	        } else {
	            // Sinon au hasard 
	            retenue = valides.get(new Random().nextInt(valides.size()));
	        }

	        // Prendre la carte visible et ajouter au deck 
	        Carte prise = retenue.carteVisiblePrise();

	        if (prise != null) {
	            joueur.getDeckPossede().ajouterCarte(prise);
	            System.out.println(joueur.getNom() + " (basique) prend la carte visible la plus forte.");
	            
	        }

	        return prise;
	    }
	    

	    /**
	     * Retourne le nom de la stratégie pour l'affichage.
	     * @return Le nom de la stratégie.
	     */
	    
	    
	    
	    public String toString() {
	    	return "Stratégie Basique";
	    }
	
}
