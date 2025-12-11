package Projet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * Classe représentant un joueur virtuel (IA).
 * Il utilise une stratégie pour :
 *  - créer son offre
 *  - prendre offre et determiner le prochain joueur  
 *  - set la stratégie
 * 
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
    public Virtuel(String nom, int strategie, Partie partie ) { //ORIANE : ajout pour faire choisir la stategie au joueur
        super(nom, "Virtuel", partie);
        
        if (strategie == 0) { 
        	Random r = new Random();
        	int n = r.nextInt(3) + 1;
        	strategie = n;
        }
        
        switch (strategie) {
        	case 1:
        		this.strategie = new StrategieBasique();
        		break;
        	case 2 : 
        		this.strategie = new StratégieDéfensive();
        		break;
        	case 3 :
        		this.strategie = new StrategiesAggressive();
        		break;
        }
    }
    
    /**
     * Retourne la stratégie actuellement utilisée par le joueur virtuel.
     *
     * @return La stratégie appliquée.
     */ 
    public Strategie getStrategie() {
    	return strategie;
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
     * Crée l'offre du joueur virtuel en se basant sur sa stratégie.
     * <p>
     * Cette méthode sélectionne une carte visible et une carte cachée
     * à partir de la main du joueur virtuel.
     *
     * @param p Référence à la partie en cours, utilisée pour accéder à la pioche et aux offres adverses.
     */
    public void creerMonOffre(Partie p) { 
    	
    	ArrayList<Offre> offresAdversaires = new ArrayList();
    	for (Joueur j : p.getJoueur()) {
            if (j != this) { 
                offresAdversaires.add(j.getOffre());
            }
    	}
        super.offre = strategie.choisirMonOffre(this, p.getPioche(), offresAdversaires);
    }

  
    
    /**
     * Permet au joueur virtuel de choisir une offre adverse et de déterminer
     * quel joueur cibler pour prendre une carte.
     * <p>
     * La stratégie appliquée décide automatiquement quel joueur et quelle carte prendre et prend la carte.
     *
     * @param p Référence à la partie en cours.
     * @return Le joueur choisi par le joueur virtuel pour prendre une carte.
     */
    
    public Joueur prendreOffreEtJoueurSuivant (Partie p){
    	Scanner sc = new Scanner(System.in);

    	System.out.println("C'est au tour du joueur virtuel " +this.nom + "  de choisir une offre ");

    	// Construire la liste des offres adverses encore disponibles
        List<Offre> offresAdverses = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this && j.getOffre() != null && j.getOffre().estDisponible()) {
                offresAdverses.add(j.getOffre());
            }
        }   	
     //  La stratégie décide quel joueur cibler
        Joueur joueurChoisi = strategie.deciderOffreAdversaire(offresAdverses, this);          
        
      
      // Retourner le joueur choisi
        return joueurChoisi;
    
    }
    
     
    public void ajouterCarteDeck() {  
		super.ajouterCarteDeck();
	}

}
