package Projet;

import java.util.*;

public class Partie {
	
	private ArrayList<Joueur> listJoueur;
	private PaquetCarte listCarte;
	private String etatPartie;
	private int variantes;
	private Trophee trophees;
	private Map<Joueur, Integer> score = new HashMap();
	
	
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
	 * méthode qui permet de distribuer les trophées au joueur qui les mérite
	 * et qui calcule les score pour chaque joueur et met donc à jour l'attribut score
	 */
	public void calculerScoreFinal() throws IllegalStateException {
		
		//on fixe les decks des joeurs au debut du calcul 
		//: on ne prend pas en compte l'effet de l'obtention d'un trophées sur la determination 
		//de l'obtention du 2eme trophées 
		Map<Joueur, Deck> joueur_deck = new HashMap();
		for (Joueur j : this.listJoueur) {
			joueur_deck.put(j, j.getDeckPossede());
		}
		
		// attention dans le cas ou le joker est un des trophées et que l'autre trophées a une condition sur le joker 
		// alors on choisit de ne pas donner le trophée avec la condition sur le joker ou alors le joueur concernait 
		// (celui qui a le plus de point (la condition du joker) prends les DEUX cartes

		for (Carte t : this.trophees.getListTrophee() ) {
			
			if (t.getCondition().equals("Lowest")) {
				//cas où condition = lowest trèfle
				if (t.getCouleur().equals("pique")) {
					Joueur gagnant = new Joueur("tmp","tmp");
					int lowest = 5;
					Iterator<Joueur> it = joueur_deck.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Deck valeur = joueur_deck.get(key);
						for (Carte c : valeur.getCartes()) {
							if (c.getCouleur().equals("trefle")) {
								if (c.getValeur() < lowest ) {
									lowest = c.getValeur();
									gagnant = key;
								}
							}
						}
					}
					if (gagnant.getNom().equals("tmp")) {
						IllegalStateException e = new IllegalStateException("personne n'a de trèfle");
						throw e;
					}
					gagnant.ajouterCarteDeck(t);
				}
				else if (t.getCouleur().equals("trefle")) {
					//cas où condition = lowest pique
					if (t.getValeur() == 4) {
						Joueur gagnant = new Joueur("tmp","tmp");
						int lowest = 5;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							for (Carte c : valeur.getCartes()) {
								if (c.getCouleur().equals("pique")) {
									if (c.getValeur() < lowest ) {
										lowest = c.getValeur();
										gagnant = key;
									}
								}
							}
						}
						if (gagnant.getNom().equals("tmp")) {
							IllegalStateException e = new IllegalStateException("personne n'a de pique");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					//cas où condition = lowest coeur
					else if (t.getValeur() == 2) {
						Joueur gagnant = new Joueur("tmp","tmp");
						int lowest = 5;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							for (Carte c : valeur.getCartes()) {
								if (c.getCouleur().equals("coeur")) {
									if (c.getValeur() < lowest ) {
										lowest = c.getValeur();
										gagnant = key;
									}
								}
							}
						}
						if (gagnant.getNom().equals("tmp")) {
							IllegalStateException e = new IllegalStateException("personne n'a de coeur");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					else {
						IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
						throw e;
					}
				}
				//cas où condition =  lowest carreau
				else if (t.getCouleur().equals("carreau")) {
					Joueur gagnant = new Joueur("tmp","tmp");
					int lowest = 5;
					Iterator<Joueur> it = joueur_deck.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Deck valeur = joueur_deck.get(key);
						for (Carte c : valeur.getCartes()) {
							if (c.getCouleur().equals("carreau")) {
								if (c.getValeur() < lowest ) {
									lowest = c.getValeur();
									gagnant = key;
								}
							}
						}
					}
					if (gagnant.getNom().equals("tmp")) {
						IllegalStateException e = new IllegalStateException("personne n'a de carreau");
						throw e;
					}
					gagnant.ajouterCarteDeck(t);
				}
				else {
					IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
					throw e;
				}	
			}
			else if (t.getCondition().equals("Highest")) {
				//cas où condition = highest trèfle
				if (t.getCouleur().equals("pique")) {
					Joueur gagnant = new Joueur("tmp","tmp");
					int highest = 0;
					Iterator<Joueur> it = joueur_deck.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Deck valeur = joueur_deck.get(key);
						for (Carte c : valeur.getCartes()) {
							if (c.getCouleur().equals("trefle")) {
								if (c.getValeur() > highest ) {
									highest = c.getValeur();
									gagnant = key;
								}
							}
						}
					}
					if (gagnant.getNom().equals("tmp")) {
						IllegalStateException e = new IllegalStateException("personne n'a de trèfle");
						throw e;
					}
					gagnant.ajouterCarteDeck(t);
				}
				else if (t.getCouleur().equals("trefle")) {
					//cas où condition = highest pique
					if (t.getValeur() == 1) {
						Joueur gagnant = new Joueur("tmp","tmp");
						int highest = 0;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							for (Carte c : valeur.getCartes()) {
								if (c.getCouleur().equals("pique")) {
									if (c.getValeur() > highest ) {
										highest = c.getValeur();
										gagnant = key;
									}
								}
							}
						}
						if (gagnant.getNom().equals("tmp")) {
							IllegalStateException e = new IllegalStateException("personne n'a de pique");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					//cas où condition = highest coeur
					else if (t.getValeur() == 3) {
						Joueur gagnant = new Joueur("tmp","tmp");
						int highest = 0;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							for (Carte c : valeur.getCartes()) {
								if (c.getCouleur().equals("coeur")) {
									if (c.getValeur() > highest ) {
										highest = c.getValeur();
										gagnant = key;
									}
								}
							}
						}
						if (gagnant.getNom().equals("tmp")) {
							IllegalStateException e = new IllegalStateException("personne n'a de coeur");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					else {
						IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
						throw e;
					}
				}
				//cas où condition =  highest carreau
				else if (t.getCouleur().equals("carreau")) {
					Joueur gagnant = new Joueur("tmp","tmp");
					int highest = 0;
					Iterator<Joueur> it = joueur_deck.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Deck valeur = joueur_deck.get(key);
						for (Carte c : valeur.getCartes()) {
							if (c.getCouleur().equals("carreau")) {
								if (c.getValeur() > highest ) {
									highest = c.getValeur();
									gagnant = key;
								}
							}
						}
					}
					if (gagnant.getNom().equals("tmp")) {
						IllegalStateException e = new IllegalStateException("personne n'a de carreau");
						throw e;
					}
					gagnant.ajouterCarteDeck(t);
				}
				else {
					IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
					throw e;
				}	
			}
			else if (t.getCondition().equals("Majority")) {
				//cas où condition = majority de 4
				if (t.getCouleur().equals("carreau")) {
					Joueur gagnant = new Joueur("tmp","tmp");
					int majority = 0;
					Iterator<Joueur> it = joueur_deck.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Deck valeur = joueur_deck.get(key);
						int nb_4 = 0;
						for (Carte c : valeur.getCartes()) {
							if (c.getValeur() == 4) {
								nb_4 += 1;
							}
						}
						if (nb_4 > majority) {
							majority = nb_4;
							gagnant = key;
						}
					}
					if (majority == 0) {
						IllegalStateException e = new IllegalStateException("personne n'a de 4");
						throw e;
					}
					gagnant.ajouterCarteDeck(t);
				}
				else if (t.getCouleur().equals("pique")){
					//cas où condition = majority de 3
					if (t.getValeur() == 2) {
						Joueur gagnant = new Joueur("tmp","tmp");
						int majority = 0;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							int nb_3 = 0;
							for (Carte c : valeur.getCartes()) {
								if (c.getValeur() == 3) {
									nb_3 += 1;
								}
							}
							if (nb_3 > majority) {
								majority = nb_3;
								gagnant = key;
							}
						}
						if (majority == 0) {
							IllegalStateException e = new IllegalStateException("personne n'a de 3");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					//cas où condition = majority de 2
					else if (t.getValeur() == 3){
						Joueur gagnant = new Joueur("tmp","tmp");
						int majority = 0;
						Iterator<Joueur> it = joueur_deck.keySet().iterator();
						while (it.hasNext()) {
							Joueur key = it.next();
							Deck valeur = joueur_deck.get(key);
							int nb_2 = 0;
							for (Carte c : valeur.getCartes()) {
								if (c.getValeur() == 2) {
									nb_2 += 1;
								}
							}
							if (nb_2 > majority) {
								majority = nb_2;
								gagnant = key;
							}
						}
						if (majority == 0) {
							IllegalStateException e = new IllegalStateException("personne n'a de 2");
							throw e;
						}
						gagnant.ajouterCarteDeck(t);
					}
					else {
						IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
						throw e;
					}
				}
				else {
					IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
					throw e;
				}	
			}
			else if (t.getCondition().equals("Joker")) {
				Joueur gagnant = new Joueur("tmp","tmp");
				Iterator<Joueur> it = joueur_deck.keySet().iterator();
				while (it.hasNext()) {
					Joueur key = it.next();
					Deck valeur = joueur_deck.get(key);
					for (Carte c : valeur.getCartes()) {
						if (c.getCouleur().equals("joker")) {
								gagnant = key;
						}
					}
				}
				if (gagnant.getNom().equals("tmp")) {
					IllegalStateException e = new IllegalStateException("personne n'a de Joker");
					throw e;
					}
				gagnant.ajouterCarteDeck(t);
			}
			else if (t.getCondition().equals("BestJest")){   
				this.calculerScoreSansTrophees();
				Joueur gagnant = this.joueurGagnant();
				gagnant.ajouterCarteDeck(t);
			}
			else if (t.getCondition().equals("BestJestAndNoJoker")) {
				this.calculerScoreSansTrophees();
				Joueur gagnant = this.joueurGagnant();
				boolean joker = false;
				for (Carte c : gagnant.getDeckPossede().getCartes()) {
					if (c.getCouleur().equals("joker")) {
						joker = true;
					}
				}
				if (joker == false) {
					gagnant.ajouterCarteDeck(t);
				}
				else {
					int score_max = score.get(gagnant);
					int score_max_second = 0;
					
					Iterator<Joueur> it = this.score.keySet().iterator();
					while (it.hasNext()) {
						Joueur key = it.next();
						Integer valeur = this.score.get(key);
						
						if (valeur<score_max && valeur>score_max_second) {
							gagnant = key;
							score_max_second = valeur;
						}
					}
					gagnant.ajouterCarteDeck(t);
				}
			}
			else {
				IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
				throw e;
			}
			
			
			// calcul des scores pour chaque joueur et les ajoute à score
			for (Joueur j : this.listJoueur) {
				CompteurPoint cpt = new CompteurPoint();
				score.put(j, cpt.visiter(j));
			}
		}
	}
	
	public void calculerScoreSansTrophees() {
		for (Joueur j : this.listJoueur) {
			CompteurPoint cpt = new CompteurPoint();
			score.put(j, cpt.visiter(j));
		}
	}
	
	public void finPartie() throws IllegalStateException {
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
	private Joueur joueurGagnant() {
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
