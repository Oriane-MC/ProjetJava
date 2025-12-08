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

    
    
    public List<Joueur> determinerGagnant(Partie p) throws GameException {
    	
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
    				listJ.add(gagnantLowestPique(p));
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
    	}
    	return listJ;
    }
    	
    	public Joueur gagnantLowestTrefle(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int lowest = 5;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("trèfle") && c.getValeur() < lowest) {
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
    	

		public Joueur gagnantLowestPique(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
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
    	
    	public Joueur gagnantLowestCoeur(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
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
    	
    	public Joueur gagnantLowestCarreau(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
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
    	
    	public Joueur gagnantHighestTrefle(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int highest = 0;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("trèfle") && c.getValeur() > highest) {
			            highest = c.getValeur();
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
    	
    	public Joueur gagnantHighestPique(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int highest = 0;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("pique") && c.getValeur() > highest) {
			            highest = c.getValeur();
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
    	
    	public Joueur gagnantHighestCoeur(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int highest = 0;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("coeur") && c.getValeur() > highest) {
			            highest = c.getValeur();
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
    	
    	public Joueur gagnantHighestCarreau(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int highest = 0;
		
			for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			        if (c.getCouleur().equals("carreau") && c.getValeur() > highest) {
			            highest = c.getValeur();
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
    	
    	public Joueur gagnantMajority4(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int majority = 0;
		
			for (Joueur j : p.getJoueur()) {
				int nb_4 = 0;
			    for (Carte c : j.getDeckPossede().getCartes()) {
					if (c.getValeur() == 4) {
						nb_4 += 1;
					}
				}
				if (nb_4 > majority) {
					majority = nb_4;
					gagnant = j;
			    }
			}
    		if (majority == 0) {
    			GameException e = new GameException("personne n'a de 4");
    			throw e ;
    		}
    		else {
    			return gagnant;
    		}
    	}
    	
    	public Joueur gagnantMajority3(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int majority = 0;
		
			for (Joueur j : p.getJoueur()) {
				int nb_3 = 0;
			    for (Carte c : j.getDeckPossede().getCartes()) {
					if (c.getValeur() == 3) {
						nb_3 += 1;
					}
				}
				if (nb_3 > majority) {
					majority = nb_3;
					gagnant = j;
			    }
			}
    		if (majority == 0) {
    			GameException e = new GameException("personne n'a de 3");
    			throw e ;
    		}
    		else {
    			return gagnant;
    		}
    	}
    	
    	public Joueur gagnantMajority2(Partie p) throws GameException {
    		Joueur gagnant = new Joueur("tmp","tmp",null);
			int majority = 0;
		
			for (Joueur j : p.getJoueur()) {
				int nb_2 = 0;
			    for (Carte c : j.getDeckPossede().getCartes()) {
					if (c.getValeur() == 2) {
						nb_2 += 1;
					}
				}
				if (nb_2 > majority) {
					majority = nb_2;
					gagnant = j;
			    }
			}
    		if (majority == 0) {
    			GameException e = new GameException("personne n'a de 2");
    			throw e ;
    		}
    		else {
    			return gagnant;
    		}
    	}
    		
    	public Joueur gagnantJoker(Partie p) throws GameException {
    		for (Joueur j : p.getJoueur()) {
			    for (Carte c : j.getDeckPossede().getCartes()) {
			    	if (c.getCouleur().equals("joker")) {
			    		return j;
			    	}
			    }
    		}
			GameException e = new GameException("personne n'a de joker");
			throw e ;	
    	}
    		

		public Joueur gagnantBestJest (Partie p) throws GameException {
			 p.calculerScoreSansTrophees();
			 return p.joueurGagnant();
		}
		
	
		public Joueur gagnantBestJestNoJoker(Partie p) throws GameException {
			p.calculerScoreSansTrophees();
			Joueur gagnant = p.joueurGagnant();
			boolean joker = false;
			for (Carte c : gagnant.getDeckPossede().getCartes()) {
				if (c.getCouleur().equals("joker")) {
					joker = true;
				}
			}
			if (joker == false) {
				return gagnant;
			}
			else {
				int score_max = p.getScore().get(gagnant);
				int score_max_second = 0;
					
				Iterator<Joueur> it = p.getScore().keySet().iterator();
				while (it.hasNext()) {
					Joueur key = it.next();
					Integer valeur = p.getScore().get(key);
						if (valeur<score_max && valeur>=score_max_second) {
							gagnant = key;
							score_max_second = valeur;
						}
					}
				return gagnant;
				}
			} 
		
		
		public String toString() {
			return "Trophees = " + this.getTrophee1() + " / condition : "+ this.getTrophee1().getCondition()+ " et "
					+ this.getTrophee2() + " / condition : " + this.getTrophee2().getCondition();
		}
    
}
