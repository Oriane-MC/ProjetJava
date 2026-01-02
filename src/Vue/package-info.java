/**
 * Package Vue
 * 
 * Contient toutes les classes liées à l'interface utilisateur du jeu Jest.
 * Ce package gère à la fois l'affichage graphique (Swing) et la console,
 * ainsi que la mise à jour des informations en fonction de l'état du modèle.
 * 
 * Classes principales :
 * - VueGraphique : interface graphique principale, affiche les joueurs, les cartes,
 *   le plateau de jeu et les scores. Gère les interactions via boutons et menus.
 * - VueConsole : interface en console permettant de jouer et de suivre la partie
 *   sans interface graphique. Exécute les interactions dans un thread séparé.
 * 
 * Rôle général :
 * Le package Vue est responsable de la présentation des informations aux joueurs
 * et de la réception de leurs actions, tout en restant découplé de la logique
 * métier du modèle. Les vues observent le modèle et se mettent à jour automatiquement.
 */
package Vue;
