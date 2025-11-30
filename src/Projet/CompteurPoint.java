package Projet;

import java.util.*;

public class CompteurPoint implements Visitor {
	

	
	/**
	 * méthode qui permet de calculer le score/les points d'un joueur 
	 */
	public int visiter (Joueur j) {
		
		int score = 0;
		List<Integer> listCoeur = new ArrayList();
		List<Integer> listPique = new ArrayList();
		List<Integer> listCarreau = new ArrayList();
		List<Integer> listTrefle = new ArrayList();
		boolean joker = false ;
		
		
		// on récupère les cartes du deck/jest du joueur en les triant par couleur
		for (Carte c : j.getDeckPossede().getCartes()) { 
			if ( c.getCouleur() == "coeur") {
				listCoeur.add(c.getValeur());
			}
			else if (c.getCouleur() == "pique") {
				listPique.add(c.getValeur());
			}
			else if (c.getCouleur() == "carreau") {
				listCarreau.add(c.getValeur());
			}
			else if (c.getCouleur() == "trefle") {
				listTrefle.add(c.getValeur());
			}
			else if (c.getCouleur() == "joker") {
				joker = true;
			}
			else {
				// il faut gérer une exception ici parce que pb sinon 
			}
		}
		
		
			
		//les cartes noirs, piques et trèfles, valent leurs valeurs nominales en positif, 
		//si As est la seule carte de sa couleur -> alors il vaut 5 en positif ou négatif selon la couleur
		if (listPique.size()==1) {
			if (listPique.get(0) == 1) {
				score += 5;
			}
		}
		else {
			for (int elt : listPique) {
				score +=elt;
			}
		}		
		if (listTrefle.size()==1) {
			if (listTrefle.get(0) == 1) {
				score += 5;
			}
		}
		else {
			for (int elt : listTrefle) {
				score +=elt;
			}
		}
		
		
		//les carreaux valent leurs valeurs nominales en négatifs, 
		if (listCarreau.size()==1) {
			if (listCarreau.get(0) == 1) {
				score += 5;
			}
		}
		else {
			for (int elt : listCarreau) {
				score +=elt;
			}
		}
		
		
		//les coeurs sont traités différemment 
		if (listCoeur.size()==1) {
			if (listCoeur.get(0) == 1) {
				score += 5;
			}
		}
		// Si joker et aucun cœur -> bonus de 4 points
		if (joker == true && listCoeur.size() == 0) {
			score += 4;
		}
				
		//Si joker et 1,2 ou 3 coeurs -> alors les coeurs valent leurs valeurs nominales en négatif
		else if (joker == true && listCoeur.size() < 4) {
			for (int elt : listCoeur ){
				score -= elt;
			}
		}
				
		//Si joker et 4 coeurs -> alors les coeurs valent leurs valeurs nominales en positif
		else if (joker == true && listCoeur.size()== 4) {
			for (int elt : listCoeur ){
				score += elt;
			}
		}
			
		//si un cœur et un trèfle ayant la même valeur -> alors +2 points pour chaque paires
		for (int elt : listCoeur) {
			for (int elt1 : listTrefle) {
				if (elt == elt1) {
					score += 2;
				}
			}
		}
		
		if (score < 0 ){
			return 0;
		}
		else {
			return score;
		}
	}
}
