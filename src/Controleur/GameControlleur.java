package Controleur;

import java.awt.event.*;

import javax.swing.JButton;

import Projet.Partie;

public class GameControlleur {
	
	
	private JButton unBouton;
	private Partie p;
	
	public GameControlleur(Partie p, JButton bouton) {
		this.unBouton = bouton;
		this.p = p;

	
		unBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.getJoueur();
			}
		});
	}
	
	
	
}
