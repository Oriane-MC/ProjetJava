package Modèle;

import java.io.Serializable;
import java.util.*;

/**
 * Implémente le calcul des scores selon les règles du jeu JEST.
 * Cette classe utilise le pattern Visitor pour parcourir les joueurs,
 * les cartes et les parties.
 */
public class CompteurPoint implements Visitor, Serializable {
    
	/** Identifiant de version pour la sérialisation */
    private static final long serialVersionUID = 1L;

    /**
     * Calcule le score total d'un joueur à partir de son deck.
     *
     * @param j joueur à évaluer
     * @return score final du joueur (toujours positif ou nul)
     * @throws GameException en cas d'erreur lors du calcul
     */
    public int visit(Joueur j) throws GameException {
        int score = 0;
        
        // On récupère le Deck du joueur
        Deck d = j.getDeckPossede();
            
        for (Carte c : d.getCartes()) { 
            // Règle de l'As : 5 points s'il est seul de sa couleur
            if (c.getValeur() == 1) {
                score += this.scoreAs(c, d);
            }
            else {
                // Utilise le visit(Carte c) ci-dessous pour Pique, Trèfle, Carreau
                score += c.accept(this);
            }
        }
        
        // Les cœurs sont traités à part (dépendent du Joker)
        score += this.scoreCoeur(d);
        
        // Bonus des paires Pique/Trèfle de même valeur
        score += this.scorePairePiqueTrefle(d);
                    
        // Le score final d'un joueur ne peut pas être négatif au JEST
        return Math.max(score, 0);
    }
    
    /**
     * Calcule le score associé à une carte selon sa couleur.
     *
     * @param c carte à évaluer
     * @return score de la carte
     * @throws GameException si la couleur est inconnue
     */
    public int visit(Carte c) throws GameException {
        String couleur = c.getCouleur().toLowerCase();
        
        // Piques et Trèfles = Valeur positive
        if (couleur.equals("pique") || couleur.equals("trèfle")) {
            return c.getValeur();           
        }
        // Carreaux = Valeur négative
        else if (couleur.equals("carreau")) {
            return (-c.getValeur());
        }
        // Cœurs et Joker = gérés par scoreCoeur()
        else if (couleur.equals("coeur") || couleur.equals("joker")) {
            return 0;
        }
        else {
            throw new GameException("Couleur inconnue : " + couleur);
        }
    }
        
    /**
     * Calcule les scores finaux de tous les joueurs d'une partie.
     * Gère l'attribution des trophées avant le calcul des scores.
     *
     * @param p partie à évaluer
     * @return association entre chaque joueur et son score final
     * @throws GameException en cas d'erreur lors du calcul
     */
    public Map<Joueur, Integer> visit(Partie p) throws GameException {
        
        // 1. Attribution des trophées (si ce n'est pas la variante "Sans Trophées")
        if (p.getVariante() != 2) {
            List<Joueur> gagnants = p.getTrophees().determinerGagnant(p);

            if (gagnants.size() >= 2) {
                Joueur j0 = gagnants.get(0);
                Joueur j1 = gagnants.get(1);

                if (j0.getNom().equals("tmp")) {
                    j1.ajouterCarteDeck(p.getTrophees().getTrophee(1));
                    j1.ajouterCarteDeck(p.getTrophees().getTrophee(2));
                } else if (j1.getNom().equals("tmp")) {
                    j0.ajouterCarteDeck(p.getTrophees().getTrophee(1));
                    j0.ajouterCarteDeck(p.getTrophees().getTrophee(2));
                } else {
                    if (!j0.getNom().equals("personne")) j0.ajouterCarteDeck(p.getTrophees().getTrophee(1));
                    if (!j1.getNom().equals("personne")) j1.ajouterCarteDeck(p.getTrophees().getTrophee(2));
                }
            }
        }
            
        // 2. Calculer le score final pour chaque joueur
        Map<Joueur, Integer> scores = new HashMap<>();
        for (Joueur j : p.getJoueur()) {
            scores.put(j, j.accept(this));
        }
        
        return scores;
    }
    
    /**
     * Calcule le score des cartes cœur d'un deck,
     * en tenant compte de la présence éventuelle du Joker.
     *
     * @param deck deck du joueur
     * @return score lié aux cœurs
     */
    public int scoreCoeur(Deck deck) {
        List<Integer> listCoeur = new ArrayList<>();
        boolean joker = false;
        int score = 0;
        
        for (Carte c : deck.getCartes()) {
            if (c.getCouleur().equalsIgnoreCase("coeur")) {
                listCoeur.add(c.getValeur());
            }
            if (c.getCouleur().equalsIgnoreCase("joker")) {
                joker = true;
            }
        }
        
        // Règle Joker
        if (joker) {
            if (listCoeur.isEmpty()) {
                score += 4; // Bonus Joker sans coeur
            } else if (listCoeur.size() == 4) {
                // Si les 4 coeurs : ils redeviennent positifs
                for (int val : listCoeur) score += val;
            } else {
                // Si entre 1 et 3 coeurs : ils comptent en négatif
                for (int val : listCoeur) score -= val;
            }
        }
        return score;   
    }
    
    /**
     * Calcule le bonus associé aux paires pique/trèfle de même valeur.
     *
     * @param deck deck du joueur
     * @return bonus de score
     */
    public int scorePairePiqueTrefle(Deck deck) {
        int score = 0;
        List<Integer> listPique = new ArrayList<>();
        List<Integer> listTrefle = new ArrayList<>();
        
        for (Carte c : deck.getCartes()) {
            if (c.getCouleur().equalsIgnoreCase("pique")) listPique.add(c.getValeur());
            if (c.getCouleur().equalsIgnoreCase("trèfle")) listTrefle.add(c.getValeur());
        }
        
        for (int p : listPique) {
            for (int t : listTrefle) {
                if (p == t) {
                    score += 2; // +2 par paire de même valeur
                }
            }
        }
        return score;
    }   
    
    /**
     * Calcule le score spécifique d'un As lorsqu'il est seul de sa couleur.
     *
     * @param c carte As à évaluer
     * @param d deck du joueur
     * @return score de l'As
     * @throws GameException si la couleur de l'As est invalide
     */
    public int scoreAs(Carte c, Deck d) throws GameException {
        // Vérifie si l'As est la seule carte de cette couleur dans le deck
        for (Carte verif : d.getCartes()) {
            if (c.getCouleur().equalsIgnoreCase(verif.getCouleur())) {
                if (verif.getValeur() != 1) {
                    return 1; // Il y a une autre carte de la même couleur
                }
            }
        }
        
        // Si on arrive ici, l'As est seul
        String couleur = c.getCouleur().toLowerCase();
        if (couleur.equals("pique") || couleur.equals("trèfle")) {
            return 5;
        } else if (couleur.equals("carreau") || couleur.equals("coeur")) {
            return -5;
        } else {
            throw new GameException("Couleur d'As invalide");
        }
    }
}