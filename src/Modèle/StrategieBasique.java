package Modèle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieBasique implements Strategie {
    
    /**
     * Visible = la plus faible | Cachée = la plus forte
     */
    @Override
    public Offre choisirMonOffre(Joueur joueur, PaquetCarte pioche, List<Offre> offresAdversaires) {
        Carte c1 = pioche.piocher();
        Carte c2 = pioche.piocher();
        
        if (c1 == null || c2 == null) return null;

        Carte faible, forte;
        if (c1.getValeur() <= c2.getValeur()) {
            faible = c1;
            forte = c2;
        } else {
            faible = c2;
            forte = c1;
        }

        // On retourne l'offre créée
        return new Offre(forte, faible, joueur);
    }

    /**v
     * Choisit un joueur au hasard parmi les offres disponibles.
     */
    @Override
    public Joueur deciderOffreAdversaire(List<Offre> offres, Joueur joueur) {
        if (offres == null || offres.isEmpty()) return null;

        // On filtre pour être sûr d'avoir des offres valides
        List<Offre> valides = new ArrayList<>();
        for (Offre o : offres) {
            if (o != null && o.getCreateur() != null) {
                valides.add(o);
            }
        }

        if (valides.isEmpty()) return null;

        // Choix purement aléatoire de l'adversaire
        int index = new Random().nextInt(valides.size());
        return valides.get(index).getCreateur();
    }

    @Override
    public String toString() {
        return "Stratégie Basique";
    }
}