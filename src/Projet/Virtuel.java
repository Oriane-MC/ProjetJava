package Projet;

import java.util.List;
public class Virtuel extends Joueur {

    private Strategie strategie;
    private static Partie p; 

    // Constructeur avec stratégie
    public Virtuel(String nom, Strategie strategie) {
        super(nom, "Virtuel", p);
        this.strategie = strategie;
    }

    // Setter pour changer de stratégie en cours de partie
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    // Décider de son offre
    public Offre choisirMonOffre(Deck pioche, List<Offre> offresAdversaires) {
        return strategie.choisirMonOffre(this, pioche, offresAdversaires);
    }

    // Décider de prendre l'offre d'un adversaire
    public void deciderOffreAdversaire(Offre offre) {
         strategie.prendreOffreAdversaire(offre, this);
    }
}
