package Modèle;

import java.io.*;
import java.util.*;
import javax.swing.Timer;

public class Virtuel extends Joueur implements Serializable {

    private transient Strategie strategie;
    private static final long serialVersionUID = 1L;
    private int strategieInt;

    public Virtuel(String nom, int strategieChoisie, Partie partie) { 
        super(nom, "Virtuel", partie);
        
        if (strategieChoisie == 0) { 
            strategieChoisie = new Random().nextInt(3) + 1;
        }
        
        this.strategieInt = strategieChoisie;
        initialiserStrategieObjet();
    }

    private void initialiserStrategieObjet() {
        switch (strategieInt) {
            case 1 -> this.strategie = new StrategieBasique();
            case 2 -> this.strategie = new StratégieDéfensive(); // Assure-toi que le nom du fichier correspond
            case 3 -> this.strategie = new StrategiesAggressive();
            default -> this.strategie = new StrategieBasique();
        }
    }

    public void reinitialiserStrategie() {
        initialiserStrategieObjet();
    }

    /**
     * L'IA choisit sa carte visible et notifie la partie.
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
     * L'IA décide chez qui ramasser et quelle carte prendre.
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

    private boolean toutesAutresOffresPrises(Partie partie) {
        for (Joueur j : partie.getJoueur()) {
            if (j != this && j.getOffre() != null && j.getOffre().estDisponible()) {
                return false;
            }
        }
        return true;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    @Override
    public String toString() {
        String nomStrat = (strategie != null) ? strategie.getClass().getSimpleName() : "Inconnue";
        return nom + " (IA: " + nomStrat + ")";
    }
}