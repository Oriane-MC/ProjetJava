package Projet;

import java.util.List;


/**
 * Représente un joueur contrôlé par l'ordinateur, utilisant une stratégie
 * pour prendre des décisions automatiquement durant la partie.
 * Il hérite du comportement général d'un {@link Joueur},
 * mais délègue ses choix à une instance de {@link Strategie}.
 */
public class Virtuel extends Joueur {

	/** Stratégie appliquée par le joueur virtuel pour ses décisions. */
    private Strategie strategie;
  

    /**
     * Constructeur du joueur virtuel
     * @param Nom du joueur
     * @param strategie utilisé pour ses décisions 
     * @param p Référence à la partie pour accès au scanner, etc.
     */
    public Virtuel(String nom, Strategie strategie, Partie partie ) {
        super(nom, "Virtuel", partie);
        this.strategie = strategie;
    }

    // Setter 
    /**
     * Possibilité de changer  la stratégie du joueur virtuel en cours de partie.
     * @param strategie Nouvelle stratégie à appliquer.
     */
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    /**
     * Demande à la stratégie de choisir l'offre que le joueur virtuel va créer, c'est à dire 
     * lorsque le joueur doit sélectionner une carte visible et une carte cachée à partir de sa main.
     * @param pioche          La pioche permettant éventuellement de tirer des cartes.
     * @param offresAdversaires Liste des offres déjà déposées par les autres joueurs.
     * @return L'offre choisie par le joueur virtuel conformément à sa stratégie.
     */
    public Offre choisirMonOffre(Deck pioche, List<Offre> offresAdversaires) {
        return strategie.choisirMonOffre(this, pioche, offresAdversaires);
    }

    /**
     * Demande à la stratégie de décider si le joueur virtuel doit prendre
     * l'offre d'un adversaire. 
     *
     * @param offre Offre proposée par un joueur adverse.
     */
    public void deciderOffreAdversaire(Offre offre) {
         strategie.prendreOffreAdversaire(offre, this);
    }
}
