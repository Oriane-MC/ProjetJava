package Projet;

import java.util.*;

import Q1.InvalidPolynomException;

public class Partie {
	
	private ArrayList<Joueur> listJoueur;
	private LinkedList<Carte> listCarte;
	private String etatPartie;
	private int variantes;
	private Trophee trophees;
	private Map<Joueur, Integer> score = new HashMap();
	
	
	public Partie() {
		this.listJoueur = new ArrayList();
		this.listCarte = new LinkedList();
		this.etatPartie = "en cours";
		
		for (TypeCarte carte : TypeCarte.values()) {
            listCarte.add(new Carte(carte.getValeur(), carte.getCouleur(), carte.getCondition()));
        }	
	}
	
	
	public void mélanger() {
		Collections.shuffle(listCarte);
	}
	
	
	public void répartir() {
		// peut etre on peut 'fussionner' la méthode mélanger et répartie car appelé que au début de partie
		Carte c1 = listCarte.poll();
		Carte c2 = listCarte.poll();
		this.Trophees = new Trophee(c1,c2);
		new PaquetCarte(listCarte);
	}
	
	public void ajouterJoueur(Joueur j) {
		this.listJoueur.add(j);
	}
	
	
	/**
	 * méthode qui permet de distribuer les trophées au joueur qui les mérite
	 * et qui calcule les score pour chaque joueur et met donc a jour l'attirbut score
	 */
	public void calculerScore() throws IllegalStateException {
		
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
					
				}
				else if (t.getCouleur().equals("trefle")) {
					//cas où condition = lowest pique
					if (t.getValeur() == 4) {
						
					}
					//cas où condition = lowest coeur
					else if (t.getValeur() == 2) {
						
					}
					else {
						IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
						throw e;
					}
				}
				//cas où condition =  lowest carreau
				else if (t.getCouleur().equals("carreau")) {
					
				}
				else {
					IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
					throw e;
				}
			
				

				
			}
			else if (t.getCondition().equals("Highest")) {
				
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
			else if (t.getCondition().equals("BestJest")){    // dans le cas ou on a besoin de calculer les points avant de donner les trophees 
				// calculer les scores de chaque joueur et les enregistrer 
				// les comparer 
				// attribuer la carte au meilleur joueur
				// recalculer le score de celui qui a changé de Jest 
				// return pour ne pas recalculer 
			}
			else if (t.getCondition().equals("BestJestAndNoJoker")) {
				// calculer les scores de chaque joueur et les enregistrer en excluant celui qui a le joker 
				// les comparer 
				// attribuer la carte au meilleur joueur
				// recalculer le score de celui qui a changé de Jest 
				// return pour ne pas recalculer 
			}
			else {
				IllegalStateException e = new IllegalStateException("problème avec la condition du trophée");
				throw e;
			}
			
			
			// calculer les scores pour chaque joueur
		}
	}
	
	public void finPartie() {
		this.etatPartie = "terminé";
		System.out.println("la partie est " + this.etatPartie +". Voici les score : ");
		
		Iterator<Joueur> it = this.score.keySet().iterator();
		Joueur gagnant = new Joueur("tmp","tmp");
		int score_max = 0;
		while (it.hasNext()) {
			Joueur key = it.next();
			Integer valeur = this.score.get(key);
			System.out.println(key.getNom() + " a " + valeur + " pts." );
			
			if (valeur > score_max) {
				gagnant = key;
				score_max = valeur;
			}
		}
		System.out.println("Le gagnant est donc : " + gagnant.getNom());	
	}
	
	public String getEtat() {
		return this.etatPartie;
	}
	
	
	public String toString() {
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = " + listJoueur + " ; Paquet Carte" + listCarte +  
				"; variantes=" + variantes + "]";
	}


	public static void main (String[] args) {
		// 1- demander si variantes ou pas 
		// 2- demander si extension ou pas
		Partie p = new Partie();
				
		Joueur j1 = new Joueur("j1","reel"); // je ne sais plus comment on fait pour récupérer une valeur d'une énumeration 
		Joueur j2 = new Joueur("j2","reel");
		
		Virtuel j3 = new Virtuel("j3", new StrategieBasique());
		
		
		
				
		p.mélanger();
		p.répartir();
	
		//chaque joueur crée son offre 
		
		//chaque joueur prend une offre 
		
		//chaque joueur ajoute a son deck 
		
		//distribuer les trophées 
		p.
		
		//calculer les points 
		p.calculerScore();
		p.finPartie();
		
		
		
		System.out.println(p);
		
		
	}
}
