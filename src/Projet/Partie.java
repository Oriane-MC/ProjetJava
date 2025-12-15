package Projet;


import java.io.*;
import java.util.*;

public class Partie implements Serializable{
	
	private ArrayList<Joueur> listJoueur;
	private PaquetCarte listCarte;
	private String etatPartie;
	private boolean extension; //extension = nouvelles cartes
	private Trophee trophees;
	private transient Scanner scanner; 
	private Map<Joueur, Integer> score;
	private Variante variante; // DINA peut être null si aucune variante choisie
	private int numeroTour;//DINA 
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * constructeur de la classe Partie qui initialise la liste de joueur, 
	 * crée la pioche (listCarte),
	 * met la partie en mode "en cours",
	 * gère si il y a des extension ou des variantes présente 
	 */
	public Partie(boolean extension, int v) {
		this.listJoueur = new ArrayList();
		
		this.etatPartie = "en cours";
		
		//variantes 
        switch (v) {
            case 1 : 
            	this.variante = new VariantePremierJoueurAleatoire();
                variante.estUtilise();
                System.out.println("Variante : "+variante +" appliquée pour cette partie");
                break;
            case 2 : 
            	this.variante = new VarianteSansTrophees();
                variante.estUtilise();
                System.out.println("Variante : "+variante +" appliquée pour cette partie");
                break;
            case 3 : 
            	this.variante = null; // aucune variante
            	System.out.println("Aucune variante appliquée pour cette partie");
            	break;
            default :
            	this.variante = null; // aucune variante
            	System.out.println("Aucune variante appliquée pour cette partie");
            	break;
        }
     
		
		this.numeroTour = 0;  
		
		//extension
		this.extension = extension;
		
		this.score = new HashMap();
		
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
     * Permet de récupérer le scanner utilisé dans la partie.
     */
    public Scanner getScanner() {
        return this.scanner;
    }
	
		/**
	 * méthode qui mélange le paquet de carte/la pioche de la partie
	 */
	public void mélanger() {
		listCarte.melanger();;
	}
	
	/**
	 * méthode qui permet de répartir les cartes : permet de déterminer et créer les trophées,
	 * et de créer la pioche qui est un paquet de carte
	 */
	public void répartir() {
		Carte c1 = listCarte.piocher();
		while (c1.getExtension()) { //on ne peut mettre un carte extension en trophées
			c1 = listCarte.piocher();
		}
		
		Carte c2 = listCarte.piocher();
		while (c2.getExtension()) {
			c2 = listCarte.piocher();
		}
		
		if (variante instanceof VarianteSansTrophees) {
			variante.appliquerVariante(this);
		}
		else {
			this.trophees = new Trophee(c1,c2);
			System.out.println("Les trophées de la partie sont : " + trophees);
		}
		
	}
	
	public void setTrophees(Trophee t) {
		this.trophees = t;
	}
	
	public Variante getVariante() {
		return variante;
	}
	
	public boolean estJouable() {
		
		//le nombre de joueurs doit etre de 3 ou 4 
		if (this.listJoueur.size() > 4 || this.listJoueur.size() < 3) {
			System.out.println("le nombre de joueurs n'est pas dans les normes (entre 3 et 4 joueurs acceptés)");
			return false;
		}
					
		//si il n'y a plus assez de carte a piocher pour chaque joueur alors le jeux s'arrete 
		if (this.listCarte.getListPioche().size() < (this.listJoueur.size()*2)) {
			System.out.println("le jeu est terminé : il n'y a plus assez de cartes à piocher");
			return false;
		}
		
		return true;
		
	}
	
	
	/**
	 * méthode qui ajoute un joueur à la partie et initialise son score à 0
	 * @param j
	 */
	public void ajouterJoueur(Joueur j) {
		this.listJoueur.add(j);
		this.score.put(j,0);
	}
	
	
	/** 
     * méthode relatif au pattern Visitor
     * @param v
     * @return
     */
    public Map<Joueur, Integer> accept(Visitor v) throws GameException {
    	return v.visit(this);
    }
	
    /**
     * méthode relatif au pattern Visitor qui permet de calculer le score de la partie final
     * @throws GameException
     */
    public void calculerScoreFinal() throws GameException {
    	Visitor cpt = new CompteurPoint();
    	this.score = this.accept(cpt);
    }
    
    
   
	public ArrayList<Joueur> getJoueur() {
		return listJoueur;
	}


	public PaquetCarte getPioche() {
		return listCarte;
	}
	
	
	public Trophee getTrophees() {
		return trophees;
	}
	
	public Map<Joueur, Integer> getScore() {
		return score;
	}


	public void calculerScoreSansTrophees() throws GameException {
		for (Joueur j : this.listJoueur) {
			CompteurPoint cpt = new CompteurPoint();
			score.put(j, cpt.visit(j));
		}
	}
	
	public void finPartie() throws GameException {
		this.calculerScoreFinal();
		this.etatPartie = "terminé";
		System.out.println("la partie est " + this.etatPartie +". Voici les score : ");
		Iterator<Joueur> it = this.score.keySet().iterator();
		while (it.hasNext()) {
			Joueur key = it.next();
			Integer valeur = this.score.get(key);
			System.out.println(key.getNom() + " a " + valeur + " pts." );
		}
		System.out.println("Le gagnant est donc : " + this.joueurGagnant().getNom());	
	}
	
	/**
	 * méthode qui permet de renvoyer le nom du gagnant de la partie
	 * @return
	 */
	public Joueur joueurGagnant() {
		Joueur gagnant = new Joueur("tmp","tmp",null);
		int score_max = 0;
		Iterator<Joueur> it = this.score.keySet().iterator();
		while (it.hasNext()) {
			Joueur key = it.next();
			Integer valeur = this.score.get(key);
			
			if (valeur > score_max) {
				gagnant = key;
				score_max = valeur;
			}
		}
		return gagnant;
	}
	
	
	
	
	public String getEtat() {
		return this.etatPartie;
	}
	
	
	public String toString() {
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = " + listJoueur +" ; variantes=" + variante + "]";
	}

	
	
	
	
	
	
	
	/**
     * Détermine l'ordre des joueurs selon la carte visible de leur offre.
     * Règles de tri  :
     * - La carte visible la plus forte joue en premier
     * - En cas d'égalité, tri selon la hiérarchie des couleurs : Pique > Trèfle > Carreau > Coeur > Joker
     *Si un joueur n'a pas d'offre, il est considéré comme ayant une carte visible nulle.
     */
    public Joueur determinerPremierJoueur() {
    	
    	if (variante instanceof VariantePremierJoueurAleatoire ){
    		Joueur j = variante.appliquerVariante(this);
    		return j;
    	}
    	else {
    		Joueur joueurPremier = listJoueur.get(0);
        	Carte plusForte = joueurPremier.offre.getCarteVisible();
        	for (Joueur j : this.listJoueur) {
        		Carte carteVisible = j.offre.getCarteVisible();
        		if (plusForte.getCouleur().equals(carteVisible.getCouleur())) {
        			if (plusForte.getValeur() < carteVisible.getValeur()) {
        				plusForte = carteVisible;
        				joueurPremier = j;
        			}
        		}
        		else {
        			int val1 = this.getForceCouleur(plusForte);
        			int val2 = this.getForceCouleur(carteVisible);
        			
        			if (val1 < val2) {
        				plusForte = carteVisible;
        				joueurPremier = j;
        			}
        		}	
        	}
        	return joueurPremier;
    	}   	
    }

    /**
     * Retourne la force d'une couleur selon les règles : 
     * Pique > Trèfle > Carreau > Coeur > Joker
     * * @param c carte dont la couleur doit être évaluée
     * @return un entier représentant la "force" de la couleur
     */
    protected int getForceCouleur(Carte c) {
        if (c == null) return 0;

        String couleur = c.getCouleur().toLowerCase();
        return switch (couleur) {
            case "pique" -> 4;
            case "trèfle" -> 3;
            case "carreau" -> 2;
            case "coeur" -> 1;
            case "joker" -> 0;
            default -> 0;
        };
    }
    
    public void tourSuivant() {
    	this.numeroTour ++;
    	System.out.println("Le tour n°"+this.numeroTour+" a été créé.");
    }
    
    	
    /**
     * Sauvegarde la partie actuelle dans un fichier.
     */
    public void sauvegarder(String fichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream(fichier))) {
            oos.writeObject(this);
            System.out.println("Partie sauvegardée dans : " + fichier);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde.");
        }
    }
	
    /**
     * Charge une partie depuis un fichier de sauvegarde.
     */
    public static Partie charger(String fichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(fichier))) {
            Partie p = (Partie) ois.readObject();
            System.out.println("Partie chargée depuis : " + fichier);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement.");
            return null;
        }
    }

    public static void main (String[] args) throws GameException {
   	    Scanner sc = new Scanner(System.in);
    	    System.out.println("Bienvenue dans le jeu de Oriane et Dina, nous te souhaitons une superbe partie !");

    	    Partie p = null;
    	    File fichierSave = new File("partie.txt");

    	    // Vérifier si une partie sauvegardée existe
    	    if (fichierSave.exists()) {
    	        System.out.println("Une partie sauvegardée a été trouvée.");
    	        System.out.println("1 - Reprendre la partie");
    	        System.out.println("2 - Nouvelle partie");
    	        int choix = -1;
    	        while (choix != 1 && choix != 2) {
    	            if (sc.hasNextInt()) {
    	                choix = sc.nextInt();
    	                if (choix != 1 && choix != 2) {
    	                    System.out.println("Entrez 1 ou 2.");
    	                }
    	            } else {
    	                sc.next();
    	            }
    	        }

    	        if (choix == 1) {
    	            p = Partie.charger("partie.txt");
    	            if (p != null) {
    	                // Réinjecter le scanner (transient)
    	                p.scanner = sc;
    	                // Réattacher chaque joueur à la partie et réinitialiser la stratégie pour les Virtuels
    	                for (Joueur j : p.getJoueur()) {
    	                    j.setPartie(p);
    	                    j.setScanner(sc);
    	                    if (j instanceof Virtuel v) {
    	                        v.reinitialiserStrategie();
    	                    }
    	                }
    	                System.out.println("Partie rechargée. Reprise en cours...");
    	            }
    	        }
    	    }

    	    // Si aucune partie chargée, créer une nouvelle partie
    	    if (p == null) {
    	        // demander si extension ou pas
    	        int choixExtension = -1;
    	        while (choixExtension != 0 && choixExtension != 1) {
    	            System.out.println("Voulez-vous activer les extensions pour cette partie (nouvelles cartes 6 et 7 de chaque couleur sauf coeur) ? (1=oui, 0=non)");
    	            if (sc.hasNextInt()) {
    	                choixExtension = sc.nextInt();
    	                if (choixExtension != 0 && choixExtension != 1) {
    	                    System.out.println("Choix invalide. Entrez 1 ou 0.");
    	                }
    	            } else {
    	                sc.next();
    	            }
    	        }
    	        boolean extension = (choixExtension == 1);

    	        // demander variante
    	        int variante = -1;
    	        while (variante < 1 || variante > 3) {
    	            System.out.println("Choisissez une variante pour cette partie :");
    	            System.out.println("1 - Premier Joueur Aléatoire Par Tour");
    	            System.out.println("2 - Partie Sans Trophées");
    	            System.out.println("3 - Aucune");
    	            if (sc.hasNextInt()) {
    	                variante = sc.nextInt();
    	            } else {
    	                sc.next();
    	            }
    	        }

    	        // instanciation de la partie
    	        p = new Partie(extension, variante);

    	        // création et ajout des joueurs
    	        int nbJoueurs = 0;
    	        while (nbJoueurs != 3 && nbJoueurs != 4) {
    	            System.out.print("Choisissez le nombre de joueurs pour cette partie [3 ou 4] : ");
    	            if (sc.hasNextInt()) {
    	                nbJoueurs = sc.nextInt();
    	            } else {
    	                sc.next();
    	            }
    	        }

    	        for (int i = 0; i < nbJoueurs; i++) {
    	            System.out.println("Quel est le nom du joueur ?");
    	            String nom = sc.next();
    	            int typeJ = -1;
    	            while (typeJ != 0 && typeJ != 1) {
    	                System.out.print("Est-ce un Joueur Réel (0) ou Virtuel (1) ? ");
    	                if (sc.hasNextInt()) {
    	                    typeJ = sc.nextInt();
    	                } else {
    	                    sc.next();
    	                }
    	            }

    	            if (typeJ == 0) {
    	                p.ajouterJoueur(new Joueur(nom, "Humain", p));
    	            } else {
    	                int strat = -1;
    	                while (strat < 0 || strat > 3) {
    	                    System.out.println("Quel type de stratégie voulez-vous ?");
    	                    System.out.println("0 - aléatoire");
    	                    System.out.println("1 - basique");
    	                    System.out.println("2 - défensive");
    	                    System.out.println("3 - aggressive");
    	                    if (sc.hasNextInt()) {
    	                        strat = sc.nextInt();
    	                    } else {
    	                        sc.next();
    	                    }
    	                }
    	                Virtuel j = new Virtuel(nom, strat, p);
    	                p.ajouterJoueur(j);
    	                System.out.println("La stratégie pour le joueur virtuel est donc : " + j.getStrategie());
    	            }
    	        }

    	        // récapitulatif
    	        System.out.println("Récapitulatif : voici les joueurs de la partie");
    	        for (Joueur j : p.getJoueur()) {
    	            System.out.println(j);
    	        }

    	        // mélange et répartition des cartes
    	        p.mélanger();
    	        p.répartir();
    	    }

    	    // boucle de jeu
    	    while (p.estJouable()) {
    	        p.tourSuivant();
    	        for (Joueur j : p.getJoueur()) {
    	            j.creerMonOffre(p);
    	        }
    	        
    	        ArrayList<Joueur> ontJoueCeTour = new ArrayList<>();
    	        
    	        Joueur suivant = p.determinerPremierJoueur().prendreOffreEtJoueurSuivant(p, ontJoueCeTour );
    	       
    	        
    	        for (int i = 0; i < (p.getJoueur().size()-1); i++) {
    	        	Joueur j = suivant;
    	            suivant = j.prendreOffreEtJoueurSuivant(p, ontJoueCeTour);
    	        }

    	        for (Joueur j : p.getJoueur()) {
    	            j.ajouterCarteDeck();
    	        }

    	        // sauvegarde automatique
    	        System.out.println("Voulez-vous sauvegarder la partie ? (1=oui, 0=non)");
    	        int save = sc.nextInt();
    	        if (save == 1) {
    	            p.sauvegarder("partie.txt");
    	        }
    	    }

    	    // fin de partie
    	    p.finPartie();

    	    // vérification
    	    for (Joueur j : p.getJoueur()) {
    	        System.out.println(j.getNom() +j.getDeckPossede());
    	    }
    	    System.out.println(p.getTrophees());
    	}

}
