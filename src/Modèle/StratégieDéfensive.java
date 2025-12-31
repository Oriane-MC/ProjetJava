package Modèle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Implémentation défensive de la stratégie pour un joueur virtuel.
 * 
 * - Lors de la création d'une offre, la carte visible est la plus forte (pour "faire peur") 
 *   et la carte cachée est la plus faible.
 * - Lors du choix d'une offre adverse, le joueur prend la carte visible la plus faible parmi les offres disponibles.
 */
public class StratégieDéfensive implements Strategie {

	/**
     * Crée l'offre du joueur selon la stratégie défensive.
     * La carte visible est la plus forte et la carte cachée est la plus faible.
     *
     * @param joueur le joueur qui crée son offre
     * @param pioche le paquet de cartes pour piocher
     * @param offresAdversaires la liste des offres déjà créées par les autres joueurs
     * @return l'offre créée par le joueur, ou null si la pioche est insuffisante
     */
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) return null;

        Carte faible = (c1.getValeur() <= c2.getValeur()) ? c1 : c2;
        Carte forte = (c1 == faible) ? c2 : c1;

        return new Offre(faible, forte, joueur);
    }

    /**
     * Choisit l'offre adverse à prendre selon la stratégie défensive.
     * Le joueur prend la carte visible la plus faible.
     *
     * @param offres la liste des offres adverses
     * @param joueur le joueur qui doit choisir une offre à prendre
     * @return le joueur dont la carte a été prise, ou null si aucune offre disponible
     */
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
        if (offres == null || offres.isEmpty()) return null;

        Offre cible = offres.stream()
                .filter(o -> o.getCarteVisible() != null)
                .min(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(offres.get(new Random().nextInt(offres.size())));

        return cible.getCreateur();
    }

    /**
     * Retourne le nom de la stratégie pour l'affichage.
     *
     * @return le nom de la stratégie
     */
    public String toString() {
        return "Stratégie Défensive";
    }
}