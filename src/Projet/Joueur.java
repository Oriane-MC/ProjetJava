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
		protected ArrayList<Carte> mainOffre; // La main temporaire de 2 cartes pour former l'offre actuelle.
		// ORIANE  : mainOffre remplacer par juste une liste basique pour plus de simplicité et changements du code relatif a ca 
		
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
	        this.mainOffre = new ArrayList(); // Main temporaire
	        this.p = partie;
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
	    
	    public ArrayList getMainOffre() { 
	    	return mainOffre; 
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
	     * Permet au joueur de choisir une offre valide parmi celles des adversaires.
	     * @param offres : ensemble de toutes les offres posées
	     * @param partie : pour accès au Scanner
	     * @return l'offre choisie par le joueur
	     */
	    public Offre choisirOffre(List<Offre> offres, Partie partie) { //ORIANE = je pense qu'il faut supprimer cett méthde : pas utile
	    	// : elle n'est pas utiliser das le reste de ton code . Cette méthode est utile seuelement pour un joueur VIRTUEL

	        System.out.println("\n" + this.nom + ", choisis l'offre d'un adversaire :");

	        // 1) Filtrer les offres valides : pas prises + pas la sienne
	        List<Offre> disponibles = new ArrayList<>();
	        for (Offre o : offres) {
	            if (o != null && o.estDisponible() && o.getCreateur() != this) {
	                disponibles.add(o);
	            }
	        }

	        // 2) Si rien de disponible → erreur logique mais on protège
	        if (disponibles.isEmpty()) {
	            System.out.println("Aucune offre disponible !");
	            return null;
	        }

	        // 3) Affichage numéroté des offres
	        for (int i = 0; i < disponibles.size(); i++) {
	            Offre o = disponibles.get(i);
	            System.out.println("  " + (i + 1) + ") Offre de " 
	                    + o.getCreateur().getNom()
	                    + " : visible = " + o.getCarteVisible());
	        }

	        // 4) Choix du joueur, sécurisé
	        int choix;
	        do {
	            System.out.print("Ton choix (1 à " + disponibles.size() + ") : ");

	            while (!partie.getScanner().hasNextInt()) {
	                System.out.print("Veuillez entrer un nombre valable : ");
	                partie.getScanner().next();
	            }

	            choix = partie.getScanner().nextInt();

	        } while (choix < 1 || choix > disponibles.size());

	        Offre selection = disponibles.get(choix - 1);

	        System.out.println(this.nom + " a choisi l'offre de " 
	                + selection.getCreateur().getNom());

	        return selection;
	    }

	  
	    
	    /**
	     * Permet au joueur de prendre soit la carte visible, soit la carte cachée d'une offre.
	     */
	    public void prendreOffre(Joueur j, Partie partie) { //ORIANE  = changement des parametre et donc du code qui en découle
	    	// j'ai changé le parametre offre en joueur, a verifier car pas sur si j'ai bien tout remplacer les offre par j.getOffre()

	        // 1) Vérifier qu'elle est disponible
	        if (!j.getOffre().estDisponible()) {
	            System.out.println("Cette offre a déjà été prise !");
	            return;
	        }

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

	            while (!partie.getScanner().hasNextInt()) {
	                System.out.print("Veuillez entrer 1 ou 2 : ");
	                partie.getScanner().next();
	            }

	            choix = partie.getScanner().nextInt();

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

	        System.out.println(this.nom + " a pris l'offre de " + nomCreateur +
	                " et a reçu : " + carteAPrendre);
	    }




	    /**
	     * Crée l'offre du joueur en utilisant le constructeur à 2 arguments de Offre.
	     */
	    private void creerMonOffre(Carte faceVisible, Carte faceCachee) { //ORIANE : supressionde l'attribut partie car inutile et 
	    // passe de la méthode en privé car elle ne sert que pour ici, que a la méthode créerMonOfreHumain
	    	 this.offre = new Offre(faceVisible, faceCachee, this); 
	        // Retirer les cartes de la MAIN D'OFFRE après la création de l'offre
	        this.mainOffre.remove(faceVisible);
	        this.mainOffre.remove(faceCachee);
	        
	    }

	    
	    /**
	     * Logique de création de l'offre pour un joueur HUMAIN, avec interaction console.
	     * Cette méthode gère la pioche, l'affichage et le choix.
	     */
	    public void creerMonOffreHumain(Partie p) { // ORIANE : changement de parametre, j'ai mis que partie 
	         System.out.println("\n--- Tour de " + this.nom + " (Humain) : Création de l'offre ---");
	        
	        // 1. Piocher les deux cartes dans la main d'offre
	        Carte c1 = p.getPioche().piocher();
	        Carte c2 = p.getPioche().piocher();
	        
	        if (c1 == null || c2 == null) {
	            System.out.println("Erreur: Pas assez de cartes dans la pioche.");
	            return;
	        }

	        // Ajouter les cartes à la main temporaire (mainOffre)
	        this.mainOffre.add(c1);
	        this.mainOffre.add(c2);
	        
	        System.out.println(this.nom + " pioche deux cartes. Voici vos options :");
	        
	        // 2. Afficher la main avec les indices [1] et [2]
	        
	        // Affichage corrigé
	        System.out.println("Main de " + this.nom + " :");
	        System.out.println("  [1] " + mainOffre.get(0).toString());
	        System.out.println("  [2] " + mainOffre.get(1).toString());

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
	        
	        Carte carteVisible = mainOffre.get(choixVisibleIndex);
	        Carte carteCachee = mainOffre.get(choixCacheIndex);
	        
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
	    public void ajouterMainAuDeck() {

	        // METTRE UN EXCEPTION Sécurité : si aucune carte en mainOffre
	       
	        // Transfert de toutes les cartes
	        for (Carte c : this.mainOffre) {
	            this.jest.ajouterCarte(c);
	        }
	        // Vider la main 
	        this.mainOffre.clear();
	        System.out.println("Les cartes de la main d'offre ont été ajoutées au Jest de " + this.nom + ".");
	    }

		
}
