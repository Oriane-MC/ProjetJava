package Projet;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe représentant un joueur virtuel (IA).
 * Il utilise une stratégie pour :
 *  - créer son offre
 *  - choisir une offre adverse
 *  - prendre une carte
 * 
 * Toute l’affichage concernant les décisions du joueur virtuel
 * est gérée ici.
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
    public Virtuel(String nom, Partie partie ) {
        super(nom, "Virtuel", partie);
        //GENERER UN STRATEGIE ALEATOIRE 
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
    public void creerMonOffre(Partie p) { //ORIANE : changement du code ici 
    	
    	ArrayList<Offre> offresAdversaires = new ArrayList();
    	for (Joueur j : p.getJoueur()) {
            if (j != this) { // DINA pr que on ignore l'offre de notre propre joueur
                offresAdversaires.add(j.getOffre());
            }
    	}
        super.offre = strategie.choisirMonOffre(this, p.getPioche(), offresAdversaires);
    }

  
    
    /**
     * Demande à la stratégie de déterminer quelle carte prendre parmi toutes les offres adverses.
     *
     * @param p Partie en cours
     * @return La carte choisie par la stratégie
     */
    private Carte deciderOffreAdversaire(Partie p) {

        // Construction de la liste des offres adverses
        List<Offre> offres = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this && j.getOffre() != null) {
                offres.add(j.getOffre());
            }
        }
        return strategie.prendreOffreAdversaire(offres, this);   
    }
    
    
    
    
    // prendreOffre doit faire appel a deciderOffreAdversaire pour choisir une carte de l'offre d'un des joueur et renvoyer 
    //cette carte pour pouvoir ajouter la carte au deck du joueur virtuel 
    //et decider offre adversaire doit donc retourner un joueur et doit juste faire appel
    // a une méthode de stratégie qui permet de renvoyer le carte le plus interresant à prendre parmes le offres et les joueur
    // normalement le code squellet est bon pour ici (pas dans les classe stategie par exemple), fait juste que tu mettes les parametres 
    // que tu veux là ou j'ai mis des //////// (tu peux te débrouiller un peu comme tu veux en ayant partie en parametre en vrai 
    // tu peux regarder ce que j'ai fait pour choisirMonOffre dans cette classe pour te donner des idées de parametre "plus simple" a gerer

    
    public Joueur prendreOffreEtJoueurSuivant (Partie p){
    	
    	// Il faut retourner le joueur choisi et prendre offre selon la strategie , bien respecter les noms 
    	
    	Joueur joueurChoisi  = strategie.deciderOffreAdversaire(null, null);
    	
    	// A CHANGER / this.prendreOffre(joueurChoisi, p);
    	
    	return joueurChoisi;
    }
    
    
    public void ajouterCarteDeck() { //ORIANE : j'ai fait ca car il y en a besoin pour la partie : a verifier 
		super.ajouterCarteDeck();
	}

}
