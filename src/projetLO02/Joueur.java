package projetLO02;

public class Joueur {
	
	// Attributs d'un joueur
	private String nom; 
	private String typeJoueur; 
	private Offre offre; 
	private Deck cartePosseder; 
	
	// Constructeur 
	public Joueur(String nom, String typeJoueur, Offre offre, Deck cartePosseder) {
		this.nom = nom;
        this.typeJoueur = typeJoueur;
        this.cartePosseder = new Deck(this); // initialisation vide
   
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
        return cartePosseder;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

	//Méthodes 
    /**
     * Prend la carte visible de l'offre et l'ajoute au deck du joueur.
     */
    public void prendreOffre(Offre offre) {
        // Correction: Utilise la méthode getCarteVisible() de la classe Offre
        Carte carteAPrendre = offre.carteVisiblePrise(); // Utilise la méthode prise pour changer l'état de l'offre
        if (carteAPrendre != null) {
            this.cartePosseder.ajouterCarte(carteAPrendre);
            System.out.println(this.nom + " a pris la carte visible de l'offre.");
        }
    }

    /**
     * Pioche une carte du deck de pioche (passé en paramètre) et l'ajoute au deck du joueur.
     */
    public void piocher(Deck pioche) {
         Carte c = pioche.piocherCarte();
        if (c != null) {
            this.cartePosseder.ajouterCarte(c);
            System.out.println(this.nom + " a pioché une carte.");
        } else {
             System.out.println("La pioche est vide.");
        }
    }


    /**
     * Crée l'offre du joueur en utilisant le constructeur à 2 arguments de Offre.
     */
    public void creerMonOffre(Carte faceVisible, Carte faceCachee) {
        this.offre = new Offre(faceVisible, faceCachee, null);
    }

    /**
     * Ajoute une carte au deck de cartes possédées par le joueur.
     */
    public void ajouterCarteDeck(Carte carte) {
        // Correction: Utilise le nom d'attribut correct 'cartePosserder'
        this.cartePosseder.ajouterCarte(carte);
    } 
	

}
