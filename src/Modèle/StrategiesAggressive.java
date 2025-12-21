package Modèle;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Stratégie Agressive  pour un joueur virtuel :
 * -Lors de la création d'une offre :
 *      Visible = faible 
 *      Cachée  = forte (je garde la meilleure)
 * - Lors de la prise d'une offre adverse :
 *      Toujours prendre la carte visible la plus forte
 */
public class StrategiesAggressive implements Strategie {

	
	/**
     * Crée l'offre agressive pour le joueur.
     * 
     * @param joueur le joueur qui crée l'offre
     * @param pioche le paquet de cartes pour piocher
     * @param offresAdversaires les offres des autres joueurs (non utilisées ici)
     * @return l'offre créée ou null si la pioche est insuffisante
     */
    @Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {

        // 1️⃣ Piocher deux cartes pour créer l'offre
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) {
            System.out.println("Pioche insuffisante pour créer une offre.");
            return null;
        }

        // 2️⃣ Identifier la carte faible et la carte forte
        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // 3️⃣ Logique agressive : visible = faible, cachée = forte
        Offre offre = new Offre(faible, forte, joueur);
        joueur.setOffre(offre);

        System.out.println(joueur.getNom() + " (agressif) crée son offre, sa carte visible est : " + faible );

        return offre;
    }

    
    
    
    /**
     * Décide quelle offre adverse prendre.
     * 
     * <p>Prend toujours la carte visible la plus forte parmi les offres valides.</p>
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

     // Choisir l'offre avec la carte visible la plus forte
         Offre cible = valides.stream()
                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(valides.get(new Random().nextInt(valides.size())));

         
         // Prendre la carte visible et l'ajouter au deck du joueur
         Carte prise = cible.carteVisiblePrise();
         if (prise != null) {
             joueur.getDeckPossede().ajouterCarte(prise);
             System.out.println(joueur.getNom() + " (agressif) prend chez " 
                 + cible.getCreateur().getNom() + " la carte : " + prise);
         }
         
         //retourne le joueur 
         return cible.getCreateur();
      }

    
    /**
     * Retourne le nom de la stratégie pour affichage.
     * 
     * @return le nom de la stratégie ("Stratégie Agressive")
     */
    public String toString() {
    	return "Stratégie Agressive";
    }
}
