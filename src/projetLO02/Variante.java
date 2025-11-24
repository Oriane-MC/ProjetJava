package projetLO02;

public class Variante {

	private String nom; //est que ce serait pas mieux une énumération puisque c'est 
	//nous qui choissisons les variantes (je crois) 
	private boolean utilise;
	
	public Variante(String nom) {
		this.nom = nom;
		this.utilise = false;
	}
	
	public void estUtilise() {
		this.utilise = true;
	}
	
	
	public void modifJoker() {
		// jsp, comment ca va fonctionné ? ...
	}
	
	
	
	
}
