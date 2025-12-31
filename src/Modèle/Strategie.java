package Modèle;

import java.util.List;

/**
 * Interface représentant une stratégie pour un joueur virtuel.
 */
public interface Strategie {
    
    /**
     * Crée l'offre du joueur.
     * 
     * @param joueur le joueur qui crée son offre
     * @param pioche le paquet de cartes pour piocher
     * @param offresAdversaires la liste des offres déjà créées par les autres joueurs
     * @return l'offre créée par le joueur
     */
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires);
    
    /**
     * Décide quelle offre adverse prendre.
     * 
     * @param offres la liste des offres adverses
     * @param joueur le joueur qui doit choisir une offre à prendre
     * @return le joueur dont la carte a été prise
     */
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur);

    /**
     * Retourne le nom de la stratégie pour l'affichage.
     */
    public String toString();
}