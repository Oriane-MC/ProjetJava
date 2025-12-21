package Modèle;

import java.io.Serializable;
import java.util.*;

public class CompteurPoint implements Visitor,  Serializable {
	
	private static final long serialVersionUID = 1L;

	public int visit(Joueur j) throws GameException {
		int score = 0;
			
		for (Carte c : j.getDeckPossede().getCartes()) { 
			//bonus si As est la seule carte de sa couleur 
			if (c.getValeur() == 1) {
				score += this.scoreAs(c,j.getDeckPossede());
			}
			else {
				score += c.accept(this);
			}
		}
		
		// les coeurs sont traités différemment
		score += this.scoreCoeur(j.getDeckPossede() );
		
		//bonus si paire de trefle et coeur
		score += this.scorePairePiqueTrefle(j.getDeckPossede());
					
		// le score ne peut pas être inférieur à 0
		if (score < 0 ){
			return 0;
		}
		else {
			return score;
		}
	}
	
	public int visit(Carte c) throws GameException {
		//les cartes piques et trèfles, valent leurs valeurs nominales en positif, 
		if (c.getCouleur().equals("pique") || c.getCouleur().equals("trèfle") ) {
			return c.getValeur();			
		}
		//les carreaux valent leurs valeurs nominales en négatifs, 
		else if (c.getCouleur().equals("carreau")) {
			return (-c.getValeur());
		}
		//les coeurs sont traités différement
		else if (c.getCouleur().equals("coeur")) {
			return 0;
		}
		//les joker ne valent rien 
		else if (c.getCouleur().equals("joker")) {
			return 0;
		}
		else {
			GameException e = new GameException("la couleur de la carte ne respecte pas les normes");
			throw e;
		}
	}
		
	
	public Map<Joueur, Integer> visit(Partie p) throws GameException {
		
		if (!(p.getVariante() instanceof VarianteSansTrophees)) {

	        List<Joueur> gagnants = p.getTrophees().determinerGagnant(p);

	        Joueur j0 = gagnants.get(0);
	        Joueur j1 = gagnants.get(1);

	      //si nom du joueur = "tmp" alors un seul joueur prends tout
	        if (j0.getNom().equals("tmp")) {
	            // j1 prend tout
	            j1.ajouterCarteDeck(p.getTrophees().getTrophee(1));
	            j1.ajouterCarteDeck(p.getTrophees().getTrophee(2));

	        } else if (j1.getNom().equals("tmp")) {
	            // j0 prend tout
	            j0.ajouterCarteDeck(p.getTrophees().getTrophee(1));
	            j0.ajouterCarteDeck(p.getTrophees().getTrophee(2));

	        }
	        else {
	            // Attribution normale
	            for (int i = 0; i < 2; i++) {
	                Joueur j = gagnants.get(i);

	              //si nom du joueur = "personne" alors le trophées ne va à personnne 
	                if (j.getNom().equals("personne")) {
	                    continue;
	                }

	                j.ajouterCarteDeck(p.getTrophees().getTrophee(i+1));
	            }
	        }
	    }
			
		
		//calculer score pour chaque joueur
		Map<Joueur, Integer> score = new HashMap();
		for (Joueur j : p.getJoueur()) {
			score.put(j,j.accept(new CompteurPoint()));
		}
		
		return score;
	}
	
			
	public int scoreCoeur(Deck deck) {
		
		List<Integer> listCoeur = new ArrayList();
		boolean joker = false ;
		int score = 0;
		
		for (Carte c : deck.getCartes() ) {
			if ( c.getCouleur().equals("coeur")) {
				listCoeur.add(c.getValeur());
			}
			if (c.getCouleur().equals("joker")) {
				joker = true;
		}}
		
		// Si joker et aucun cœur -> bonus de 4 points
		if (joker == true && listCoeur.size() == 0) {
			score += 4;
		}
		
		//Si joker et 4 coeurs -> alors les coeurs valent leurs valeurs nominales en positif
		else if (joker == true && listCoeur.size()== 4) {
			for (int elt : listCoeur ){
				score += elt;
		}}
		
		//Si joker et 1,2 ou 3 coeurs -> alors les coeurs valent leurs valeurs nominales en négatif
		else if (joker == true && listCoeur.size() < 4) {
			for (int elt : listCoeur ){
				score -= elt;
			}}
		return score;	
	}
	
	
	//si un cœur et un trèfle ayant la même valeur -> alors +2 points pour chaque paires
	public int scorePairePiqueTrefle(Deck deck) {
		
		int score = 0;
		List<Integer> listPique = new ArrayList();
		List<Integer> listTrefle = new ArrayList();
		for (Carte c : deck.getCartes() ) {
			if ( c.getCouleur().equals("pique")) {
				listPique.add(c.getValeur());
			}
			if ( c.getCouleur().equals("trèfle")) {
				listTrefle.add(c.getValeur());
		}}
		for (int elt : listPique) {
			for (int elt1 : listTrefle) {
				if (elt == elt1) {
					 score += 2;
		}}}
		return score;
	}	
	
	//si As est la seule carte de sa couleur -> alors il vaut 5 en positif ou négatif selon la couleur 	
	public int scoreAs(Carte c,Deck d) throws GameException {
	
		// on parcourt la liste et si on rencontre une carte de la même couleur que l'As qui n'est pas l'As 
		// le programme s'arrete et retourne 0 sinon 5 pour pique et trefles et -5 sinon
		for (Carte verif : d.getCartes() ) {
			if (c.getCouleur().equals(verif.getCouleur())) {
				if (verif.getValeur() != 1) {
					return 0;
		}}}
		if (c.getCouleur().equals("pique") || c.getCouleur().equals("trèfle")) {
			return 5;
		}
		else if (c.getCouleur().equals("carreau") || c.getCouleur().equals("coeur")) {
			return (-5);
		}
		else {
			GameException e = new GameException("la couleur de la carte ne respecte pas les normes");
			throw e;
		}
	}

}

