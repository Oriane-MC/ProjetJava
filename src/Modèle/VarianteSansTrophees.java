package Modèle;

import java.io.Serializable;

/**
 * Variante supprimant l'utilisation des trophées dans la partie.
 */
public class VarianteSansTrophees implements Variante, Serializable {
    
	/**
	 * Nom de la variante.
	 */
    private String nom;
    /**
     * Indique si la variante a été activée.
     */
    private boolean utilise;
    /**
     * Identifiant de sérialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit une variante sans trophées.
     * La variante est inactive par défaut.
     */
    public VarianteSansTrophees() {
        this.nom = "Partie Sans Trophees";
        this.utilise = false;
    }
    
    /**
     * Active la variante.
     */
    public void estUtilise() {
        this.utilise = true;
    }
    
    /**
     * Applique la variante à la partie.
     * Supprime les trophées si la variante est active.
     *
     * @param p la partie concernée
     * @return toujours null
     */
    public Joueur appliquerVariante(Partie p) {
        if (utilise) {
            p.setTrophees(null);
        }
        return null;
    }
    
    /**
     * Retourne le nom de la variante.
     *
     * @return nom de la variante
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