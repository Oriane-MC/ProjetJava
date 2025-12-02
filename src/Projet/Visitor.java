package Projet;

public interface  Visitor {
	// patron visitor : il va venir visiter chaque element et compter les point pour chacun des elements 

	public int visit(Joueur j);
	public int visit(Partie p);
	public int visit(Carte c);
	public int visit(Trophee t);
}
