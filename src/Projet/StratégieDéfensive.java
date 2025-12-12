package Projet;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StratégieDéfensive implements Strategie {

	
    /**
     *  Stratégie Défensive pour un joueur virtuel
     *  Pour la création de l'offre la logique est la suivante :
     * - La carte visible = la plus forte
     * - La carte cachée = la plus faible 
     * 
     * Lors de la prise :
     * -Prend la carte visible la plus faible parmi les offres valides
     * -Si aucune offre n’est valide, choisit une offre au hasard
     * 
     */
 
	
	
    /**
     * Crée l'offre du joueur selon la logique défensive.
     * 
     * @param joueur le joueur qui crée l'offre
     * @param pioche le paquet de cartes à partir duquel on pioche
     * @param offresAdversaires les offres des autres joueurs (non utilisées ici)
     * @return l'offre créée ou null si la pioche est insuffisante
     */
	@Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {

        // On pioche 2 cartes
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) {
            System.out.println("Pioche insuffisante pour créer une offre.");
            return null;
        }

        // On classe les cartes
        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // Logique défensive :
        // Visible = forte | Cachée = faible
        Offre offre = new Offre(forte, faible, joueur);  
        joueur.setOffre(offre);

        System.out.println(joueur.getNom() + " (défensif) crée une offre, sa carte visible est : " + forte);

        return offre;
    }




	/**
    * Décide quelle offre adverse prendre.
    * 
    * <p>Prend la carte visible la plus faible parmi les offres valides.</p>
    * 
    * @param offres la liste des offres adverses
    * @param joueur le joueur qui doit choisir
    * @return le joueur dont la carte a été prise, ou null si aucune offre n'est disponible
    */
    @Override
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
    	if (offres == null || offres.isEmpty()) return null;

        // Filtrer les offres encore disponibles
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null && o.getCarteVisible() != null) {
                valides.add(o);
            }
        }

        if (valides.isEmpty()) return null;

        // Chercher la carte visible la plus faible
        Offre cible = valides.stream()
                .min(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(null);

        // Sinon random
        if (cible == null) {
            cible = valides.get(new Random().nextInt(valides.size()));
        }

        
        // On prend la carte VISIBLE ici et ajoute au deck
        Carte prise = cible.carteVisiblePrise();
        if (prise != null) {
            joueur.getDeckPossede().ajouterCarte(prise);
            System.out.println(joueur.getNom() + " (défensif) prend chez le joueur " + cible.getCreateur().getNom() + " sa carte : "  + prise)  ;
        }
        
         return cible.getCreateur();
        
     }


    /**
     * Retourne le nom de la stratégie pour affichage.
     * 
     * @return le nom de la stratégie ("Stratégie Défensive")
     */
    public String getNom() {
        return "Stratégie Défensive";
    }
    
    public String toString() {
    	return "Stratégie Défensive";
    }
}
