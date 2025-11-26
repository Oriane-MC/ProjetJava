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
    public Offre choisirMonOffre(Joueur joueur, Deck pioche, List<Offre> offresAdversaires) {

        // On pioche 2 cartes
        Carte c1 = pioche.piocherCarte();
        Carte c2 = pioche.piocherCarte();

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

        System.out.println(joueur.getNom() + " (défensif) crée une offre : visible = forte, cachée = faible");

        return offre;
    }




    /**
     * Prendre une offre adverse – version défensive :
     * Prend la CARTE VISIBLE
     */
    @Override
    public void prendreOffreAdversaire(Offre offreChoisie, Joueur joueur) {
        if (offreChoisie == null) return;

        Carte prise = offreChoisie.carteVisiblePrise();
        if (prise != null) {
            joueur.getDeckPossede().ajouterCarte(prise);
            System.out.println(joueur.getNom() + " (défensif) prend la carte visible la moins dangereuse.");
        }
    }




    /**
     * Choisir quelle offre PRENDRE :
     * → Priorité : la carte visible LA PLUS FAIBLE
     * → Sinon : aléatoire
     */
    public Offre choisirOffreDefensive(List<Offre> offres) {

        // Filtrage : offres valides et non déjà prises
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null && o.carteVisiblePrise() == null) {
                valides.add(o);
            }
        }

        if (valides.isEmpty()) return null;

        // Cherche la carte visible LA PLUS FAIBLE
        Offre plusFaible = valides.stream()
                .min(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(null);

        if (plusFaible != null) return plusFaible;

        // Sinon → random
        return valides.get(new Random().nextInt(valides.size()));
    }



    @Override
    public String getNom() {
        return "Stratégie Défensive";
    }
}
