package Modèle;

import java.io.*;
import java.util.*;

/**
 * Classe représentant un joueur virtuel (IA).
 * Utilise le pattern Strategy pour ses décisions.
 */
public class Virtuel extends Joueur implements Serializable {

    private transient Strategie strategie;
    private static final long serialVersionUID = 1L;
    private int strategieInt;

    /**
     * Constructeur du joueur virtuel.
     */
    public Virtuel(String nom, int strategieChoisie, Partie partie) { 
        super(nom, "Virtuel", partie);
        
        // Si stratégie aléatoire (0)
        if (strategieChoisie == 0) { 
            strategieChoisie = new Random().nextInt(3) + 1;
        }
        
        this.strategieInt = strategieChoisie;
        initialiserStrategieObjet();
    }

    /**
     * Initialise l'objet Strategie en fonction de l'entier stocké.
     */
    private void initialiserStrategieObjet() {
        switch (strategieInt) {
            case 1 -> this.strategie = new StrategieBasique();
            case 2 -> this.strategie = new StratégieDéfensive();
            case 3 -> this.strategie = new StrategiesAggressive();
            default -> this.strategie = new StrategieBasique();
        }
    }

    /**
     * Réinitialiser la stratégie après chargement d'une sauvegarde (transient).
     */
    public void reinitialiserStrategie() {
        initialiserStrategieObjet();
    }

    // --- LOGIQUE GRAPHIQUE (PHASE 3) ---

    /**
     * Méthode appelée par la Partie pour que l'IA crée son offre.
     * Utilise TA stratégie choisie.
     */
    public void choisirOffreGraphique(Carte c1, Carte c2) {
        // Liste des offres adverses pour que la stratégie puisse décider
        ArrayList<Offre> offresAdversaires = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this && j.getOffre() != null) {
                offresAdversaires.add(j.getOffre());
            }
        }
        
        // La stratégie renvoie un objet Offre (qui contient faceVisible et faceCachee)
        // Note : On utilise un wrapper temporaire ou on adapte l'appel selon ta signature Strategie
        this.offre = strategie.choisirMonOffre(this, p.getPioche(), offresAdversaires);
    }

    /**
     * Méthode appelée par la Partie pour que l'IA prenne une carte.
     * Détermine la cible via la stratégie et effectue la prise.
     */
    public void effectuerActionIA() {
        // 1. Lister les offres disponibles
        List<Offre> offresDispo = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j.getOffre() != null && j.getOffre().estDisponible()) {
                // On ne prend pas la sienne sauf si c'est le dernier recours
                if (j != this) offresDispo.add(j.getOffre());
            }
        }

        Joueur cible;
        if (offresDispo.isEmpty()) {
            cible = this; // Obligé de prendre chez soi
        } else {
            // TA stratégie décide quel joueur cibler
            cible = strategie.deciderOffreAdversaire(offresDispo, this);
        }

        // 2. Décider quelle carte prendre (Visible=1 ou Cachée=2)
        // Par défaut, l'IA prend souvent la visible si elle est forte, 
        // ou la cachée si la visible est mauvaise.
        int choixFace = (new Random().nextBoolean()) ? 1 : 2; 

        // 3. Appeler la méthode de prise commune (héritée de Joueur)
        this.prendreCarteGraphique(cible, choixFace);
    }

    // --- GETTERS / SETTERS ---

    public Strategie getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    @Override
    public String toString() {
        return nom + " (IA " + strategie.getClass().getSimpleName() + ")";
    }

    // --- ANCIENNES MÉTHODES CONSOLE (Gardées pour compatibilité) ---

    public void creerMonOffre(Partie p) { 
        ArrayList<Offre> offresAdversaires = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this) offresAdversaires.add(j.getOffre());
        }
        this.offre = strategie.choisirMonOffre(this, p.getPioche(), offresAdversaires);
    }

    @Override
    public Joueur prendreOffreEtJoueurSuivant(Partie p, ArrayList<Joueur> dejaJoue) {
        // Cette méthode console peut être laissée telle quelle si tu lances en mode texte.
        // Mais en mode GUI, c'est effectuerActionIA() qui sera utilisée.
        return super.prendreOffreEtJoueurSuivant(p, dejaJoue);
    }
}