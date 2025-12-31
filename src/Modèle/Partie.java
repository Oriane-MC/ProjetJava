package Modèle;

import java.io.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.JOptionPane; // Nécessaire pour la boîte de dialogue

public class Partie implements Serializable {
    public enum EtatPartie { INITIALISATION, OFFRES, RAMASSAGE, FIN }
    
    private ArrayList<Joueur> listJoueur = new ArrayList<>();
    private List<Carte> cartesEnAttente = new ArrayList<>(); 
    private PaquetCarte listCarte;
    private EtatPartie etat = EtatPartie.INITIALISATION;
    private boolean extension = false;
    private int varianteChoisie = 3; 
    private String dernierMessage = "Bienvenue ! Ajoutez les joueurs.";
    private Trophee trophees;
    private int numeroTour = 1;
    private int indexJoueurOffre = 0;
    
    private Joueur joueurActuel; 
    private Variante objetVariante; 
    private ArrayList<Joueur> ontJoueCeTour = new ArrayList<>();
    private Map<Joueur, Integer> scoresFinaux;

    private transient List<Observateur> observateurs = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Partie() {}
     
    

    // --- LOGIQUE DE SAUVEGARDE ET CHARGEMENT ---
    public static boolean sauvegarder(Partie p, String fichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(p);
            return true;  // ✅ Succès
        } catch (IOException e) {
            e.printStackTrace();
            return false; // ✅ Échec
        }
    }

    public static Partie charger(String fichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (Partie) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    // --- Gestion des Observateurs ---
 
    public void enregistrerObservateur(Observateur o) {
        if (this.observateurs == null) {
            this.observateurs = new ArrayList<>();
        }
        this.observateurs.add(o);
    }
    
 // Dans Partie.java

    public void relancerLeJeuApresChargement() {
        // 1️⃣ Recréer les listes transient (pas sauvegardées)
        if (observateurs == null) {
            observateurs = new ArrayList<>();
        }
        if (ontJoueCeTour == null) {
            ontJoueCeTour = new ArrayList<>();
        }
        if (cartesEnAttente == null) {
            cartesEnAttente = new ArrayList<>();
        }

        // 2️⃣ Restaurer joueurActuel si nécessaire
        if (joueurActuel == null && !listJoueur.isEmpty() && etat != EtatPartie.INITIALISATION) {
            joueurActuel = listJoueur.get(0);
        }

        // 3️⃣ Réinitialiser les stratégies IA
        for (Joueur j : listJoueur) {
            if (j instanceof Virtuel) {
                ((Virtuel) j).reinitialiserStrategie();
            }
        }

        // 4️⃣ Message de confirmation
        dernierMessage = "Partie chargée - Tour n°" + numeroTour;
    }

    
    public void notifier() {
        if (this.observateurs != null) {
            for (Observateur o : observateurs) o.update(this);
        }
    }

    // --- Initialisation ---
    public void ajouterJoueur(String nom, String type, int strategie) {
        if (listJoueur.size() >= 4) {
            dernierMessage = "Maximum 4 joueurs !";
        } else {
            Joueur j = type.equalsIgnoreCase("Virtuel") ? 
                       new Virtuel(nom, strategie, this) : new Joueur(nom, "Humain", this);
            listJoueur.add(j);
            dernierMessage = nom + " ajouté.";
        }
        notifier();
    }

    public void configurerOptions(boolean ext, int var) {
        this.extension = ext;
        this.varianteChoisie = var;
        if (var == 1) {
            this.objetVariante = new VariantePremierJoueurAleatoire();
            this.objetVariante.estUtilise();
        } else if (var == 2) {
            this.objetVariante = new VarianteSansTrophees();
            this.objetVariante.estUtilise();
        } else {
            this.objetVariante = null;
        }
    }

    public PaquetCarte getPioche() { return listCarte; }

    public void lancerPartie() {
        if (listJoueur.size() < 3) return;
        initialiserJeu();
        mélanger();
        demarrerNouveauTour();
    }

    // --- Phase 1 : Offres ---
    private void demarrerNouveauTour() {
        this.ontJoueCeTour.clear();
        this.indexJoueurOffre = 0; 
        this.répartir(); 
        this.etat = EtatPartie.OFFRES;
        proposerOffreAuJoueurSuivant();
    }

    private void proposerOffreAuJoueurSuivant() {
        if (indexJoueurOffre < listJoueur.size()) {
            this.joueurActuel = listJoueur.get(indexJoueurOffre);
            this.cartesEnAttente.clear();

            if (listCarte.getNombreCartes() >= 2) {
                this.cartesEnAttente.add(listCarte.piocher());
                this.cartesEnAttente.add(listCarte.piocher());
                this.dernierMessage = joueurActuel.getNom() + ", choisis ta carte VISIBLE.";
                notifier();
                
                if (joueurActuel instanceof Virtuel) {
                    ((Virtuel) joueurActuel).choisirOffreGraphique(cartesEnAttente.get(0), cartesEnAttente.get(1));
                }
            } else {
                finirTourEtSuivant(); 
            }
        } else {
            passerAuRamassage();
        }
    }

    public void validerChoixOffre(Carte visible) {
        if (cartesEnAttente == null || cartesEnAttente.size() < 2) return; 
        try {
            Carte cachee = (cartesEnAttente.get(0).equals(visible)) ? cartesEnAttente.get(1) : cartesEnAttente.get(0);
            joueurActuel.setOffre(new Offre(cachee, visible, joueurActuel));
            cartesEnAttente.clear();
            indexJoueurOffre++;
            proposerOffreAuJoueurSuivant();
        } catch (Exception e) {
            System.err.println("Erreur validation offre: " + e.getMessage());
        }
    }

    // --- Phase 2 : Ramassage ---
    private void passerAuRamassage() {
        this.etat = EtatPartie.RAMASSAGE;
        this.joueurActuel = determinerPremierJoueur();
        this.dernierMessage = "Phase de ramassage ! " + joueurActuel.getNom() + " commence.";
        notifier();
        verifierSiRobotDoitJouer();
    }

    public void ramasserAction(Joueur cible, boolean estVisible) {
        Joueur prochain = joueurActuel.prendreOffreEtJoueurSuivant(this, ontJoueCeTour, cible, estVisible);
        this.joueurActuel = prochain;

        if (ontJoueCeTour.size() == listJoueur.size()) {
            this.dernierMessage = "Fin du tour " + numeroTour;
            notifier();
            Timer timer = new Timer(1500, e -> finirTourEtSuivant());
            timer.setRepeats(false);
            timer.start();
        } else {
            this.dernierMessage = "Au tour de " + joueurActuel.getNom();
            notifier();
            verifierSiRobotDoitJouer();
        }
    }

    // --- Phase 3 : Transition et Sauvegarde ---
    private void finirTourEtSuivant() {
        // 1. Ramassage des cartes restantes
        for (Joueur j : listJoueur) {
            j.ajouterDerniereCarteOffreAuJest(); 
            j.setOffre(null); 
        }

        
     // 2. PROPOSITION DE SAUVEGARDE
        // On demande à l'utilisateur s'il veut sauvegarder avant de continuer
        //int choix = JOptionPane.showConfirmDialog(null, 
            //"Le tour " + numeroTour + " est terminé.\nVoulez-vous sauvegarder la partie maintenant ?", 
            //"Sauvegarde Automatique", 
            //JOptionPane.YES_NO_OPTION);
        
        //if (choix == JOptionPane.YES_OPTION) {
            //sauvegarder(this, "partie_jest.ser");
            //JOptionPane.showMessageDialog(null, "Partie sauvegardée sous 'partie_jest.ser' !");
        //}
        
        
        // 2. Suite du jeu (pas de pop-up ici!)
        if (estJouable()) {
            numeroTour++;
            demarrerNouveauTour();
        } else {
            terminerLaPartie();
        }
    }
    

    private void terminerLaPartie() {
        this.etat = EtatPartie.FIN;
        try {
            CompteurPoint visiteur = new CompteurPoint();
            this.scoresFinaux = visiteur.visit(this); 
            this.dernierMessage = "Jeu terminé !";
        } catch (Exception e) {
            this.dernierMessage = "Erreur calcul score.";
        }
        notifier();
    }

    // --- Méthodes Utilitaires & Calculs ---
    public Map<Joueur, Integer> calculerScoreSansTrophees() {
        Map<Joueur, Integer> scoresTemp = new HashMap<>();
        CompteurPoint visiteur = new CompteurPoint();
        for (Joueur j : listJoueur) {
            try {
                scoresTemp.put(j, j.accept(visiteur));
            } catch (Exception e) {
                scoresTemp.put(j, 0);
            }
        }
        this.scoresFinaux = scoresTemp; 
        return scoresTemp;
    }

    public Joueur joueurGagnant() {
        Map<Joueur, Integer> scores = calculerScoreSansTrophees();
        Joueur gagnant = null;
        int max = -1000;
        for (Map.Entry<Joueur, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                gagnant = entry.getKey();
            }
        }
        return gagnant;
    }

    public Map<Joueur, Integer> getScore() { return this.scoresFinaux; }

    public Joueur determinerPremierJoueur() {
        if (varianteChoisie == 1 && objetVariante != null) return objetVariante.appliquerVariante(this);
        Joueur vainqueur = null;
        for (Joueur j : listJoueur) {
            if (ontJoueCeTour.contains(j)) continue;
            if (vainqueur == null || comparerForce(j.getOffre().getCarteVisible(), vainqueur.getOffre().getCarteVisible()) > 0) {
                vainqueur = j;
            }
        }
        return (vainqueur != null) ? vainqueur : listJoueur.get(0);
    }

    private int comparerForce(Carte c1, Carte c2) {
        if (c1.getValeur() != c2.getValeur()) return Integer.compare(c1.getValeur(), c2.getValeur());
        return Integer.compare(getForceCouleur(c1), getForceCouleur(c2));
    }

    public int getForceCouleur(Carte c) {
        return switch (c.getCouleur().toUpperCase()) {
            case "PIQUE" -> 4; case "TREFLE" -> 3; case "CARREAU" -> 2; case "COEUR" -> 1; default -> 0;
        };
    }

    private void verifierSiRobotDoitJouer() {
        if (joueurActuel instanceof Virtuel && etat == EtatPartie.RAMASSAGE) {
            Timer timer = new Timer(1500, e -> ((Virtuel) joueurActuel).jouerAutomatiquement(this));
            timer.setRepeats(false);
            timer.start();
        }
    }

    public boolean estJouable() {
        return listCarte != null && listCarte.getNombreCartes() >= listJoueur.size();
    }

    private void initialiserJeu() {
        LinkedList<Carte> listC = new LinkedList<>();
        for (TypeCarte type : TypeCarte.values()) {
            listC.add(new Carte(type.getValeur(), type.getCouleur(), type.getCondition()));
        }
        this.listCarte = new PaquetCarte(listC);
    }

    public void mélanger() { if (listCarte != null) listCarte.melanger(); }

    public void répartir() {
        if (varianteChoisie != 2 && trophees == null) {
            this.trophees = new Trophee(listCarte.piocher(), listCarte.piocher());
        }
    }

    public void setTrophees(Trophee t) { this.trophees = t; }

    // Getters
    public EtatPartie getEtat() { return etat; }
    public ArrayList<Joueur> getJoueur() { return listJoueur; }
    public List<Carte> getCartesEnAttente() { return cartesEnAttente; }
    public Joueur getJoueurActuel() { return joueurActuel; }
    public String getDernierMessage() { return dernierMessage; }
    public int getNumeroTour() { return numeroTour; }
    public Map<Joueur, Integer> getScoresFinaux() { return scoresFinaux; }
    public Trophee getTrophees() { return trophees; }
    public int getVariante() { return varianteChoisie; }
}