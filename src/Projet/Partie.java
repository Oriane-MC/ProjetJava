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
		
		// il faut fixé les deck des joeurs au debut du calcul : on ne prend pas en compte l'effet de l'obtention d'un 
		// trophées sur la determination de l'obtention du 2eme trophées 
		
		
		// attention dans le cas ou le joker est un des trophées et que l'autre trophées a une condition sur le joker 
		// alors on choisit de ne pas donner le trophée avec la condition sur le joker ou alors le joueur concernait 
		// (celui qui a le plus de point (la condition du joker) prends les DEUX cartes

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
				
		Joueur j1 = new Joueur("j1","reel"); // je ne sais plus comment on fait pour récupérer une valeur d'une énumeration 
		Joueur j2 = new Joueur("j2","reel");
		
		Virtuel j3 = new Virtuel("j3", new StrategieBasique());
		
		
		
				
		p.mélanger();
		p.répartir();
	
		//chaque joueur crée son offre 
		
		//chaque joueur prend une offre 
		
		//chaque joueur ajoute a son deck 
		
		//distribuer les trophées 
		
		//calculer les points 
		
		
		
		System.out.println(p);
		
		
	}
}
