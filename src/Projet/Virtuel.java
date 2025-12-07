package Projet;

import java.util.ArrayList;
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
    private Offre choisirMonOffre(Partie p) { //ORIANE : changement du code ici 
    	
    	ArrayList<Offre> offresAdversaires = new ArrayList();
    	for (Joueur j : p.getJoueur()) {
    		offresAdversaires.add(j.getOffre());
    	}
        return strategie.choisirMonOffre(this, p.getPioche(), offresAdversaires);
    }

    public void creerMonOffre(Partie p) { //ORIANE : changement du code ici 
    	super.offre = choisirMonOffre(p);
    }
    /**
     * Demande à la stratégie de décider si le joueur virtuel doit prendre
     * l'offre d'un adversaire. 
     *
     * @param offre Offre proposée par un joueur adverse.
     */
    private Carte deciderOffreAdversaire( ///////// ) {
         return strategie.prendreOffreAdversaire( //////// );
    }
    
    
    public void prendreOffre(Partie partie) {
    	Carte c = this.deciderOffreAdversaire( ///////// );
    	super.jest.ajouterCarte(c);
    }
    // prendreOffre doit faire appel a deciderOffreAdversaire pour choisir une carte de l'offre d'un des joueur et renvoyer 
    //cette carte pour pouvoir ajouter la carte au deck du joueur virtuel 
    //et decider offre adversaire doit donc retourner un joueur et doit juste faire appel
    // a une méthode de stratégie qui permet de renvoyer le carte le plus interresant à prendre parmes le offres et les joueur
    // normalement le code squellet est bon pour ici (pas dans les classe stategie par exemple), fait juste que tu mettes les parametres 
    // que tu veux là ou j'ai mis des //////// (tu peux te débrouiller un peu comme tu veux en ayant partie en parametre en vrai 
    // tu peux regarder ce que j'ai fait pour choisirMonOffre dans cette classe pour te donner des idées de parametre "plus simple" a gerer

    
    
    
    
    public void ajouterMainAuDeckVirtuel() { //ORIANE : j'ai fait ca car il y en a besoin pour la partie : a verifier 
		super.ajouterMainAuDeck();
	}

}
