package Modèle;

import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant un joueur dans le jeu.
 * Compatible Console (Scanner) et IHM (Evènements).
 */
public class Joueur implements Serializable {
    
    // --- Attributs ---
    protected String nom; 
    protected String typeJoueur; // "Humain" ou "Virtuel"
    protected Offre offre; 
    protected Deck jest; // Cartes possédées de manière permanente
    protected transient Partie p; // Référence à la partie
    protected transient Scanner scanner;
    private static final long serialVersionUID = 1L;
    private ArrayList<Joueur> dejaJoue = new ArrayList<>();

    // --- Constructeur ---
    public Joueur(String nom, String typeJoueur, Partie partie ) {
        this.nom = nom;
        this.typeJoueur = typeJoueur;
        this.jest = new Deck(this); 
        this.p = partie;
        this.scanner = new Scanner(System.in);
    }
    
    // --- Getters / Setters ---
    
    public String getNom() { return nom; }
    public String getTypeJoueur() { return typeJoueur; }
    public Offre getOffre() { return offre; }
    public Deck getDeckPossede() { return jest; }
    public void setOffre(Offre offre) { this.offre = offre; }
    
    public boolean isVirtuel() {
        return "Virtuel".equalsIgnoreCase(this.typeJoueur);
    }
    
    public void setPartie(Partie p) { this.p = p; }
    public void setScanner(Scanner sc) { this.scanner = sc; }

    // --- MÉTHODES MVC / GRAPHIQUES (Ajouts pour l'IHM) ---

    /**
     * Version MVC de la création d'offre.
     * Appelée par le contrôleur quand l'utilisateur clique sur une carte.
     */
    public void choisirOffreMVC(Carte visible, Carte cachee) {
        creerMonOffre(visible, cachee);
    }

    /**
     * Version MVC de la prise d'offre.
     * Effectue l'action sans attendre d'entrée Scanner.
     */
    public void prendreOffreMVC(Joueur victime, int choixFace) {
        Carte carteAPrendre = (choixFace == 1) 
                ? victime.getOffre().carteVisiblePrise() 
                : victime.getOffre().carteCacheePrise();

        if (carteAPrendre != null) {
            this.jest.ajouterCarte(carteAPrendre);
            // On notifie la partie pour passer au joueur suivant
            p.finDeTourJoueur(this, victime);
        }
    }

    // --- TES MÉTHODES ORIGINALES (Conservées telles quelles) ---

    public int accept(Visitor v) throws GameException {
        return v.visit(this);
    }

    public void prendreOffre(Joueur j, Partie partie) { 
        Scanner sc = new Scanner(System.in);
        String nomCreateur = (j.getNom() != null) ? j.getNom() : "un joueur inconnu";

        int choix;
        do {
            System.out.print("Prends-tu (1) la carte visible ou (2) la carte cachée ? ");
            choix = sc.nextInt();
        } while (choix != 1 && choix != 2);

        Carte carteAPrendre = (choix == 1)
                ? j.getOffre().carteVisiblePrise()
                : j.getOffre().carteCacheePrise();

        if (carteAPrendre == null) {
            System.out.println("Erreur : la carte choisie n'est pas disponible.");
            return;
        }

        this.jest.ajouterCarte(carteAPrendre);
        System.out.println(this.nom + " a pris une carte de l'offre de " + nomCreateur );
    }

    private void creerMonOffre(Carte faceVisible, Carte faceCachee) { 
         this.offre = new Offre(faceVisible, faceCachee, this);         
    }

    public void creerMonOffre(Partie p) {  
        System.out.println("\n--- Tour de " + this.nom + " (Humain) : Création de l'offre ---");
        Carte c1 = p.getPioche().piocher();
        Carte c2 = p.getPioche().piocher();
        
        System.out.println("Main de " + this.nom + " :");
        System.out.println("  [1] " + c1);
        System.out.println("  [2] " + c2);

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
                scanner.next();
            }
        }
        Carte carteVisible = (choixVisible == 1) ? c1 : c2;
        Carte carteCachee = (choixVisible == 1) ? c2 : c1;

        creerMonOffre(carteVisible, carteCachee);
        System.out.println("Offre de " + this.nom + " créée : Visible (" + carteVisible.toString() + ") / Cachée.");
    }

    public void ajouterCarteDeck(Carte carte) { 
        this.jest.ajouterCarte(carte);
    } 

    public void ajouterCarteDeck() {
        for (Carte c : this.offre.getListeCarte() ) {
            if (c != null) {
                this.jest.ajouterCarte(c);
            }
        }
    }

    public Joueur prendreOffreEtJoueurSuivant(Partie p, ArrayList<Joueur> dejaJoueCeTour) {
        Scanner sc = this.scanner;
        System.out.println(this.nom + " : L'offre de quel joueur veux-tu prendre ?");

        ArrayList<Joueur> joueurs = p.getJoueur();
        ArrayList<Integer> choixValides = new ArrayList<>();
        
        for (int i = 0; i < joueurs.size(); i++) {
            Joueur j = joueurs.get(i);
            if (j != this && j.getOffre() != null && j.getOffre().estDisponible()) {
                System.out.println(i + " pour " + j.getNom() + " ( " + j.getOffre() + " )");
                choixValides.add(i);
            }
        }
        
        if (choixValides.isEmpty() && this.getOffre() != null && this.getOffre().estDisponible()) {
            int index = joueurs.indexOf(this);
            System.out.println(index + " pour toi-même (dernière offre disponible)");
            choixValides.add(index);
        }

        int choix = -1;
        while (!choixValides.contains(choix)) {
            System.out.print("Ton choix : ");
            if (sc.hasNextInt()) {
                choix = sc.nextInt();
                if (!choixValides.contains(choix)) {
                    System.out.println("Choix invalide.");
                }
            } else {
                System.out.println("Entrée invalide.");
                sc.next();
            }
        }

        Joueur joueurChoisi = joueurs.get(choix);
        this.prendreOffre(joueurChoisi, p);
        dejaJoueCeTour.add(this);
        
        Joueur joueurSuivant = null; 
        if (!dejaJoueCeTour.contains(joueurChoisi)) {
            joueurSuivant = joueurChoisi;
        } else {
            int valeurMax = -1;
            for (Joueur j : joueurs) {
                if (!dejaJoueCeTour.contains(j)) {
                    int valeurVisible = j.getOffre().getCarteVisible().getValeur();
                    if (valeurVisible > valeurMax) {
                        valeurMax = valeurVisible;
                        joueurSuivant = j;
                    } else if (valeurVisible == valeurMax && joueurSuivant != null) {
                        if (p.getForceCouleur(j.getOffre().getCarteVisible()) > 
                            p.getForceCouleur(joueurSuivant.getOffre().getCarteVisible())) {
                            joueurSuivant = j;
                        }
                    }
                }
            }
        }
        return joueurSuivant;
    }

    @Override
    public String toString() {
        return nom + " : joueur " + typeJoueur;
    }
}