package Modèle;

import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {
    protected String nom;
    protected String typeJoueur; // "Humain" ou "Virtuel"
    protected Offre offre;
    
    // On utilise un objet Deck pour être compatible avec CompteurPoint
    protected Deck deckPossede; 
    
    protected transient Partie p;
    private static final long serialVersionUID = 1L;

    public Joueur(String nom, String typeJoueur, Partie partie) {
        this.nom = nom;
        this.typeJoueur = typeJoueur;
        this.deckPossede = new Deck(); // Initialisation du Deck
        this.p = partie;
    }

    // --- Getters et Setters ---
    public String getNom() { return nom; }
    public String getTypeJoueur() { return typeJoueur; }
    public Offre getOffre() { return offre; }
    public void setOffre(Offre offre) { this.offre = offre; }
    
    // Retourne l'objet Deck (utilisé par le Visiteur)
    public Deck getDeckPossede() {
        return deckPossede;
    }

    // Retourne la liste brute des cartes (utilisé par la Vue pour .size())
    public ArrayList<Carte> getJest() { 
        return (ArrayList<Carte>) deckPossede.getCartes(); 
    }

    /**
     * Ajoute une carte au Deck (utilisé par CompteurPoint pour les trophées)
     */
    public void ajouterCarteDeck(Carte c) {
        if (c != null) {
            this.deckPossede.ajouterCarte(c);
        }
    }
    
    /**
     * Alias pour ajouterCarteDeck (pour la compatibilité avec ton ancien code)
     */
    public void ajouterAuJest(Carte c) {
        ajouterCarteDeck(c);
    }
    
    /**
     * Acceptation du visiteur pour le calcul des points
     */
    public int accept(Visitor v) throws GameException {
        return v.visit(this);
    }

    /**
     * Méthode appelée à la fin de chaque tour pour ramasser la carte restant dans son offre.
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
     * Logique de ramassage et détermination du joueur suivant
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

    @Override
    public String toString() {
        return nom + " (" + typeJoueur + ")";
    }
}