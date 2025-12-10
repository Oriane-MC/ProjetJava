package Projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Variante {
	
	public void estUtilise();
	
	public Joueur appliquerVariante(Partie p ); //renvoie joueur pour variante = VariantePremierJoueurAleatoire et null pour variante = VarianteSansTrophees
	
	public String toString();
	

}
