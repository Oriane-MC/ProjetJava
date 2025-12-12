package Projet;
import java.util.List;

/**
 * Interface représentant une stratégie pour un joueur virtuel.
 * 
 * <p>Une stratégie définit :</p>
 * <ul>
 *     <li>La manière dont le joueur crée son offre (quelle carte est visible et quelle carte est cachée).</li>
 *     <li>La manière dont le joueur choisit de prendre une carte chez ses adversaires.</li>
 * </ul>
 * 
 */
public interface Strategie   {
	
	/**
     * Crée l'offre du joueur.
     * 
     * <p>Cette méthode décide quelles cartes seront visibles et cachées dans l'offre
     * du joueur. Elle peut utiliser la pioche pour obtenir de nouvelles cartes et tenir
     * compte des offres déjà créées par les adversaires.</p>
     * 
     * @param joueur le joueur qui crée son offre
     * @param pioche le paquet de cartes pour piocher
     * @param offresAdversaires la liste des offres déjà créées par les autres joueurs
     * @return l'offre créée par le joueur, ou null si la pioche ne permet pas de créer d'offre
     */
	 public Offre choisirMonOffre(Joueur joueur,PaquetCarte pioche, List<Offre> offresAdversaires);
    
    
	 
	 /**
	     * Décide quelle offre adverse prendre.
	     * 
	     * <p>Cette méthode définit la logique pour choisir une carte parmi les offres des
	     * autres joueurs. Elle doit retourner le joueur ciblé dont la carte a été prise
	     * (visible ou cachée selon la stratégie).</p>
	     * 
	     * @param offres la liste des offres adverses
	     * @param joueur le joueur qui doit choisir une offre à prendre
	     * @return le joueur dont la carte a été prise, ou null si aucune offre n’est valide
	     */
	 public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur);

    
    
    
    /**
     * Retourne le nom de la stratégie pour l'affichage.
     * @return Le nom de la stratégie.
     */
    
    public String toString();
}
