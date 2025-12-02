package Projet;

import java.util.*;

public class Trophee {
	
	private LinkedList<Carte> listTrophee;
	
	public Trophee (Carte c1, Carte c2) {
		this.listTrophee = new LinkedList();
		this.listTrophee.add(c1);
		this.listTrophee.add(c2);
	}
	
	public Carte getTrophee1() {
		return this.listTrophee.get(0);
	}
	
	public Carte getTrophee2() {
		return this.listTrophee.get(1);
	}
	
	public LinkedList<Carte> getListTrophee(){
		return this.listTrophee;
	}

    
    
    public List<Joueur> determinerGagnant(Partie p) {
    	
    	List<Joueur> listJ = new ArrayList();
    	
    	for (Carte t : p.getTrophees().getListTrophee()) {
    		
    		switch (t.getCondition()) {
    			case "BestJest" : 
    				listJ.add(gagnantBestJest(p));
    				break;
    			case "Joker" :
    				listJ.add(gagnantJoker(p));
    				break;
    			case "Majority4" : 
    				listJ.add(gagnantMajority4(p));
    				break;
    			case "HighestCarreau" :
    				listJ.add(gagnantHighestCarreau(p));
    				break;
    			case "LowestCarreau" : 
    				listJ.add(gagnantLowestCarreau(p));
    				break;
    			case "BestJestNoJoker" :
    				listJ.add(gagnantBestJestNoJoker(p));
    				break;
    			case "HighestPique" : 
    				listJ.add(gagnantHighestPique(p));
    				break;
    			case "LowestCoeur" : 
    				listJ.add(gagnantLowestCoeur(p));
    				break;
    			case "HighestCoeur" : 
    				listJ.add(gagnantHighestCoeur(p));
    				break;
    			case "LowesttPique" : 
    				listJ.add(gagnantLowesttPique(p));
    				break;
    			case "HighestTrefle" :
    				listJ.add(gagnantHighestTrefle(p));
    				break;
    			case "Majority3" : 
    				listJ.add(gagnantMajority3(p));
    				break;
    			case "Majority2" :
    				listJ.add(gagnantMajority2(p));
    				break;
    			case "LowestTrefle" : 
    				listJ.add(gagnantLowestTrefle(p));
    				break;
    			default: 
    				throw new GameException("condition du trophée inconnu");    		
    	}
    	return listJ;
    	
    }
    	
    	
  
    	
    	//IL FAUT GERER CA ?????
    	// attention dans le cas ou le joker est un des trophées et que l'autre trophées a une condition sur le joker 
    	// alors on choisit de ne pas donner le trophée avec la condition sur le joker ou alors le joueur concernait 
    	// (celui qui a le plus de point (la condition du joker) prends les DEUX cartes
    	
    	
    	
    	
    	
    	public Joueur gagnantLowestTrefle(Partie p) {
    		Joueur gagnant = new Joueur("tmp","tmp");
			int lowest = 5;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("trefle") && c.getValeur() < lowest) {
			            lowest = c.getValeur();
			            gagnant = j;
			}}}
			if (gagnant.getNom().equals("tmp")) {
				GameException e = new GameException("aucun joueur n'a de trèfle");
				throw e ;
			}
			else {
				return gagnant;
			}
    	}
    	
    	
    	public Joueur gagnantLowestPique(Partie p) {
    		Joueur gagnant = new Joueur("tmp","tmp");
			int lowest = 5;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("pique") && c.getValeur() < lowest) {
			            lowest = c.getValeur();
			            gagnant = j;
			}}}
			if (gagnant.getNom().equals("tmp")) {
				GameException e = new GameException("aucun joueur n'a de pique");
				throw e ;
			}
			else {
				return gagnant;
			}
    	}
    	
    	
    	public Joueur gagnantLowestCoeur(Partie p) {
    		Joueur gagnant = new Joueur("tmp","tmp");
			int lowest = 5;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("coeur") && c.getValeur() < lowest) {
			            lowest = c.getValeur();
			            gagnant = j;
			}}}
			if (gagnant.getNom().equals("tmp")) {
				GameException e = new GameException("aucun joueur n'a de coeur");
				throw e ;
			}
			else {
				return gagnant;
			}
    	}
    	
    	public Joueur gagnantLowestCarreau(Partie p) {
    		Joueur gagnant = new Joueur("tmp","tmp");
			int lowest = 5;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("carreau") && c.getValeur() < lowest) {
			            lowest = c.getValeur();
			            gagnant = j;
			}}}
			if (gagnant.getNom().equals("tmp")) {
				GameException e = new GameException("aucun joueur n'a de carreau");
				throw e ;
			}
			else {
				return gagnant;
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
			
			}
		}
	}
    
    
    
}
