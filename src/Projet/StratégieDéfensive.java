package Projet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StratégieDéfensive implements Strategie {

    /**
     * Création d’une offre défensive :
     * Visible  = carte la plus forte (pour que personne ne la prenne) et pr esperer commencer 
     * Cachée   = carte la plus faible
     */
    @Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {

        // On pioche 2 cartes
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) {
            System.out.println("Pioche insuffisante pour créer une offre.");
            return null;
        }

        // On classe les cartes
        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // Logique défensive :
        // Visible = forte | Cachée = faible
        Offre offre = new Offre(faible, forte, joueur);  
        joueur.setOffre(offre);

        System.out.println(joueur.getNom() + " (défensif) crée une offre, sa carte visible est : " + forte);

        return offre;
    }




    /**
     * Prendre une offre adverse – version défensive :
     * Prend la CARTE VISIBLE
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

        // Chercher la carte visible la plus faible
        Offre cible = valides.stream()
                .min(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(null);

        // Sinon random
        if (cible == null) {
            cible = valides.get(new Random().nextInt(valides.size()));
        }

        // Prendre la carte visible
        Carte prise = cible.carteVisiblePrise();

        if (prise != null) {

            Joueur createur = cible.getCreateur(); 

            joueur.getDeckPossede().ajouterCarte(prise);

            System.out.println(joueur.getNom() + " (défensif) prend la carte visible : " + prise);
            System.out.println("   → cette carte appartenait à : " + createur.getNom());
        }

        return prise;
     }


    @Override
    public String getNom() {
        return "Stratégie Défensive";
    }
}
