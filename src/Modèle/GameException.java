package Modèle;

/**
 * Exception spécifique au jeu.
 * Utilisée pour signaler des erreurs liées aux règles ou à la logique du jeu.
 */
public class GameException extends Exception {
	
	/**
	 * Crée une GameException avec un message descriptif.
	 *
	 * @param msg message d'erreur
	 */
	public GameException(String msg) {
		super(msg);
	}

}
