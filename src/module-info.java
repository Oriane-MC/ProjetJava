/**
 * Projet Java - Jest Game
 * 
 * Ce projet implémente le jeu de cartes Jest avec une interface graphique.
 * Il est conçu selon le modèle MVC et utilise plusieurs patrons de
 * conception comme Observer, Strategy et Visitor pour structurer la logique
 * du jeu, gérer les interactions et les stratégies des joueurs.
 */
module ProjetJava {
    requires java.desktop;
    exports Modèle;
    exports Controleur;
    exports Vue;
}

