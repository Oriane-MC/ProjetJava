package Projet;

import java.util.*;

public class PaquetCarte {
	
	private LinkedList<Carte> listPioche;
	
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
