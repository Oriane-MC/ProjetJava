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
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {

        // 1️⃣ Piocher deux cartes pour créer l'offre
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

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

        System.out.println(joueur.getNom() + " (agressif) crée son offre, sa carte visible est : " + faible );

        return offre;
    }

    /**
     * Prendre une carte d'une offre adverse
     */
    @Override
    public Carte prendreOffreAdversaire(List<Offre> offres, Joueur joueur) {
    	if (offres == null || offres.isEmpty()) return null;

        // Filtrer les offres encore disponibles
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null && o.carteVisiblePrise() == null) {
                valides.add(o);
            }
        }

        if (valides.isEmpty()) return null;

        // Prendre l'offre dont la carte visible est la plus forte
        Offre cible = valides.stream()
                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(valides.get(new Random().nextInt(valides.size())));

        // Prendre la carte visible
        Carte prise = cible.carteVisiblePrise();

        if (prise != null) {
            joueur.getDeckPossede().ajouterCarte(prise);

            // Affichage 
            Joueur createur = cible.getCreateur();
            System.out.println(joueur.getNom() + " (agressif) prend la carte visible : " + prise);
            System.out.println("   → cette carte appartenait à : " + createur.getNom());
        }

        return prise;
      }

    /**
     * Nom de la stratégie pour affichage
     */
    @Override
    public String getNom() {
        return "Stratégie Agressive";
    }
}
