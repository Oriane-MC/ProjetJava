package Projet;

import java.util.*;

public class Trophee {
	
	private LinkedList<Carte> listTrophee;
	
	public Trophee (Carte c1, Carte c2) {
		this.listTrophee = new LinkedList();
		this.listTrophee.add(c1);
		this.listTrophee.add(c2);
	}
	
	public Carte getTrophee1() {
		return this.listTrophee.get(0);
	}
	
	public Carte getTrophee2() {
		return this.listTrophee.get(1);
	}
	
	public LinkedList<Carte> getListTrophee(){
		return this.listTrophee;
	}
}
