package Modèle;

import java.io.Serializable;
import java.util.*;

public class Partie implements Serializable {
    public enum EtatPartie { INITIALISATION, OFFRES, RAMASSAGE, FIN }

    private ArrayList<Joueur> listJoueur = new ArrayList<>();
    private PaquetCarte listCarte;
    private EtatPartie etat = EtatPartie.INITIALISATION;
    private boolean extension = false;
    private int varianteChoisie = 3;
    private String dernierMessage = "Bienvenue ! Configurez la partie.";
    
    private transient List<Observateur> observateurs = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Partie() {
        // Initialisation vide, les options seront fixées au lancement
    }

    // --- GESTION DES OBSERVATEURS ---
    public void enregistrerObservateur(Observateur o) {
        if (this.observateurs == null) this.observateurs = new ArrayList<>();
        this.observateurs.add(o);
    }

    public void notifier() {
        if (this.observateurs != null) {
            for (Observateur o : observateurs) o.update(this);
        }
    }

    // --- CONFIGURATION ET JOUEURS ---
    public void ajouterJoueur(String nom, String type, int strategie) {
        if (listJoueur.size() >= 4) {
            dernierMessage = "Maximum 4 joueurs !";
        } else {
            Joueur j = type.equalsIgnoreCase("Virtuel") ? 
                       new Virtuel(nom, strategie, this) : new Joueur(nom, "Humain", this);
            listJoueur.add(j);
            dernierMessage = nom + " ajouté à la partie.";
        }
        notifier();
    }

    public void configurerOptions(boolean ext, int var) {
        this.extension = ext;
        this.varianteChoisie = var;
    }

    public void lancerPartie() {
        if (listJoueur.size() < 3) {
            dernierMessage = "Il faut au moins 3 joueurs !";
        } else {
            this.etat = EtatPartie.OFFRES;
            this.dernierMessage = "La partie commence ! Phase d'offres.";
            initialiserJeu();
        }
        notifier();
    }

    private void initialiserJeu() {
        // Logique de création du paquet selon extension
        // listCarte = new PaquetCarte(extension);
        // listCarte.melanger();
        // distribuerCartes();
    }

    // --- GETTERS ---
    public EtatPartie getEtat() { return etat; }
    public ArrayList<Joueur> getJoueur() { return listJoueur; }
    public String getDernierMessage() { return dernierMessage; }
}