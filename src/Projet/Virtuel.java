package Projet;

import java.io.*;

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
public class Virtuel extends Joueur implements Serializable {

	/** Stratégie appliquée par le joueur virtuel pour ses décisions. */
    private  transient Strategie strategie;
    private static final long serialVersionUID = 1L;
    private int strategieInt;

    /**
     * Constructeur du joueur virtuel
     * @param Nom du joueur
     * @param strategie utilisé pour ses décisions 
     * @param p Référence à la partie pour accès au scanner, etc.
     */
    public Virtuel(String nom, int strategie, Partie partie ) { 
        super(nom, "Virtuel", partie);
        
        if (strategie == 0) { 
        	Random r = new Random();
        	int n = r.nextInt(3) + 1;
        	strategie = n;
        }
        
        this.strategieInt = strategie;  // mémoriser la stratégie

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
   /**
    * Réinitialiser la stratégie apres chargement d'une sauvegarde  
    */
    public void reinitialiserStrategie() {
        switch (strategieInt) {
            case 1 -> this.strategie = new StrategieBasique();
            case 2 -> this.strategie = new StratégieDéfensive();
            case 3 -> this.strategie = new StrategiesAggressive();
        }
    }


    // Setter 
    /**
     * Possibilité de changer  la stratégie du joueur virtuel en cours de partie.
     * @param strategie Nouvelle stratégie à appliquer.
     */
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    
    @Override
    public void setPartie(Partie p) {
        super.setPartie(p);
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

    
 // Dans Virtuel.java

 // Cette méthode est appelée automatiquement par Java après le chargement (desérialisation)
 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
     in.defaultReadObject(); // On charge les données normales (nom, score...)
     // On recrée la stratégie qui avait disparu
     this.reinitialiserStrategie(); 
 }
  
    @Override
    /**
     * Permet au joueur virtuel de choisir une offre adverse et de déterminer
     * quel joueur cibler pour prendre une carte.
     * <p>
     * La stratégie appliquée décide automatiquement quel joueur et quelle carte prendre et prend la carte.
     *
     * @param p Référence à la partie en cours.
     * @return Le joueur choisi par le joueur virtuel pour prendre une carte.
     */
    
    public Joueur prendreOffreEtJoueurSuivant (Partie p, ArrayList<Joueur> dejaJoue){
    	ArrayList<Joueur> joueurs = p.getJoueur();
       Scanner sc = new Scanner(System.in);

    	System.out.println("C'est au tour du joueur virtuel " +this.nom + "  de choisir une offre ");

    	// Construire la liste des offres adverses encore disponibles
        List<Offre> offresAdverses = new ArrayList<>();
        for (Joueur j : p.getJoueur()) {
            if (j != this && j.getOffre() != null && j.getOffre().estDisponible()) {
            	
            	offresAdverses.add(j.getOffre());
            }
        }   	
     
        Joueur joueurChoisi = null;
        
        if (offresAdverses.isEmpty() && this.getOffre() != null && this.getOffre().estDisponible()) {
            // Cas du dernier joueur : obligé de prendre sa propre offre
            joueurChoisi = this;
            System.out.println( " Il prend sa propre offre (dernière offre disponible)");
	        
        } else if (!offresAdverses.isEmpty()) {
            // Appel à TA méthode de stratégie
            joueurChoisi = strategie.deciderOffreAdversaire(offresAdverses, this);
        }
      
        
        if (!dejaJoue.contains(this)) {
            dejaJoue.add(this);
        }
        
        Joueur joueurSuivant = null;
        
     // Priorité 1 : Le joueur dont on vient de prendre l'offre s'il n'a pas encore pris de carte
        if (joueurChoisi != null && !dejaJoue.contains(joueurChoisi)) {
            joueurSuivant = joueurChoisi;
        } 
        // Priorité 2 : Le joueur n'ayant pas encore joué avec la carte visible la plus forte
        else {
            int valeurMax = -1;
            for (Joueur j : joueurs) {
                if (!dejaJoue.contains(j) && j.getOffre() != null && j.getOffre().getCarteVisible() != null) {
                    int valeur = j.getOffre().getCarteVisible().getValeur();
                    
                    if (valeur > valeurMax) {
                        valeurMax = valeur;
                        joueurSuivant = j;
                    } else if (valeur == valeurMax && joueurSuivant != null) {
                        // Utilisation de ta méthode de force de couleur en cas d'égalité
                        if (p.getForceCouleur(j.getOffre().getCarteVisible()) > 
                            p.getForceCouleur(joueurSuivant.getOffre().getCarteVisible())) {
                            joueurSuivant = j;
                        }
                    }
                }
            }
        }
      // Retourner le joueur choisi
        return joueurSuivant;
    
    }
    
     
    public void ajouterCarteDeck() {  
		super.ajouterCarteDeck();
	}
    
    public String toString() {
    	return nom + " : joueur " + typeJoueur + " ( "+ strategie+" )";
    }

}
