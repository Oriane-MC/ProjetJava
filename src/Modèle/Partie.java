package Modèle;

import java.io.*;
import java.util.*;
import javax.swing.Timer;


import javax.swing.JOptionPane; // Nécessaire pour la boîte de dialogue

/**
 * Représente une partie du jeu.
 * Gère l'état du jeu, les joueurs, les cartes, les trophées et les différentes phases (offres, ramassage, fin).
 * Permet également la sauvegarde et le chargement de l'état d'une partie.
 */
public class Partie implements Serializable {
	
	/**
	 * Représente les différentes phases d'une partie.
	 */
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

    
    /**
     * Constructeur par défaut.
     * Initialise une partie vide, sans joueurs ni cartes.
     */
    public Partie() {}
    
    
    // --- Gestion de la sauvegarde et récupération  ---

    /**
     * Sauvegarde l'état d'une partie dans un fichier.
     *
     * @param p partie à sauvegarder
     * @param fichier chemin du fichier de sauvegarde
     * @return true si la sauvegarde a réussi, false sinon
     */
    public static boolean sauvegarder(Partie p, String fichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(p);
            return true; 
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
    }

    /**
     * Charge une partie depuis un fichier.
     *
     * @param fichier chemin du fichier de sauvegarde
     * @return partie chargée, ou null si erreur
     */
    public static Partie charger(String fichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (Partie) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Réinitialise les éléments transient et restaure l'état nécessaire après le chargement d'une partie.
     */
    public void relancerLeJeuApresChargement() {
        // 1️ Recréer les listes transient (pas sauvegardées)
        if (observateurs == null) {
            observateurs = new ArrayList<>();
        }
        if (ontJoueCeTour == null) {
            ontJoueCeTour = new ArrayList<>();
        }
        if (cartesEnAttente == null) {
            cartesEnAttente = new ArrayList<>();
        }

        // 2️ Restaurer joueurActuel si nécessaire
        if (joueurActuel == null && !listJoueur.isEmpty() && etat != EtatPartie.INITIALISATION) {
            joueurActuel = listJoueur.get(0);
        }

        // 3️ Réinitialiser les stratégies IA
        for (Joueur j : listJoueur) {
            if (j instanceof Virtuel) {
                ((Virtuel) j).reinitialiserStrategie();
            }
        }

        // 4️ Message de confirmation
        dernierMessage = "Partie chargée - Tour n°" + numeroTour;
    }

    // --- Gestion des Observateurs ---
 
    /**
     * Ajoute un observateur qui sera notifié à chaque changement de la partie.
     *
     * @param o observateur à enregistrer
     */
    public void enregistrerObservateur(Observateur o) {
        if (this.observateurs == null) {
            this.observateurs = new ArrayList<>();
        }
        this.observateurs.add(o);
    }
    
    /**
     * Notifie tous les observateurs d'un changement dans la partie.
     */
    public void notifier() {
        if (this.observateurs != null) {
            for (Observateur o : observateurs) o.update(this);
        }
    }
    
    // --- Initialisation ---
    
    /**
     * Ajoute un joueur à la partie.
     * Limite de 4 joueurs maximum.
     *
     * @param nom nom du joueur
     * @param type "Humain" ou "Virtuel"
     * @param strategie stratégie si le joueur est virtuel
     */
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

    /**
     * Configure les options de la partie : extension et variante.
     *
     * @param ext true si les cartes extension doivent être ajoutées
     * @param var numéro de la variante choisie
     */
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

    /**
     * Retourne la pioche actuelle de la partie.
     *
     * @return paquet de cartes
     */
    public PaquetCarte getPioche() { return listCarte; }

    /**
     * Initialise et lance la partie : mélange les cartes, distribue les trophées et démarre le premier tour.
     */
    public void lancerPartie() {
        if (this.estJouable()) return;
        initialiserJeu();
        mélanger();
        demarrerNouveauTour();
    }

    // --- Phase 1 : Offres ---
    
    /**
     * Démarre un nouveau tour de la partie.
     * 
     * - Réinitialise les joueurs ayant joué ce tour.
     * - Réinitialise l'index pour la phase d'offres.
     * - Répartit les cartes.
     * - Passe à l'état OFFRES et propose l'offre au joueur suivant.
     */
    private void demarrerNouveauTour() {
        this.ontJoueCeTour.clear();
        this.indexJoueurOffre = 0; 
        this.répartir(); 
        this.etat = EtatPartie.OFFRES;
        proposerOffreAuJoueurSuivant();
    }

    /**
     * Propose l'offre de cartes au joueur suivant dans la phase d'offres.
     * 
     * - Si le joueur est virtuel, déclenche son choix automatique.
     * - Si la pioche n'a plus assez de cartes, termine le tour et passe à la phase suivante.
     */
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

    /**
     * Valide le choix de la carte visible pour le joueur actuel et met à jour l'offre.
     *
     * @param c carte choisie
     */
    public void validerChoixOffre(Carte c) {
        if (cartesEnAttente == null || cartesEnAttente.size() < 2) return; 
        try {
            Carte cachee = (cartesEnAttente.get(0).equals(c)) ? cartesEnAttente.get(1) : cartesEnAttente.get(0);
            joueurActuel.setOffre(new Offre(cachee, c, joueurActuel));
            cartesEnAttente.clear();
            indexJoueurOffre++;
            proposerOffreAuJoueurSuivant();
        } catch (Exception e) {
            System.err.println("Erreur validation offre: " + e.getMessage());
        }
    }

    // --- Phase 2 : Ramassage ---
    
    /**
     * Passe la partie à la phase de ramassage.
     * 
     * - Détermine le joueur qui commence la phase.
     * - Met à jour le message d'état.
     * - Notifie les observateurs.
     * - Vérifie si le joueur actif est un robot et doit jouer automatiquement.
     */
    private void passerAuRamassage() {
        this.etat = EtatPartie.RAMASSAGE;
        this.joueurActuel = determinerPremierJoueur();
        this.dernierMessage = "Phase de ramassage ! " + joueurActuel.getNom() + " commence.";
        notifier();
        verifierSiRobotDoitJouer();
    }

    /**
     * Permet au joueur actuel de ramasser une carte d'un autre joueur.
     *
     * @param cible joueur dont on prend la carte
     * @param estVisible true si c'est la carte visible, false si cachée
     */
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

    // --- Phase 3 : Transition ---
    
    /**
     * Termine le tour actuel et passe au tour suivant.
     * 
     * - Ramasse les cartes restantes dans les offres.
     * - Réinitialise les offres des joueurs.
     * - Démarre un nouveau tour si la partie est jouable, sinon termine la partie.
     */
    private void finirTourEtSuivant() {
        // 1. Ramassage des cartes restantes
        for (Joueur j : listJoueur) {
            j.ajouterDerniereCarteOffreAuJest(); 
            j.setOffre(null); 
        }

        // 2. Suite du jeu
        if (estJouable()) {
            numeroTour++;
            demarrerNouveauTour();
        } else {
            terminerLaPartie();
        }
    }
    
    /**
     * Termine la partie.
     * 
     * - Change l'état de la partie en FIN.
     * - Calcule les scores finaux à l'aide du visiteur CompteurPoint.
     * - Notifie les observateurs.
     */
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

    // --- Autres Méthodes ---
    
    /**
     * Calcule les scores de tous les joueurs sans tenir compte des trophées.
     *
     * @return map associant chaque joueur à son score
     */
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

    /**
     * Détermine le joueur ayant le meilleur score sans trophées.
     *
     * @return joueur gagnant
     */
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

    /**
     * Retourne les scores finaux de la partie.
     *
     * @return une Map associant chaque joueur à son score final
     */
    public Map<Joueur, Integer> getScore() { return this.scoresFinaux; }

    /**
     * Détermine le joueur qui commence le ramassage en fonction de la variante ou de la force de la carte visible.
     *
     * @return joueur premier à jouer
     */
    public Joueur determinerPremierJoueur() {
        if (varianteChoisie == 1 && objetVariante != null) return objetVariante.appliquerVariante(this);
        Joueur vainqueur = null;
        for (Joueur j : listJoueur) {
            if (vainqueur == null || comparerForce(j.getOffre().getCarteVisible(), vainqueur.getOffre().getCarteVisible()) > 0) {
                vainqueur = j;
            }
        }
        return (vainqueur != null) ? vainqueur : listJoueur.get(0);
    }

    /**
     * Compare deux cartes pour déterminer laquelle est la plus forte.
     * 
     * - Compare d'abord la valeur des cartes.
     * - En cas d'égalité, compare la force de leur couleur via getForceCouleur().
     *
     * @param c1 première carte
     * @param c2 deuxième carte
     * @return un entier négatif si c1 < c2, positif si c1 > c2, 0 si égal
     */
    private int comparerForce(Carte c1, Carte c2) {
        if (c1.getValeur() != c2.getValeur()) return Integer.compare(c1.getValeur(), c2.getValeur());
        return Integer.compare(getForceCouleur(c1), getForceCouleur(c2));
    }

    /**
     * Retourne la force d'une couleur de carte pour la comparaison.
     *
     * @param c carte à évaluer
     * @return entier représentant la force de la couleur
     */
    public int getForceCouleur(Carte c) {
        return switch (c.getCouleur().toUpperCase()) {
            case "PIQUE" -> 4; case "TREFLE" -> 3; case "CARREAU" -> 2; case "COEUR" -> 1; default -> 0;
        };
    }

    /**
     * Vérifie si le joueur actif est un robot et doit jouer automatiquement.
     * 
     * - Ne s'applique que pendant la phase de ramassage.
     * - Déclenche l'action du robot après un délai de 1,5 seconde.
     */
    private void verifierSiRobotDoitJouer() {
        if (joueurActuel instanceof Virtuel && etat == EtatPartie.RAMASSAGE) {
            Timer timer = new Timer(1500, e -> ((Virtuel) joueurActuel).jouerAutomatiquement(this));
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Indique si la partie peut continuer (suffisamment de cartes pour tous les joueurs).
     *
     * @return true si la partie est jouable, false sinon
     */
    public boolean estJouable() {
        return listCarte != null && listCarte.getNombreCartes() >= listJoueur.size();
    }

    /**
     * Initialise le jeu en créant le paquet de cartes principal.
     * 
     * - Crée les cartes standard à partir de l'énumération TypeCarte.
     * - Si l'extension est activée, ajoute les cartes 6 et 7 de chaque couleur (sauf cœur).
     * - Initialise la pioche (listCarte) avec toutes les cartes créées.
     */
    private void initialiserJeu() {
    	LinkedList<Carte> listC = new LinkedList();
		for (TypeCarte carte : TypeCarte.values()) {
            listC.add(new Carte(carte.getValeur(), carte.getCouleur(), carte.getCondition()));
        }
		
		if (extension == true) {
			System.out.println("Les cartes 6 et 7 de chaque couleur (sauf coeur) sont ajoutés à la partie (extension)");
			for (CarteVariante carte : CarteVariante.values()) {
	            listC.add(new Carte(carte.getValeur(), carte.getCouleur(), "None"));
	        }
		}
		this.listCarte = new PaquetCarte(listC);
    }
    
    /**
     * Mélange le paquet de cartes principal de la partie.
     * Vérifie que la pioche existe avant de mélanger.
     */
    public void mélanger() { if (listCarte != null) listCarte.melanger(); }
    
    /**
     * Répartit les cartes pour déterminer les trophées de la partie.
     * 
     * - Pioche deux cartes standard (non-extension) pour les trophées si aucun trophée n'est défini.
     * - Si la variante "Sans Trophées" est active, applique la variante correspondante.
     * - Sinon, crée un objet Trophee avec les deux cartes sélectionnées.
     */
    public void répartir() {
    	if (trophees == null) {
    		Carte c1 = listCarte.piocher();
    		while (c1.getExtension()) { //on ne peut mettre un carte extension en trophées
    			c1 = listCarte.piocher();
    		}
    		
    		Carte c2 = listCarte.piocher();
    		while (c2.getExtension()) {
    			c2 = listCarte.piocher();
    		}
    		
    		if (objetVariante instanceof VarianteSansTrophees) {
    			objetVariante.appliquerVariante(this);
    		}
    		else {
    			this.trophees = new Trophee(c1,c2);
    			System.out.println("Les trophées de la partie sont : " + trophees);
    		}
    	
    	}
	}
    
    // Getters et setters 
    
    /**
     * Définit les trophées de la partie.
     *
     * @param t trophées à définir
     */
    public void setTrophees(Trophee t) { this.trophees = t; }
    
    /**
     * Retourne l'état actuel de la partie.
     *
     * @return état de la partie
     */
    public EtatPartie getEtat() { return etat; }
    
    /**
     * Retourne la liste des joueurs de la partie.
     *
     * @return liste des joueurs
     */
    public ArrayList<Joueur> getJoueur() { return listJoueur; }
    
    
    /**
     * Retourne la liste des joueurs ayant déjà joué ce tour.
     *
     * @return liste des joueurs
     */
    public ArrayList<Joueur> getJoueurOntJoueCeTour() { return ontJoueCeTour; }        

    /**
     * Retourne les cartes actuellement en attente de choix.
     *
     * @return liste des cartes en attente
     */
    public List<Carte> getCartesEnAttente() { return cartesEnAttente; }
    
    /**
     * Retourne le joueur actuel.
     *
     * @return joueur courant
     */
    public Joueur getJoueurActuel() { return joueurActuel; }
    
    /**
     * Retourne le message le plus récent pour l'interface.
     *
     * @return dernier message
     */
    public String getDernierMessage() { return dernierMessage; }
    
    /**
     * Retourne le numéro du tour actuel.
     *
     * @return numéro du tour
     */
    public int getNumeroTour() { return numeroTour; }
    
    /**
     * Retourne les scores finaux calculés.
     *
     * @return map joueurs → score
     */
    public Map<Joueur, Integer> getScoresFinaux() { return scoresFinaux; }
    
    /**
     * Retourne les trophées de la partie.
     *
     * @return trophées
     */
    public Trophee getTrophees() { return trophees; }
    
    /**
     * Retourne le numéro de variante choisie.
     *
     * @return variante choisie
     */
    public int getVariante() { return varianteChoisie; }

    /**
     * Retourne la liste des joueurs de la partie.
     *
     * @return liste des joueurs
     */
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}

	/**
	 * Définit la liste des joueurs de la partie.
	 *
	 * @param listJoueur liste des joueurs
	 */
	public void setListJoueur(ArrayList<Joueur> listJoueur) {
		this.listJoueur = listJoueur;
	}

	/**
	 * Retourne la pioche actuelle de la partie.
	 *
	 * @return paquet de cartes
	 */
	public PaquetCarte getListCarte() {
		return listCarte;
	}

	/**
	 * Définit la pioche de la partie.
	 *
	 * @param listCarte paquet de cartes
	 */
	public void setListCarte(PaquetCarte listCarte) {
		this.listCarte = listCarte;
	}

	/**
	 * Indique si l'extension est activée.
	 *
	 * @return true si extension activée, false sinon
	 */
	public boolean isExtension() {
		return extension;
	}

	/**
	 * Active ou désactive l'extension.
	 *
	 * @param extension true pour activer l'extension, false pour désactiver
	 */
	public void setExtension(boolean extension) {
		this.extension = extension;
	}

	/**
	 * Retourne le numéro de variante choisi pour la partie.
	 *
	 * @return numéro de variante
	 */
	public int getVarianteChoisie() {
		return varianteChoisie;
	}

	/**
	 * Définit la variante choisie pour la partie.
	 *
	 * @param varianteChoisie numéro de variante
	 */
	public void setVarianteChoisie(int varianteChoisie) {
		this.varianteChoisie = varianteChoisie;
	}

	/**
	 * Retourne l'indice du joueur actuel dans la phase d'offres.
	 *
	 * @return indice du joueur
	 */
	public int getIndexJoueurOffre() {
		return indexJoueurOffre;
	}

	/**
	 * Définit l'indice du joueur actuel dans la phase d'offres.
	 *
	 * @param indexJoueurOffre indice à définir
	 */
	public void setIndexJoueurOffre(int indexJoueurOffre) {
		this.indexJoueurOffre = indexJoueurOffre;
	}

	/**
	 * Retourne la variante appliqué à la partie.
	 *
	 * @return objet variante
	 */
	public Variante getObjetVariante() {
		return objetVariante;
	}

	/**
	 * Définit la variante de la partie.
	 *
	 * @param objetVariante variante à appliquer
	 */
	public void setObjetVariante(Variante objetVariante) {
		this.objetVariante = objetVariante;
	}

	/**
	 * Retourne la liste des joueurs ayant joué ce tour.
	 *
	 * @return liste des joueurs
	 */
	public ArrayList<Joueur> getOntJoueCeTour() {
		return ontJoueCeTour;
	}

	/**
	 * Définit la liste des joueurs ayant joué ce tour.
	 *
	 * @param ontJoueCeTour liste de joueurs
	 */
	public void setOntJoueCeTour(ArrayList<Joueur> ontJoueCeTour) {
		this.ontJoueCeTour = ontJoueCeTour;
	}

	/**
	 * Retourne la liste des observateurs inscrits à la partie.
	 *
	 * @return liste des observateurs
	 */
	public List<Observateur> getObservateurs() {
		return observateurs;
	}

	/**
	 * Définit la liste des observateurs de la partie.
	 *
	 * @param observateurs liste d'observateurs
	 */
	public void setObservateurs(List<Observateur> observateurs) {
		this.observateurs = observateurs;
	}

	/**
	 * Définit les cartes actuellement en attente de choix.
	 *
	 * @param cartesEnAttente liste de cartes
	 */
	public void setCartesEnAttente(List<Carte> cartesEnAttente) {
		this.cartesEnAttente = cartesEnAttente;
	}

	/**
	 * Définit l'état actuel de la partie.
	 *
	 * @param etat nouvel état de la partie
	 */
	public void setEtat(EtatPartie etat) {
		this.etat = etat;
	}

	/**
	 * Définit le dernier message affiché pour l'interface.
	 *
	 * @param dernierMessage message à afficher
	 */
	public void setDernierMessage(String dernierMessage) {
		this.dernierMessage = dernierMessage;
	}

	/**
	 * Définit le numéro du tour actuel.
	 *
	 * @param numeroTour numéro à définir
	 */
	public void setNumeroTour(int numeroTour) {
		this.numeroTour = numeroTour;
	}

	/**
	 * Définit le joueur actuellement actif.
	 *
	 * @param joueurActuel joueur actif
	 */
	public void setJoueurActuel(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	/**
	 * Définit les scores finaux de la partie.
	 *
	 * @param scoresFinaux map joueurs → scores
	 */
	public void setScoresFinaux(Map<Joueur, Integer> scoresFinaux) {
		this.scoresFinaux = scoresFinaux;
	}
}