package Modèle;

/**
 * Interface représentant une variante de jeu.
 *
 * Une variante modifie le déroulement ou les règles de la partie.
 */
public interface Variante {
	
	/**
	 * Indique que la variante est utilisée dans la partie.
	 */
	public void estUtilise();
	
	/**
	 * Applique les effets de la variante à la partie.
	 *
	 * @param p la partie sur laquelle la variante est appliquée
	 * @return un joueur ou null selon la variante
	 */
	public Joueur appliquerVariante(Partie p );
	
	/**
	 * Retourne le nom de la variante pour l'affichage.
	 *
	 * @return une description textuelle de la variante
	 */
	public String toString();
}
