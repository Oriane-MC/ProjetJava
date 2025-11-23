package Projet;

import java.util.*;

public class Partie {
	
	private ArrayList<Joueur> listJoueur;
	private LinkedList<Carte> listCarte;
	private String etatPartie;
	private int variantes;
	private Trophee Trophees;
	private Map<Joueur, Integer> Score = new HashMap();
	
	
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
	
	public void calculerScore() {

		for (Carte t : this.Trophees.getListTrophee() ) {
			
			if (t.getCondition() == "Lowest") {
				
			}
			else if (t.getCondition() == "Highest") {
				
			}
			else if (t.getCondition() == "Majority") {
				
			}
			else if (t.getCondition() == "Joker") {
				
			}
			else if (t.getCondition() == "BestJest"){    // dans le cas ou on a besoin de calculer les points avant de donner les trophees 
				// calculer les scores de chaque joueur et les enregistrer 
				//les comparer 
				// attribuer la carte au meilleur joueur
				// recalculer le score de celui qui a changé de Jest 
				// return pour ne pas recalculer 
			}
			else {
				// calculer les scores de chaque joueur et les enregistrer en excluant celui qui a le joker 
				// les comparer 
				// attribuer la carte au meilleur joueur
				// recalculer le score de celui qui a changé de Jest 
				// return pour ne pas recalculer 
			}
			// calculer les scores pour chaque joueur
		}
	}
	
	
	
	public String toString() {
		return "Partie [etatPartie= " + etatPartie + " ; Joueur = " + listJoueur + " ; Paquet Carte" + listCarte +  
				"; variantes=" + variantes + "]";
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
