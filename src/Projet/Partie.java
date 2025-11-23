package Projet;

import java.util.*;

public class Partie {
	
	//private ArrayList<Joueur> listJoueur;
	private LinkedList<Carte> listCarte;
	//private List[Score] listScore;
	private String etatPartie;
	private int variantes;
	
	
	public Partie() {
		//this.listJoueur = new ArrayList();
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
		new Trophee(c1,c2);
		new PaquetCarte(listCarte);
	}
	
	//public void ajouterJoueur(Joueur j) {
	//	this.listJoueur.add(j);
	//}
	
	
	
	public String toString() {
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = ; Paquet Carte" + listCarte +  
				"; variantes=" + variantes + "]";
		//" + listJoueur + " 
	}


	public static void main (String[] args) {
		// 1- demander si variantes ou pas 
		// 2- demander si extension ou pas
		Partie p = new Partie();
		
		//ajouter les joueurs
		
		p.mélanger();
		p.répartir();
		
		System.out.println(p);
		
		// chaque joueur crée leurs offre 
		
	}
}
