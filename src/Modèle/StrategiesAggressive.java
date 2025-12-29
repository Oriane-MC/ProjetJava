package Modèle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StrategiesAggressive implements Strategie {

    @Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();

        if (c1 == null || c2 == null) return null;

        Carte faible = (c1.getValeur() <= c2.getValeur()) ? c1 : c2;
        Carte forte = (c1 == faible) ? c2 : c1;

        // Logique agressive : Visible = faible | Cachée = forte (on garde le meilleur pour soi)
        return new Offre(forte, faible, joueur);
    }

    @Override
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
        if (offres == null || offres.isEmpty()) return null;

        // On cherche l'offre avec la carte visible la plus FORTE
        Offre cible = offres.stream()
                .filter(o -> o.getCarteVisible() != null)
                .max(Comparator.comparingInt(o -> o.getCarteVisible().getValeur()))
                .orElse(offres.get(new Random().nextInt(offres.size())));

        return cible.getCreateur();
    }

    @Override
    public String toString() {
        return "Stratégie Agressive";
    }
}