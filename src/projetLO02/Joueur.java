package projetLO02;

public class Joueur {
	
	// Attributs d'un joueur
	private String nom; 
	private String typeJoueur; 
	private Offre offre; 
	private Deck cartePosserder; 
	
	// Constructeur 
	public Joueur() {
		this.nom = nom;
        this.typeJoueur = typeJoueur;
        this.deckPossede = new Deck(); // initialisation vide
   
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
        return deckPossede;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

	//Méthodes 
    public void prendreOffre(Offre offre) {
        this.deckPossede.ajouterCarte(offre.getCarte());
    }

    public void piocher(Deck pioche) {
         Carte c = pioche.piocherCarte();
        this.deckPossede.ajouterCarte(c);
    }

    public void creerMonOffre(Carte faceVisible, Carte faceCachee) {
        this.offre = new Offre(faceVisible, faceCachee);
    }

    public void ajouterCarteDeck(Carte carte) {
        this.deckPossede.ajouterCarte(carte);
    }
	
	

}
