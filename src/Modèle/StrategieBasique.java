package Modèle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implémentation basique de la stratégie pour un joueur virtuel.
 * 
 * - Lors de la création d'une offre, la carte la plus faible est visible et la plus forte est cachée.
 * - Lors du choix d'une offre adverse, le joueur est choisi aléatoirement parmi les offres disponibles.
 */
public class StrategieBasique implements Strategie {
	
	/**
	 * Crée une stratégie de type basique
	 */
	public StrategieBasique() {	}
    
	/**
	 * Crée l'offre du joueur.
	 * La carte la plus forte est cachée, la plus faible est visible.
	 *
	 * @param joueur le joueur qui crée son offre
	 * @param pioche le paquet de cartes pour piocher
	 * @param offresAdversaires la liste des offres déjà créées par les autres joueurs
	 * @return l'offre créée par le joueur, ou null si moins de deux cartes disponibles
	 */
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();
        
        if (c1 == null || c2 == null) return null;

        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // On retourne l'offre créée
        return new Offre(forte, faible, joueur);
    }

    /**
     * Décide quelle offre adverse prendre.
     * La stratégie choisit un joueur au hasard parmi les offres valides.
     *
     * @param offres la liste des offres adverses
     * @param joueur le joueur qui doit choisir une offre à prendre
     * @return le joueur dont la carte a été prise, ou null si aucune offre disponible
     */
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
        if (offres == null || offres.isEmpty()) return null;

        // On filtre pour être sûr d'avoir des offres valides
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null && o.getCreateur() != null) {
                valides.add(o);
            }
        }

        if (valides.isEmpty()) return null;

        // Choix purement aléatoire de l'adversaire
        int index = new Random().nextInt(valides.size());
        return valides.get(index).getCreateur();
    }

    /**
     * Retourne le nom de la stratégie.
     *
     * @return une chaîne décrivant la stratégie
     */
    public String toString() {
        return "Stratégie Basique";
    }
}