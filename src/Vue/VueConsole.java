package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;

import Projet.*;
import Modèle.Observateur; // DINA
import Modèle.Partie;// DINA
//public class VueConsole implements Observer, Runnable {
	
public class VueConsole implements Observateur {
    @Override
    public void update(Partie p) {
        System.out.println("\n[CONSOLE] " + p.getDernierMessage());
    }

}
