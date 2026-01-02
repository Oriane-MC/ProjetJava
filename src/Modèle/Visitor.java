package Modèle;

import java.util.Map;

/**
 * Interface du patron de conception Visitor.
 * Elle permet de visiter les différents éléments du jeu
 * afin de calculer les scores selon les règles en vigueur.
 */
public interface  Visitor {

	/**
     * Calcule le score associé à un joueur.
     *
     * @param j joueur à visiter
     * @return score du joueur
     * @throws GameException en cas d'erreur de calcul
     */
	public int visit(Joueur j) throws GameException;
	
	/**
     * Calcule les scores finaux de la partie.
     *
     * @param p partie à visiter
     * @return map associant chaque joueur à son score final
     * @throws GameException en cas d'erreur de calcul
     */
	public Map<Joueur, Integer> visit(Partie p) throws GameException;
	
	/**
     * Calcule la valeur d'une carte.
     *
     * @param c carte à visiter
     * @return valeur de la carte
     * @throws GameException en cas d'erreur de calcul
     */
	public int visit(Carte c) throws GameException;
}
