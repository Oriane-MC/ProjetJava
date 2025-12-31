package Modèle;

/**
 * Interface pour les observateurs d'une partie.
 * Permet de recevoir des notifications lorsque l'état de la partie change.
 */
public interface Observateur {
	
	/**
	 * Méthode appelée pour notifier l'observateur d'un changement dans la partie.
	 *
	 * @param p partie mise à jour
	 */
	void update(Partie p);
}
