package Modèle;

import java.io.Serializable;

public class VarianteSansTrophees implements Variante, Serializable {
    
    private String nom;
    private boolean utilise;
    private static final long serialVersionUID = 1L;

    public VarianteSansTrophees() {
        this.nom = "Partie Sans Trophees";
        this.utilise = false;
    }
    
    @Override
    public void estUtilise() {
        this.utilise = true;
    }
    
    @Override
    public Joueur appliquerVariante(Partie p) {
        if (utilise) {
            p.setTrophees(null);
            System.out.println("Variante appliquée : Pas de trophées.");
        }
        return null;
    }
    
    @Override
    public String toString() {
        return nom;
    }
}