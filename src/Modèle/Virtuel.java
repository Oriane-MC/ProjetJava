package Modèle;

import java.io.*;
import java.util.*;
import javax.swing.Timer;

/**
 * Représente un joueur virtuel contrôlé par une stratégie.
 */
public class Virtuel extends Joueur implements Serializable {

	/**
	 * Stratégie utilisée par le joueur virtuel.
	 * Transient car recréée après désérialisation.
	 */
    private transient Strategie strategie;
    /**
     * Identifiant de sérialisation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identifiant numérique de la stratégie choisie.
     */
    private int strategieInt;

    /**
     * Crée un joueur virtuel avec une stratégie donnée.
     *
     * @param nom nom du joueur
     * @param strategieChoisie identifiant de la stratégie (0 = aléatoire)
     * @param partie partie associée
     */
    public Virtuel(String nom, int strategieChoisie, Partie partie) { 
        super(nom, "Virtuel", partie);
        
        if (strategieChoisie == 0) { 
            strategieChoisie = new Random().nextInt(3) + 1;
        }
        
        this.strategieInt = strategieChoisie;
        initialiserStrategieObjet();
    }

    /**
     * Initialise la stratégie en fonction de l'identifiant choisi.
     */
    private void initialiserStrategieObjet() {
        switch (strategieInt) {
            case 1 -> this.strategie = new StrategieBasique();
            case 2 -> this.strategie = new StratégieDéfensive(); // Assure-toi que le nom du fichier correspond
            case 3 -> this.strategie = new StrategiesAggressive();
            default -> this.strategie = new StrategieBasique();
        }
    }

    /**
     * Réinitialise la stratégie du joueur virtuel.
     */
    public void reinitialiserStrategie() {
        initialiserStrategieObjet();
    }

    /**
     * Choisit automatiquement une offre et notifie la partie après un délai.
     *
     * @param c1 première carte proposée
     * @param c2 seconde carte proposée
     */

    public void choisirOffreGraphique(Carte c1, Carte c2) {
        ArrayList<Offre> offresAdversaires = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this && j.getOffre() != null) {
                offresAdversaires.add(j.getOffre());
            }
        }
        
        // On donne à la stratégie les deux cartes sous forme de paquet temporaire
        PaquetCarte miniPaquet = new PaquetCarte(new LinkedList<>(Arrays.asList(c1, c2)));
        Offre choix = strategie.choisirMonOffre(this, miniPaquet, offresAdversaires);

        // On attend 1 seconde pour que l'humain voit le changement, puis on valide
        Timer timer = new Timer(1000, e -> p.validerChoixOffre(choix.getCarteVisible()));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Joue automatiquement la phase de ramassage selon la stratégie.
     *
     * @param partie partie en cours
     */
    public void jouerAutomatiquement(Partie partie) {
        List<Offre> offresDispo = new ArrayList<>();
        for (Joueur j : partie.getJoueur()) {
            if (j.getOffre() != null && j.getOffre().estDisponible()) {
                // Le robot prend chez les autres, sauf s'il est obligé de prendre la sienne
                if (j != this || (j == this && toutesAutresOffresPrises(partie))) {
                    offresDispo.add(j.getOffre());
                }
            }
        }

        if (!offresDispo.isEmpty()) {
            // La stratégie décide du joueur cible
            Joueur cible = strategie.deciderOffreAdversaire(offresDispo, this);
            
            // Logique de sélection de face : 50% de chance ou selon besoin
            boolean prendreVisible = new Random().nextBoolean(); 

            // On exécute l'action dans le moteur de jeu
            partie.ramasserAction(cible, prendreVisible);
        }
    }

    /**
     * Vérifie si toutes les offres adverses ont déjà été prises.
     *
     * @param partie partie en cours
     * @return true si aucune autre offre n'est disponible
     */
    private boolean toutesAutresOffresPrises(Partie partie) {
        for (Joueur j : partie.getJoueur()) {
            if (j != this && j.getOffre() != null && j.getOffre().estDisponible()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne la stratégie utilisée par le joueur virtuel.
     *
     * @return stratégie courante
     */
    public Strategie getStrategie() {
        return strategie;
    }
    
    /**
     * Retourne l'identifiant numérique de la stratégie.
     *
     * @return identifiant de la stratégie
     */
    public int getStrategieInt() {
		return strategieInt;
	}

    /**
     * Modifie l'identifiant numérique de la stratégie.
     *
     * @param strategieInt nouvel identifiant de stratégie
     */
	public void setStrategieInt(int strategieInt) {
		this.strategieInt = strategieInt;
	}

	/**
	 * Modifie la stratégie utilisée par le joueur virtuel.
	 *
	 * @param strategie nouvelle stratégie
	 */
	public void setStrategie(Strategie strategie) {
		this.strategie = strategie;
	}
	
	/**
     * Retourne une représentation textuelle du joueur virtuel.
     *
     * @return description du joueur et de sa stratégie
     */
    public String toString() {
        String nomStrat = (strategie != null) ? strategie.getClass().getSimpleName() : "Inconnue";
        return nom + " (IA: " + nomStrat + ")";
    }
}