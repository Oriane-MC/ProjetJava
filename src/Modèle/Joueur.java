package Modèle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente un joueur du jeu.
 * Un joueur possède un nom, un type, un deck de cartes et éventuellement une offre.
 */
public class Joueur implements Serializable {
	
	/** Nom du joueur */
	protected String nom;

	/** Type du joueur : "Humain" ou "Virtuel" */
	protected String typeJoueur;

	/** L'offre actuellement en cours pour ce joueur */
	protected Offre offre;

	/** Le deck possédé par le joueur */
	protected Deck deckPossede;

	/** La partie à laquelle ce joueur appartient (transient pour ne pas sérialiser) */
	protected transient Partie p;

	/** Identifiant de version pour la sérialisation */
	private static final long serialVersionUID = 1L;

    /**
     * Crée un joueur avec un nom, un type et une référence à la partie.
     * Le deck du joueur est initialisé vide.
     *
     * @param nom nom du joueur
     * @param typeJoueur type du joueur ("Humain" ou "Virtuel")
     * @param partie partie à laquelle le joueur appartient
     */
    public Joueur(String nom, String typeJoueur, Partie partie) {
        this.nom = nom;
        this.typeJoueur = typeJoueur;
        this.deckPossede = new Deck(this);
        this.p = partie;
    }

    /**
     * Retourne le nom du joueur.
     *
     * @return nom du joueur
     */
    public String getNom() { return nom; }
    
    /**
     * Retourne le type du joueur ("Humain" ou "Virtuel").
     *
     * @return type du joueur
     */
    public String getTypeJoueur() { return typeJoueur; }
    
    /**
     * Retourne l'offre actuelle du joueur.
     *
     * @return offre du joueur
     */
    public Offre getOffre() { return offre; }
    
    /**
     * Définit l'offre actuelle du joueur.
     *
     * @param offre nouvelle offre
     */
    public void setOffre(Offre offre) { this.offre = offre; }
    
    /**
     * Retourne la partie à laquelle appartient le joueur.
     *
     * @return partie du joueur
     */
    public Partie getPartie() {
		return p;
	}

    /**
     * Définit la partie à laquelle le joueur appartient.
     *
     * @param p nouvelle partie du joueur
     */
	public void setPartie(Partie p) {
		this.p = p;
	}

	/**
	 * Modifie le nom du joueur.
	 *
	 * @param nom nouveau nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Modifie le type du joueur ("Humain" ou "Virtuel").
	 *
	 * @param typeJoueur nouveau type
	 */
	public void setTypeJoueur(String typeJoueur) {
		this.typeJoueur = typeJoueur;
	}

	/**
	 * Remplace le deck actuel du joueur par un nouveau deck.
	 *
	 * @param deckPossede nouveau deck du joueur
	 */
	public void setDeckPossede(Deck deckPossede) {
		this.deckPossede = deckPossede;
	}

	/**
	 * Retourne le deck de cartes du joueur.
	 *
	 * @return deck du joueur
	 */
    public Deck getDeckPossede() {
        return deckPossede;
    }

    /**
     * Retourne la liste brute des cartes du deck.
     *
     * @return liste des cartes
     */
    public ArrayList<Carte> getJest() { 
        return (ArrayList<Carte>) deckPossede.getCartes(); 
    }

    /**
     * Ajoute une carte au deck du joueur.
     *
     * @param c carte à ajouter
     */
    public void ajouterCarteDeck(Carte c) {
        if (c != null) {
            this.deckPossede.ajouterCarte(c);
        }
    }
    
    /**
     * Accepte un visiteur pour le calcul de points.
     *
     * @param v visiteur appliqué au joueur
     * @return résultat du visiteur
     * @throws GameException en cas d'erreur dans le calcul
     */

    public int accept(Visitor v) throws GameException {
        return v.visit(this);
    }

    /**
     * Ajoute la dernière carte restante de l'offre du joueur à son deck.
     */
    public void ajouterDerniereCarteOffreAuJest() {
        if (this.offre != null && this.offre.estDisponible()) {
            // On prend la carte qui n'a pas été choisie par un adversaire
            Carte c = (this.offre.getCarteVisible() != null) ? 
                      this.offre.carteVisiblePrise() : this.offre.carteCacheePrise();
            ajouterCarteDeck(c);
        }
    }
    
    /**
     * Prend une carte depuis l'offre d'un autre joueur et détermine le joueur suivant.
     *
     * @param p partie en cours
     * @param dejaJoueCeTour liste des joueurs ayant déjà joué ce tour
     * @param joueurChoisi joueur dont la carte est prise
     * @param prendreVisible true pour prendre la carte visible, false pour la cachée
     * @return joueur suivant après cette action
     */
    public Joueur prendreOffreEtJoueurSuivant(Partie p, ArrayList<Joueur> dejaJoueCeTour, Joueur joueurChoisi, boolean prendreVisible) {
        
        // 1. Récupération de la carte chez la cible
        Carte carteAPrendre = prendreVisible ? 
                              joueurChoisi.getOffre().carteVisiblePrise() : 
                              joueurChoisi.getOffre().carteCacheePrise();

        if (carteAPrendre != null) {
            this.ajouterCarteDeck(carteAPrendre);
        }

        // 2. Marquer que le joueur actuel a fini son action de ramassage
        dejaJoueCeTour.add(this);
        
        // 3. Logique de priorité pour le joueur suivant
        Joueur joueurSuivant = null;

        // Priorité 1 : Le joueur qui vient de se faire voler sa carte (s'il n'a pas encore joué)
        if (!dejaJoueCeTour.contains(joueurChoisi)) {
            joueurSuivant = joueurChoisi;
        } 
        // Priorité 2 : Le joueur restant avec la carte visible la plus forte
        else {
            int valeurMax = -1;
            for (Joueur j : p.getJoueur()) {
                if (!dejaJoueCeTour.contains(j)) {
                    if (j.getOffre() != null && j.getOffre().getCarteVisible() != null) {
                        int valeurVisible = j.getOffre().getCarteVisible().getValeur();
                        
                        if (valeurVisible > valeurMax) {
                            valeurMax = valeurVisible;
                            joueurSuivant = j;
                        } else if (valeurVisible == valeurMax && joueurSuivant != null) {
                            // En cas d'égalité, on compare la force des couleurs
                            if (p.getForceCouleur(j.getOffre().getCarteVisible()) > 
                                p.getForceCouleur(joueurSuivant.getOffre().getCarteVisible())) {
                                joueurSuivant = j;
                            }
                        }
                    }
                }
            }
        }
        return joueurSuivant;
    }

    /**
     * Retourne une représentation textuelle du joueur.
     *
     * @return nom et type du joueur
     */
    public String toString() {
        return nom + " (" + typeJoueur + ")";
    }
}