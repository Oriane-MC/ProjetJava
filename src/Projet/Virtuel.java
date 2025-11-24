package Projet;


import java.util.List;
import java.util.Comparator;
 
public class Virtuel extends Joueur {
    // attributs
    private boolean virtuel;

    // constructeur
    public Virtuel(String nom) {
        super(nom, "Virtuel"); // on appelle le constructeur de Joueur
        this.virtuel = true;
    }

    // méthodes
    /**
     * Simule la décision du joueur virtuel pour créer son offre.
     * Le joueur virtuel doit avoir exactement deux cartes dans son deck temporaire
     * après la distribution.
     * * STRATÉGIE SIMPLE:
     * 1. Il détermine laquelle des deux cartes est la plus forte (pour jouer en premier).
     * 2. La carte la plus forte va face visible (pour remporter le tour).
     * 3. La carte la plus faible va face cachée.
     * * Cette méthode crée l'objet Offre et le stocke dans l'attribut 'offre' du Joueur.
     */
    
    public void choisirEtCreerOffre() {
        // Le deck du joueur contient les 2 cartes distribuées pour ce tour
        List<Carte> cartesEnMain = this.getDeckPossede().getCartes();
        
        if (cartesEnMain.size() != 2) {
            System.err.println("ERREUR: Le joueur " + this.getNom() + " n'a pas exactement 2 cartes pour faire une offre.");
            return;
        }

        Carte c1 = cartesEnMain.get(0);
        Carte c2 = cartesEnMain.get(1);

        // Définition du comparateur pour classer les cartes selon les règles de départage (valeur puis couleur)
        Comparator<Carte> comparateurCarte = (carteA, carteB) -> {
            // As = 1, Joker = 0 (Page 4, Take Cards)
            int valA = (carteA.getCouleur() == Couleur.JOKER) ? 0 : carteA.getValeur().getValeurNumerique();
            int valB = (carteB.getCouleur() == Couleur.JOKER) ? 0 : carteB.getValeur().getValeurNumerique();

            if (valA != valB) {
                return Integer.compare(valA, valB); // Tri croissant par valeur
            }
            return Integer.compare(carteA.getCouleur().getForce(), carteB.getCouleur().getForce()); // Tri croissant par force de couleur
        };

        // Tri (ici, nous classons simplement les deux cartes)
        Carte carteFaible = comparateurCarte.compare(c1, c2) <= 0 ? c1 : c2;
        Carte carteForte = comparateurCarte.compare(c1, c2) > 0 ? c1 : c2;

        // Stratégie: Mettre la carte Forte visible pour avoir plus de chance de jouer en premier
        Carte faceVisible = carteForte;
        Carte faceCachee = carteFaible;

        // Création de l'offre (et suppression des cartes de la main du joueur)
        this.creerMonOffre(faceVisible, faceCachee);
        
        // CORRECTION MAJEURE : On retire les cartes de la "main" (deck temporaire) du joueur
        // car elles sont maintenant dans l'Offre.
        cartesEnMain.clear();
    }
    

    // getter pour savoir si c'est un joueur virtuel
    public boolean isVirtuel() {
        return virtuel;
    }
}

