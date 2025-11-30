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
	    public Offre choisirMonOffre( Joueur joueur, Deck pioche, List<Offre> offresAdversaires) {
	    	  // Piocher 2 cartes pour créer l'offre
	        Carte c1 = pioche.piocherCarte();
	        Carte c2 = pioche.piocherCarte();
	        
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

	        System.out.println(joueur.getNom() + " (basique) crée une offre : visible = faible, cachée = forte");

	        return offre;
	    }

	        
	    
	   @Override
	    public void prendreOffreAdversaire(Offre offreChoisie, Joueur joueur) {
	        if (offreChoisie == null) return;

	        // Basique prend toujours la CARTE VISIBLE
	        Carte prise = offreChoisie.carteVisiblePrise();
	        if (prise != null) {
	            joueur.getDeckPossede().ajouterCarte(prise);
	            System.out.println(joueur.getNom() + " (basique) prend la meilleure carte visible.");
	        }
	    }
	   
	    
	   /**
	     * Cette méthode choisit L'OFFRE adverse 
	     * Selon la logique :
	     *   → prendre la carte visible la plus forte parmi toutes les offres valides
	     *   → sinon prendre une offre au hasard
	     */
	    public Offre choisirMeilleureOffre(List<Offre> offres) {

	        // Filtre : offres encore valides (etat = true)
	        List<Offre> valides = new ArrayList<>();
	        for (Offre o : offres) {
	            if (o != null && o.carteVisiblePrise() == null) {
	                valides.add(o);
	            }
	        }

	        if (valides.isEmpty()) return null;

	        // Choisir l'offre dont la carte visible est LA PLUS FORTE
	        Offre meilleure = valides.stream()
	                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
	                .orElse(null);

	        if (meilleure != null) return meilleure;

	        // Si aucune meilleure → au hasard
	        return valides.get(new Random().nextInt(valides.size()));
	    }
	    
	    

	    /**
	     * Retourne le nom de la stratégie pour l'affichage.
	     * @return Le nom de la stratégie.
	     */
	    
	    @Override 
	    public String getNom() {
	    	return "Stratégie Basique";
	    }
	
}
