package Projet;

import java.util.List;
import java.util.Scanner;

public class Joueur {
	
	// Attributs d'un joueur
		private String nom; 
		private String typeJoueur; 
		private Offre offre; 
		private Deck jest; //correspond au carte qu'il possede de maniere permanante
		private Deck mainOffre; // La main temporaire de 2 cartes pour former l'offre actuelle.
		
	// Constructeur 
		
		public Joueur(String nom, String typeJoueur ) {
			this.nom = nom;
	        this.typeJoueur = typeJoueur;
	        this.jest = new Deck(this); 
	        this.mainOffre = new Deck(this); // Nouvelle main temporaire
		}
		
		// Getters / Setters 
	    public String getNom() {
	        return nom;
	    }

	    public String getTypeJoueur() {
	        return typeJoueur;
	    }

	    public Offre getOffre() {
	        return offre;
	    }

	    public Deck getDeckPossede() {
	        return jest;
	    }

	    public void setOffre(Offre offre) {
	        this.offre = offre;
	    }

		//Méthodes 
	    
	    /** 
	     * méthode relatif au pattern Visitor
	     * @param v
	     * @return
	     * @throws GameException 
	     */
	    public int accept(Visitor v) throws GameException {
	    	return v.visit(this);
	    }
	    
	    
	    
	    
	    /**
	     * Prend la carte visible de l'offre et l'ajoute au deck du joueur.
	     */
	    public void prendreOffre(Offre offre) {
	    	Joueur createurOffre = offre.getCreateur(); 
	        String nomCreateur = (createurOffre != null) ? createurOffre.getNom() : "un joueur inconnu";
	        
	        Carte carteAPrendre = offre.carteVisiblePrise();
	    	 if (carteAPrendre != null) {
	            this.jest.ajouterCarte(carteAPrendre);
	            System.out.println(this.nom + " a pris l'offre de " + nomCreateur + 
                        " et a reçu la carte : " + carteAPrendre.toString() + " pour son Jest.");
	        } else {
	        	System.out.println("L'offre est vide ou déjà prise.");
	        }
	    }

	    /**
	     * Pioche une carte du deck de pioche (passé en paramètre) et l'ajoute au deck du joueur.
	     * C'est la méthode de secours si le joueur décide de ne pas prendre d'offre adverse.
	     */
	    public void piocher(Deck pioche) {
	         Carte c = pioche.piocherCarte();
	        if (c != null) {
	            this.jest.ajouterCarte(c);
	            System.out.println(this.nom + " ne prend pas d'offre et pioche une carte : " + c.toString() + " pour son Jest.");
	            } else {
	             System.out.println("La pioche est vide.");
	        }
	    }


	    /**
	     * Crée l'offre du joueur en utilisant le constructeur à 2 arguments de Offre.
	     */
	    public void creerMonOffre(Carte faceVisible, Carte faceCachee) {
	    	// Le constructeur est supposé être : new Offre(carteVisible, carteCachee, Joueur createur)
	        this.offre = new Offre(faceVisible, faceCachee, this); 
	        
	        // Retirer les cartes de la MAIN D'OFFRE après la création de l'offre
	        this.mainOffre.retirerCarte(faceVisible);
	        this.mainOffre.retirerCarte(faceCachee);
	        
	    }

	    
	    /**
	     * Logique de création de l'offre pour un joueur HUMAIN, avec interaction console.
	     * Cette méthode gère la pioche, l'affichage et le choix.
	     */
	    public void creerMonOffreHumain(Deck pioche) {
	        // Le Scanner devrait être géré par la classe principale de la partie
	        Scanner scanner = new Scanner(System.in);
	        
	        System.out.println("\n--- Tour de " + this.nom + " (Humain) : Création de l'offre ---");
	        
	        // 1. Piocher les deux cartes dans la main d'offre
	        Carte c1 = pioche.piocherCarte();
	        Carte c2 = pioche.piocherCarte();
	        
	        if (c1 == null || c2 == null) {
	            System.out.println("Erreur: Pas assez de cartes dans la pioche.");
	            return;
	        }

	        // Ajouter les cartes à la main temporaire (mainOffre)
	        this.mainOffre.ajouterCarte(c1);
	        this.mainOffre.ajouterCarte(c2);
	        
	        System.out.println(this.nom + " pioche deux cartes. Voici vos options :");
	        
	        // 2. Afficher la main avec les indices [1] et [2]
	        List<Carte> cartesEnMain = this.mainOffre.getCartes();
	        
	        // Affichage corrigé
	        System.out.println("Main de " + this.nom + " :");
	        System.out.println("  [1] " + cartesEnMain.get(0).toString());
	        System.out.println("  [2] " + cartesEnMain.get(1).toString());

	        // 3. Choix de quelle carte est visible
	        int choixVisible = 0;
	        
	        while (choixVisible != 1 && choixVisible != 2) {
	            System.out.print("Choisissez le NUMÉRO de la carte à mettre en VISIBLE [1 ou 2] : ");
	            if (scanner.hasNextInt()) {
	                choixVisible = scanner.nextInt();
	                if (choixVisible != 1 && choixVisible != 2) {
	                    System.out.println("Choix invalide. Entrez 1 ou 2.");
	                }
	            } else {
	                System.out.println("Entrée invalide.");
	                scanner.next(); // Consomme l'entrée invalide
	            }
	        }
	     // Détermination des cartes
	        int choixVisibleIndex = choixVisible - 1;
	        int choixCacheIndex = (choixVisible == 1) ? 1 : 0;
	        
	        Carte carteVisible = cartesEnMain.get(choixVisibleIndex);
	        Carte carteCachee = cartesEnMain.get(choixCacheIndex);
	        
	        // 4. Créer l'offre
	        creerMonOffre(carteVisible, carteCachee);
	        
	        System.out.println("Offre de " + this.nom + " créée : Visible (" + carteVisible.toString() + ") / Cachée.");
	    }
	    
	    /**
	     * Utile pour différencier l'humain du virtuel.
	     */
	    public boolean isVirtuel() {
	        return this.typeJoueur.equalsIgnoreCase("Virtuel"); 
	    }
	    
	    /**
	     * Ajoute une carte au deck de cartes possédées par le joueur.
	     */
	    public void ajouterCarteDeck(Carte carte) {
	        this.jest.ajouterCarte(carte);
	    } 
		
}
