package Projet;

import java.util.Scanner;
import java.util.ArrayList;



/**
 * Classe représentant un joueur dans le jeu.
 * <p>
 * Un joueur peut être de type "Humain" ou "Virtuel". 
 * Il possède un deck permanent (Jest) et peut créer et gérer des offres.
 * Cette classe contient également la logique pour interagir avec la pioche,
 * prendre des cartes dans les offres adverses et gérer les cartes visibles et cachées.
 */

public class Joueur {
	
	// Attributs d'un joueur
		protected String nom; 
		protected String typeJoueur; // "Humain" ou "Virtuel"
		protected Offre offre; 
		protected Deck jest; //correspond au carte qu'il possede de maniere permanante
		protected Partie p; // Référence à la partie (pour Scanner, etc.)
		protected Scanner scanner;
		
		// Constructeur 
		 /**
	     * Constructeur principal.
	     * @param nom Nom du joueur
	     * @param typeJoueur Type du joueur ("Humain" ou "Virtuel")
	     * @param partie Référence à la partie (pour Scanner)
	     */
		
		public Joueur(String nom, String typeJoueur, Partie partie ) {
			this.nom = nom;
	        this.typeJoueur = typeJoueur;
	        this.jest = new Deck(this); // Deck permanent
	        this.p = partie;
	        this.scanner = new Scanner(System.in);
		}
		
		// Getters / Setters 
		
		/** @return le nom du joueur */
	    public String getNom() {
	        return nom;
	    }

	    /** @return le type du joueur ("Humain" ou "Virtuel") */
	    public String getTypeJoueur() {
	        return typeJoueur;
	    }

	    /** @return l'offre actuelle du joueur */
	    public Offre getOffre() {
	        return offre;
	    }


	    /** @return le deck permanent du joueur */	
	    public Deck getDeckPossede() {
	        return jest;
	    }
	    
	   
	    /** Définit l'offre actuelle du joueur */
	    public void setOffre(Offre offre) {
	        this.offre = offre;
	    }
	    
	    /**
	     * Vérifie si le joueur est virtuel.
	     * @return true si le joueur est virtuel
	     */
	    public boolean isVirtuel() {
	        return "Virtuel".equalsIgnoreCase(this.typeJoueur);
	    }
	    
		
	    //Méthodes 

	 
	    /**
	     *  Méthode du pattern Visitor.
	     *
	     * @param v Visitor appliqué
	     * @return un entier correspondant au traitement du visitor
	     * @throws GameException si une erreur se produit lors de l'application du visitor
	     */
	    public int accept(Visitor v) throws GameException {
	    	return v.visit(this);
	    }
	    
	    
	    
	    
	    /**
	     * Permet au joueur de prendre soit la carte visible, soit la carte cachée d'une offre.
	     */
	    public void prendreOffre(Joueur j, Partie partie) { 
	    	Scanner sc = new Scanner(System.in);
	        String nomCreateur = (j.getNom() != null)
	                ? j.getNom()
	                : "un joueur inconnu";

	        
	        // Choix sécurisé : visible ou cachée
	        int choix;
	        do {
	            System.out.print("Prends-tu (1) la carte visible ou (2) la carte cachée ? ");

	            choix = sc.nextInt();

	        } while (choix != 1 && choix != 2);

	        // Récupération de la carte
	        Carte carteAPrendre = (choix == 1)
	                ? j.getOffre().carteVisiblePrise()
	                : j.getOffre().carteCacheePrise();

	        // Sécurité
	        if (carteAPrendre == null) {
	            System.out.println("Erreur : la carte choisie n'est pas disponible.");
	            return;
	        }

	        // Ajout au Jest
	        this.jest.ajouterCarte(carteAPrendre);

	        System.out.println(this.nom + " a pris une carte de l'offre de " + nomCreateur );
	    }




	    /**
	     * Crée une offre pour le joueur à partir de cartes visibles et cachées.
	     * Méthode privée utilisée pour encapsuler la création d'offre.
	     *
	     * @param faceVisible Carte qui sera visible
	     * @param faceCachee Carte qui sera cachée
	     */
	    private void creerMonOffre(Carte faceVisible, Carte faceCachee) { //ORIANE : supression de l'attribut partie car inutile et 
	    // passe de la méthode en privé car elle ne sert que pour ici, que a la méthode créerMonOfreHumain
	    	
	    	 this.offre = new Offre(faceVisible, faceCachee, this);         
	    }

	    
	    /**
	     * Logique de création de l'offre pour un joueur HUMAIN, avec interaction console.
	     * Cette méthode gère la pioche, l'affichage et le choix.
	     *  @param p Référence à la partie en cours
	     */
	    public void creerMonOffre(Partie p) {  
	         System.out.println("\n--- Tour de " + this.nom + " (Humain) : Création de l'offre ---");
	        
	        //  Piocher les deux cartes dans la main d'offre
	        Carte c1 = p.getPioche().piocher();
	        Carte c2 = p.getPioche().piocher();
	                

	        
	        
	        System.out.println(this.nom + " pioche deux cartes. Voici vos options :");
	        
	        //Afficher la main avec les indices [1] et [2]
	        
	       
	        System.out.println("Main de " + this.nom + " :");
	        System.out.println("  [1] " + c1);
	        System.out.println("  [2] " + c2);

	        //  Choix de quelle carte est visible
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
	        Carte carteVisible = (choixVisible == 1) ? c1 : c2;
	        Carte carteCachee = (choixVisible == 1) ? c2 : c1;

	        // Créer l'offre
	        creerMonOffre(carteVisible, carteCachee);
	        
	        System.out.println("Offre de " + this.nom + " créée : Visible (" + carteVisible.toString() + ") / Cachée.");
	    }
	    
	  
	    
	    /**
	     * Ajoute une carte au deck de cartes possédées par le joueur.
	     * @param carte Carte à ajouter
	     */
	    public void ajouterCarteDeck(Carte carte) { 
	        this.jest.ajouterCarte(carte);
	        
	    } 
	    
	    /**
	     * Ajoute toutes les cartes de la main temporaire au deck permanent (Jest)
	     */
	    public void ajouterCarteDeck() {

	         // Transfert de toutes les cartes
	        for (Carte c : this.offre.getListeCarte() ) // ajouter l'offre au deck 
	        {	if (c!= null) {
	            this.jest.ajouterCarte(c);
	        	}
	         }
	    }
	    
	    
	    
	    /**
	     * Permet au joueur humain de choisir une offre adverse et de prendre une carte.
	     * <p>
	     * Cette méthode affiche les offres disponibles, sécurise le choix et ajoute
	     * la carte choisie au deck du joueur.
	     *
	     * @param p Référence à la partie en cours
	     * @return Le joueur dont l'offre a été prise
	     */
	    public Joueur prendreOffreEtJoueurSuivant(Partie p) {
	    	
	    	Scanner sc = new Scanner(System.in);

	    	System.out.println(this.nom + " : L'offre de quel joueur veux-tu prendre ?");

	    	ArrayList<Joueur> joueurs = p.getJoueur();

	    	// Lister uniquement les choix valides = les offre qui peuvent etre prise
	    	ArrayList<Integer> choixValides = new ArrayList<>();

	    	for (int i = 0; i < joueurs.size(); i++) {
	    	    if (joueurs.get(i).getOffre().estDisponible()) {
	    	        System.out.println(i + " pour " + joueurs.get(i).getNom()+ " ( " + joueurs.get(i).getOffre()+ " )");
	    	        choixValides.add(i);
	    	    }
	    	}

	    	// Sécurisation du choix
	    	int choix = -1;

	    	while (!choixValides.contains(choix)) {
	    	    System.out.print("Ton choix : ");

	    	    if (sc.hasNextInt()) {
	    	        choix = sc.nextInt();

	    	        if (!choixValides.contains(choix)) {
	    	            System.out.println("Choix invalide. Sélectionne un joueur disponible dans la liste.");
	    	        }

	    	    } else {
	    	        System.out.println("Entrée invalide.");
	    	        sc.next(); // vide le buffer pour éviter la boucle infinie
	    	    }
	    	}

	    	Joueur joueurChoisi = joueurs.get(choix);

	    	// Exécuter l'action une fois le choix validé
	    	this.prendreOffre(joueurChoisi, p);

	    	return joueurChoisi;
	    }
	    
	    public String toString() {
	    	return nom + " : joueur " + typeJoueur;
	    }

		
}
