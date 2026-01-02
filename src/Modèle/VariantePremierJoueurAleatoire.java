package Modèle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Variante qui désigne aléatoirement le premier joueur à chaque tour.
 */
public class VariantePremierJoueurAleatoire implements Variante, Serializable {
	
	/** 
	 * Nom de la variante affiché à l'utilisateur 
	 */
	private String nom;
	/**
	 * Indique si la variante est utilisée dans la partie
	 */
	private boolean utilise;
	/**
	 * Identifiant de sérialisation
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construit la variante "Premier Joueur Aléatoire".
	 */
	public VariantePremierJoueurAleatoire() {
		this.nom = "Premier Joueur Aleatoire Par Tour";
		this.utilise = false;
	}
	
	/**
	 * Marque la variante comme active pour la partie.
	 */
	public void estUtilise() {
		this.utilise = true;
	}
	
	/**
	 * Désigne aléatoirement un joueur comme premier joueur du tour.
	 *
	 * @param p la partie concernée
	 * @return le joueur choisi aléatoirement ou null si la variante n'est pas active
	 */
	public Joueur appliquerVariante(Partie p) {
		ArrayList<Joueur> listJoueur = p.getJoueur();
		
		if (utilise == true) {
			Random r = new Random();
			Joueur j = listJoueur.get(r.nextInt(listJoueur.size()));
			return j;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Retourne le nom de la variante.
	 *
	 * @return le nom de la variante
	 */
	public String toString() {
		return nom;
	}

	/**
	 * Retourne le nom de la variante.
	 * 
	 * @return le nom de la variante
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Modifie le nom de la variante.
	 * 
	 * @param nom le nouveau nom à attribuer
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Indique si la variante a été utilisée.
	 * 
	 * @return true si utilisée, false sinon
	 */
	public boolean isUtilise() {
		return utilise;
	}

	/**
	 * Modifie l'état d'utilisation de la variante.
	 * 
	 * @param utilise true si la variante est utilisée, false sinon
	 */
	public void setUtilise(boolean utilise) {
		this.utilise = utilise;
	}
}
