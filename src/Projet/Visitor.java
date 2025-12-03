package Projet;

import java.util.Map;

public interface  Visitor {
	// patron visitor : il va venir visiter chaque element et compter les point pour chacun des elements 

	public int visit(Joueur j) throws GameException;
	public Map<Joueur, Integer> visit(Partie p) throws GameException;
	public int visit(Carte c) throws GameException;
}
