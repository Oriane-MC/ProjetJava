package Modèle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StratégieDéfensive implements Strategie {

    @Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) return null;

        Carte faible = (c1.getValeur() <= c2.getValeur()) ? c1 : c2;
        Carte forte = (c1 == faible) ? c2 : c1;

        // Logique défensive : Visible = forte (pour faire peur) | Cachée = faible
        return new Offre(faible, forte, joueur);
    }

    @Override
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
        if (offres == null || offres.isEmpty()) return null;

        // On cherche l'offre avec la carte visible la plus FAIBLE
        Offre cible = offres.stream()
                .filter(o -> o.getCarteVisible() != null)
                .min(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(offres.get(new Random().nextInt(offres.size())));

        return cible.getCreateur();
    }

    @Override
    public String toString() {
        return "Stratégie Défensive";
    }
}