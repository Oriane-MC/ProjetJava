package projetJest;

import java.util.*;

public class PaquetCarte {
	
	private LinkedList<Carte> listPioche;
	
	
	public PaquetCarte(LinkedList<Carte> list) {
		this.listPioche = list;
	}
	
	public Carte piocher() {
		return this.listPioche.poll();
	}
	
	
	

}
