package Modèle;

import java.io.Serializable;
import java.util.*;

public class PaquetCarte implements Serializable{
	
	private LinkedList<Carte> listPioche;
	private static final long serialVersionUID = 1L;

	public PaquetCarte(LinkedList<Carte> list) {
		this.listPioche = list;
	}
	
	public Carte piocher() {
		return this.listPioche.poll();
	}

	public LinkedList<Carte> getListPioche() {
		return listPioche;
	}

	public void setListPioche(LinkedList<Carte> listPioche) {
		this.listPioche = listPioche;
	}
	
	public void melanger() {
		Collections.shuffle(listPioche);
	}

	
	public String toString() {
		return "Pioche : [ " + listPioche + " ]"; 
	}
	
	

}
