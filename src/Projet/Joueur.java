package Projet;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

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
	    
	    /**
	     * Vérifie si le joueur est virtuel.
	     * @return true si le joueur est virtuel
	     */
	    public boolean isVirtuel() {
	        return "Virtuel".equalsIgnoreCase(this.typeJoueur);
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
	     * Permet au joueur de prendre soit la carte visible, soit la carte cachée d'une offre.
	     */
	    public void prendreOffre(Joueur j, Partie partie) { //ORIANE  = changement des parametre et donc du code qui en découle
	    	// j'ai changé le parametre offre en joueur, a verifier car pas sur si j'ai bien tout remplacer les offre par j.getOffre()
	    	Scanner sc = new Scanner(System.in);
	        String nomCreateur = (j.getNom() != null)
	                ? j.getNom()
	                : "un joueur inconnu";

	        System.out.println("\n" + this.nom + ", tu dois choisir une carte dans l'offre de " + nomCreateur + " :");
	        System.out.println(" ► Carte visible : " + j.getOffre().getCarteVisible());
	        System.out.println(" ► Carte cachée : [???]");

	        // 2) Choix sécurisé : visible ou cachée
	        int choix;
	        do {
	            System.out.print("Prends-tu (1) la carte visible ou (2) la carte cachée ? ");

	            choix = sc.nextInt();

	        } while (choix != 1 && choix != 2);

	        // 3) Récupération de la carte
	        Carte carteAPrendre = (choix == 1)
	                ? j.getOffre().carteVisiblePrise()
	                : j.getOffre().carteCacheePrise();

	        // 4) Sécurité
	        if (carteAPrendre == null) {
	            System.out.println("Erreur : la carte choisie n'est pas disponible.");
	            return;
	        }

	        // 5) Ajout au Jest
	        this.jest.ajouterCarte(carteAPrendre);

	        System.out.println(this.nom + " a pris une carte de l'offre de " + nomCreateur );
	    }




	    /**
	     * Crée l'offre du joueur en utilisant le constructeur à 2 arguments de Offre.
	     */
	    private void creerMonOffre(Carte faceVisible, Carte faceCachee) { //ORIANE : supression de l'attribut partie car inutile et 
	    // passe de la méthode en privé car elle ne sert que pour ici, que a la méthode créerMonOfreHumain
	    	
	    	 this.offre = new Offre(faceVisible, faceCachee, this);         
	    }

	    
	    /**
	     * Logique de création de l'offre pour un joueur HUMAIN, avec interaction console.
	     * Cette méthode gère la pioche, l'affichage et le choix.
	     */
	    public void creerMonOffre(Partie p) { // ORIANE : changement de parametre, j'ai mis que partie 
	         System.out.println("\n--- Tour de " + this.nom + " (Humain) : Création de l'offre ---");
	        
	        // 1. Piocher les deux cartes dans la main d'offre
	        Carte c1 = p.getPioche().piocher();
	        Carte c2 = p.getPioche().piocher();
	                

	        
	        
	        System.out.println(this.nom + " pioche deux cartes. Voici vos options :");
	        
	        // 2. Afficher la main avec les indices [1] et [2]
	        
	        // Affichage corrigé
	        System.out.println("Main de " + this.nom + " :");
	        System.out.println("  [1] " + c1);
	        System.out.println("  [2] " + c2);

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
	        Carte carteVisible = (choixVisible == 1) ? c1 : c2;
	        Carte carteCachee = (choixVisible == 1) ? c2 : c1;

	        // 4. Créer l'offre
	        creerMonOffre(carteVisible, carteCachee);
	        
	        System.out.println("Offre de " + this.nom + " créée : Visible (" + carteVisible.toString() + ") / Cachée.");
	    }
	    
	  
	    
	    /**
	     * Ajoute une carte au deck de cartes possédées par le joueur.
	     */
	    public void ajouterCarteDeck(Carte carte) { //Oriane = suppression du parametre partie 
	        this.jest.ajouterCarte(carte);
	        
	    } 
	    
	    /**
	     * Ajoute toutes les cartes de la main temporaire au deck permanent (Jest)
	     */
	    public void ajouterCarteDeck() {

	        // METTRE UN EXCEPTION Sécurité : si aucune carte en mainOffre
	       
	        // Transfert de toutes les cartes
	        for (Carte c : this.offre.getListeCarte() ) // ajouter l'offre au deck 
	        {	if (c!= null) {
	            this.jest.ajouterCarte(c);
	        	}
	         }
	    }
	    
	    public Joueur prendreOffreEtJoueurSuivant(Partie p) {
	    	
	    	Scanner sc = new Scanner(System.in);

	    	System.out.println(this.nom + " : L'offre de quel joueur veux-tu prendre ?");

	    	ArrayList<Joueur> joueurs = p.getJoueur();

	    	// Lister uniquement les choix valides = les offre qui peuvent etre prise
	    	ArrayList<Integer> choixValides = new ArrayList<>();

	    	for (int i = 0; i < joueurs.size(); i++) {
	    	    if (joueurs.get(i).getOffre().estDisponible()) {
	    	        System.out.println(i + " pour " + joueurs.get(i).getNom());
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

		
}
