package projetLO02;

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
     * Décider de l'offre à faire à l'adversaire
     * @return Offre choisie pour l'adversaire
     */
    public Offre deciderOffreAdversaire() {
        // Exemple simple : retourne une offre aléatoire
        Offre[] toutesOffres = Offre.values();
        int index = (int) (Math.random() * toutesOffres.length);
        return toutesOffres[index];
    }

    /**
     * Choisir mon offre
     * @return Offre que le joueur virtuel choisit
     */
    public Offre choisirMonOffre() {
        // Exemple simple : retourne une offre aléatoire
        Offre[] toutesOffres = Offre.values();
        int index = (int) (Math.random() * toutesOffres.length);
        return toutesOffres[index];
    }

    // getter pour savoir si c'est un joueur virtuel
    public boolean isVirtuel() {
        return virtuel;
    }
}
