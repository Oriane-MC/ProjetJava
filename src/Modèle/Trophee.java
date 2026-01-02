package Modèle;

import java.io.Serializable;
import java.util.*;

/**
 * Représente les trophées d'une partie composé de deux cartes avec leurs conditions.
 * 
 * Chaque trophée définit des règles permettant de déterminer un gagnant
 * à partir des cartes appartenant aux joueurs.
 */
public class Trophee implements Serializable {
	
	/**
     * Liste contenant les deux cartes trophées.
     */
	private LinkedList<Carte> listTrophee;
	
	/**
     * Identifiant de sérialisation.
     */
	private static final long serialVersionUID = 1L;

	/**
     * Construit un trophée à partir de deux cartes.
     *
     * @param c1 première carte trophée
     * @param c2 seconde carte trophée
     */
	public Trophee (Carte c1, Carte c2) {
		this.listTrophee = new LinkedList();
		this.listTrophee.add(c1);
		this.listTrophee.add(c2);
	}
	
	/**
     * Retourne la première carte trophée.
     *
     * @return la première carte trophée
     */
	public Carte getTrophee1() {
		return this.listTrophee.get(0);
	}
	
	/**
     * Retourne la seconde carte trophée.
     *
     * @return la seconde carte trophée
     */
	public Carte getTrophee2() {
		return this.listTrophee.get(1);
	}
	
	/**
     * Retourne une carte trophée selon son indice (1 ou 2).
     *
     * @param i indice du trophée (1 ou 2)
     * @return la carte correspondante ou null si l'indice est invalide
     */
	public Carte getTrophee(int i) {
		if (i == 1 || i == 2) {
			return this.listTrophee.get(i-1);
		}
		return null;
	}
	
	/**
     * Retourne la liste des cartes trophées.
     *
     * @return la liste des trophées
     */
	public LinkedList<Carte> getListTrophee(){
		return this.listTrophee;
	}
	
	/**
	 * Modifie la liste des cartes trophées.
	 *
	 * @param listTrophee nouvelle liste des trophées
	 */
	public void setListTrophee(LinkedList<Carte> listTrophee) {
		this.listTrophee = listTrophee;
	}

	/**
     * Détermine les gagnants associés aux trophées de la partie.
     *
     * @param p la partie en cours
     * @return la liste des joueurs gagnants
     * @throws GameException si une condition de trophée est inconnue
     */
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
    			case "LowestPique" : 
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
    	
    /**
     * Détermine le joueur ayant le trèfle de plus faible valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de trèfle
     */
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
	
    /**
     * Détermine le joueur ayant le pique de plus faible valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de pique
     */
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
	
	/**
     * Détermine le joueur ayant le cœur de plus faible valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de cœur
     */
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
	
	/**
     * Détermine le joueur ayant le carreau de plus faible valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de carreau
     */
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
	
	/**
     * Détermine le joueur ayant le trèfle de plus forte valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de trèfle
     */
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
	
	/**
     * Détermine le joueur ayant le pique de plus forte valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de pique
     */
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
	
	/**
     * Détermine le joueur ayant le cœur de plus forte valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de cœur
     */
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
	
	/**
     * Détermine le joueur ayant le carreau de plus forte valeur.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de carreau
     */
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
	
	/**
     * Détermine le joueur ayant la majorité de cartes de valeur 4.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de 4
     */
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
	
	/**
     * Détermine le joueur ayant la majorité de cartes de valeur 3.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de 3
     */
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
	
	/**
     * Détermine le joueur ayant la majorité de cartes de valeur 2.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de 2
     */
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
		
	/**
     * Détermine le joueur possédant le joker.
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     * @throws GameException si aucun joueur ne possède de joker
     */
	public Joueur gagnantJoker(Partie p) throws GameException {
		
		//si le deuxième trophées est le joker ca pose problème lors de la vérification du premier trophées : 
		if (p.getTrophees().getTrophee2().getCouleur().equals("joker")) {
			return new Joueur("tmp","tmp",p);
		}
		
		//si le joker est dans la pioche ca pose problème aussi :
		for (Carte c : p.getPioche().getListPioche()) {
			if (c.getCouleur().equals("joker")){
				return new Joueur("personne","tmp",p);
			}
		}
		
				
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
		
	/**
     * Détermine le joueur ayant le meilleur score global (Best Jest).
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     */
	public Joueur gagnantBestJest (Partie p) throws GameException {
		 p.calculerScoreSansTrophees();
		 return p.joueurGagnant();
	}
	
	/**
     * Détermine le joueur ayant le meilleur score global sans avoir le joker
     *
     * @param p la partie en cours
     * @return le joueur gagnant
     */
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
	
	/**
     * Retourne une description textuelle du trophée.
     *
     * @return description du trophée
     */
	public String toString() {
		return "Trophees = " + this.getTrophee1() + " / condition : "+ this.getTrophee1().getCondition()+ " et "
				+ this.getTrophee2() + " / condition : " + this.getTrophee2().getCondition();
	}

}
