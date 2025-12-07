package Projet;

import java.util.*;

public class Partie {
	
	private ArrayList<Joueur> listJoueur;
	private PaquetCarte listCarte;
	private String etatPartie;
	private boolean extension; //extension = nouvelles cartes
	private Trophee trophees;
	private Scanner scanner; 
	private Map<Joueur, Integer> score;
	private Variante variante; // DINA peut être null si aucune variante choisie
	private int numeroTour;//DINA 
	
	
	
	
	/**
	 * constructeur de la classe Partie qui initialise la liste de joueur, 
	 * crée la pioche (listCarte),
	 * met la partie en mode "en cours",
	 * gère si il y a des extension ou des variantes présente 
	 */
	public Partie(boolean extension, int v) {
		this.listJoueur = new ArrayList();
		
		this.etatPartie = "en cours";
		
		//variantes AJOUT DINA 
		 this.variante = variante; // null signifie aucune variante
		 this.numeroTour = 1;  
		
		//extension
		this.extension = extension;
		
		this.score = new HashMap();
		
		LinkedList listC = new LinkedList();
		for (TypeCarte carte : TypeCarte.values()) {
            listC.add(new Carte(carte.getValeur(), carte.getCouleur(), carte.getCondition()));
        }
		if (extension == true) {
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
		// peut etre on peut 'fussionner' la méthode mélanger et répartie car appelé que au début de partie
		Carte c1 = listCarte.piocher();
		Carte c2 = listCarte.piocher();
		this.trophees = new Trophee(c1,c2);
		System.out.println("Les trophées de la partie sont : " + c1 + " et " + c2);
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
	
	public void jouerTour() {
        // Créer le tour
        Tour tour = new Tour(this.listJoueur, numeroTour);

        // Déterminer l'ordre normal selon les cartes visibles
        tour.determinerOrdreJoueurs();

     // Appliquer la variante si elle existe
        if (variante != null) {
            variante.appliquer(tour);
        }

        // Faire jouer les joueurs dans l'ordre
        for (Joueur j : tour.getListeJoueurs()) {
            System.out.println("C'est au tour de " + Joueur.getNom() + " de jouer !");
            j.jouer();// PEUT NOUS AIDER A SIMPLIFIER 
        }

        numeroTour++;
    }
	
	
	public String getEtat() {
		return this.etatPartie;
	}
	
	
	public String toString() {
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = " + listJoueur +" ; variantes=" + variante + "]";
	}


	public static void main (String[] args) throws GameException {
		Scanner sc = new Scanner(System.in);

		// 1- demander si extension ou pas
        System.out.println("Voulez-vous activer les extensions/ nouvelles cartes ? (1=oui, 0=non)");
        boolean extension = sc.nextInt() == 1;
        
        // 2- demander si variantes ou pas 
        System.out.println("Choisissez une variante : 0=base, 1=v1, 2=v2");
        int variante = sc.nextInt();
       
        //instanciation de la partie 
		Partie p = new Partie(extension, variante);
			
		//création et ajout des joueurs à la partie 
		Joueur j1 = new Joueur("j1","reel",p); 
		Joueur j2 = new Joueur("j2","reel",p);
		Virtuel j3 = new Virtuel("j3", new StrategieBasique(),p);
		p.ajouterJoueur(j1);
		p.ajouterJoueur(j2);
		p.ajouterJoueur(j3);
		
		p.mélanger();
		p.répartir();
		
		
		// IL FAUT INTEGRER LES TOURS DANS LA CLASSE PARTIE
 
		 //  Demander à l'utilisateur de choisir une variante
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Choisissez une variante pour cette partie :");
        System.out.println("1 - Inversion (inverse l'ordre des joueurs chaque tour)");
        System.out.println("2 - Départ aléatoire (mélange aléatoirement l'ordre des joueurs chaque tour)");
        System.out.println("3 - Aucune (ordre normal selon les cartes visibles)");

        int choix = sc1.nextInt();
        sc1.nextLine(); // consommer le retour à la ligne

        Variante varianteChoisie = null;
        switch (choix) {
            case 1 -> varianteChoisie = new Variante("Inversion");
            case 2 -> varianteChoisie = new Variante("Départ aléatoire");
            case 3 -> varianteChoisie = null; // aucune variante
            default -> System.out.println("Choix invalide, aucune variante appliquée.");
        }
		

        while (p.estJouable()) {
        	//tour de plus 
        	
        	
        	
        
        	//chaque joueur crée son offre 
        	j1.creerMonOffreHumain(p); 
        	j2.creerMonOffreHumain(p);
        	j3.creerMonOffreVirtuel(p); 
		
        	
        	
        	// déterminer ordre de joueurs
        	
		
        	//chaque joueur prend une offre 
        	j1.prendreOffre(j2,p); 
        	j2.prendreOffre(j1,p);
        	j3.prendreOffreVirtuel(p);
		
        	//chaque joueur ajoute a son deck 
        	j1.ajouterMainAuDeck();
        	j2.ajouterMainAuDeck();
        	j3.ajouterMainAuDeckVirtuel();
		
        }
		
		//distribuer les trophées, calculer les points, annoncer le gagnant 
		p.finPartie();
		
		
		
	}

	
}
