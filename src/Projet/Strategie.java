package Projet;
import java.util.List;

public interface Strategie  {
	// Décider quelle offre faire, quel carte je mets cacher et quel carte je mets visible 
    public Offre choisirMonOffre(Joueur joueur, Deck pioche, List<Offre> offresAdversaires);
    
    // Décider quelle offre je vais prendre chez mes adversaire 
    public void prendreOffreAdversaire(Offre offre, Joueur joueur);

    /**
     * Retourne le nom de la stratégie pour l'affichage.
     * @return Le nom de la stratégie.
     */
    public String getNom();
}
