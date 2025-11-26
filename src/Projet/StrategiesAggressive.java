package Projet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Stratégie Agressive :
 * - Lors de la création d'une offre :
 *      Visible = faible (attire les adversaires)
 *      Cachée  = forte (je garde la meilleure)
 * - Lors de la prise d'une offre adverse :
 *      Toujours prendre la carte visible la plus forte
 */
public class StrategiesAggressive implements Strategie {

    /**
     * Créer l'offre agressive pour le joueur
     */
    @Override
    public Offre choisirMonOffre(Joueur joueur, Deck pioche, List<Offre> offresAdversaires) {

        // 1️⃣ Piocher deux cartes pour créer l'offre
        Carte c1 = pioche.piocherCarte();
        Carte c2 = pioche.piocherCarte();

        if (c1 == null || c2 == null) {
            System.out.println("Pioche insuffisante pour créer une offre.");
            return null;
        }

        // 2️⃣ Identifier la carte faible et la carte forte
        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // 3️⃣ Logique agressive : visible = faible, cachée = forte
        Offre offre = new Offre(forte, faible, joueur);
        joueur.setOffre(offre);

        System.out.println(joueur.getNom() + " (agressif) crée son offre : visible = faible, cachée = forte");

        return offre;
    }

    /**
     * Prendre une carte d'une offre adverse
     */
    @Override
    public void prendreOffreAdversaire(Offre offreChoisie, Joueur joueur) {
        if (offreChoisie == null) return;

        // 1️⃣ Tenter de prendre la carte visible (méthode déjà présente dans Offre)
        Carte prise = offreChoisie.carteVisiblePrise(); 
        if (prise != null) {
            joueur.getDeckPossede().ajouterCarte(prise);
            System.out.println(joueur.getNom() + " (agressif) prend la carte visible de l'offre adverse.");
        }
    }

    /**
     * Choisir l'offre la plus intéressante parmi toutes les offres adverses
     * Ici on choisit la carte visible la plus forte disponible
     */
    public Offre choisirOffreAgressive(List<Offre> offres) {
        if (offres == null || offres.isEmpty()) return null;

        // 1️⃣ Filtrer les offres encore disponibles : 
        // On teste si la carte visible n'a pas encore été prise
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null) {
                try {
                    // Si carteVisiblePrise() retourne une carte, elle est encore disponible
                    // On ne l'enlève pas réellement ici car on l'appelle juste pour tester
                    Carte test = o.getCarteVisible();
                    if (test != null) {
                        valides.add(o);
                    }
                } catch (Exception e) {
                    // Si carte déjà prise, on ignore
                }
            }
        }

        if (valides.isEmpty()) return null;

        // 2️⃣ Retourner l'offre dont la carte visible est la plus forte
        return valides.stream()
                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(valides.get(new Random().nextInt(valides.size())));
    }

    /**
     * Nom de la stratégie pour affichage
     */
    @Override
    public String getNom() {
        return "Stratégie Agressive";
    }
}
