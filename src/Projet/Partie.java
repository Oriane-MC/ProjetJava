package Projet;

import java.util.*;

public class Partie {
	
	private ArrayList<Joueur> listJoueur;
	private PaquetCarte listCarte;
	private String etatPartie;
	private int variantes;
	private Trophee trophees;
	private Map<Joueur, Integer> score;
	
	
	/**
	 * constructeur de la classe Partie qui initialise la liste de joueur, 
	 * crée la pioche (listCarte),
	 * et met la partie en mode "en cours"
	 */
	public Partie() {
		this.listJoueur = new ArrayList();
		this.etatPartie = "en cours";
		
		LinkedList listC = new LinkedList();
		for (TypeCarte carte : TypeCarte.values()) {
            listC.add(new Carte(carte.getValeur(), carte.getCouleur(), carte.getCondition()));
        }
		this.listCarte = new PaquetCarte(listC);
		
		this.score = new HashMap();
	}
	
	/**
	 * surcharge du constructeur de la classe Partie qui, ici permet de créer une partie avec une variantes 
	 * et qui permet de : initialiser la liste de joueur, 
	 * créer la pioche (listCarte),
	 * et mettre la partie en mode "en cours"
	 */
	public Partie(int v) {
		this.listJoueur = new ArrayList();
		this.etatPartie = "en cours";
		
		LinkedList listC = new LinkedList();
		for (TypeCarte carte : TypeCarte.values()) {
            listC.add(new Carte(carte.getValeur(), carte.getCouleur(), carte.getCondition()));
        }
		this.listCarte = new PaquetCarte(listC);
		
		this.score = new HashMap();

		this.variantes = v;
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
	 * méthode privée car elle ne s'appelle qu'à la fin de la partie ou dans le calcul des scores, 
	 * l'utilisateur ne peut l'appeler pour eviter de l'appeler si les scores sont nuls
	 * @return
	 */
	public Joueur joueurGagnant() {
		Joueur gagnant = new Joueur("tmp","tmp");
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
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = " + listJoueur +" ; variantes=" + variantes + "]";
	}


	public static void main (String[] args) throws IllegalStateException {
		// 1- demander si variantes ou pas 
		// 2- demander si extension ou pas
		Partie p = new Partie();
			
		
		Joueur j1 = new Joueur("j1","reel"); 
		Joueur j2 = new Joueur("j2","reel");
		Virtuel j3 = new Virtuel("j3", new StrategieBasique());
		p.ajouterJoueur(j1);
		p.ajouterJoueur(j2);
		p.ajouterJoueur(j3);

		
		p.mélanger();
		p.répartir();
		
		
		// IL FAUT INTEGRER LES TOURS DANS LA CLASSE PARTIE
		
		// déterminer ordre de joueurs
		
		
		//chaque joueur crée son offre 
		j1.creerMonOffreHumain(null); //créer offre doit prendre en paramètre la partie, pk y'a 2 créer mon offre ?
		j2.creerMonOffreHumain(null, null);
		
		j3.creerMonOffreVirtuel(null, null); 
		
		
		//chaque joueur prend une offre 
		j1.prendreOffre(null); 
		j2.prendreOffre(null);
		
		j3.prendreOffre(null);
		
		//chaque joueur ajoute a son deck 
		j1.ajouterCarteDeck(null);
		j2.ajouterCarteDeck(null);
		
		j3.ajouterCarteDeck(null);
		
		//distribuer les trophées, calculer les points, annoncer le gagnant 
		p.finPartie();
		
		
		
		
		
		
	}

	
}
