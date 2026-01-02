/**
 * Package Modèle
 * 
 * Contient toutes les classes représentant le modèle du jeu Jest.
 * Ces classes gèrent la logique métier et les données du jeu.
 * 
 * Classes principales :
 * - Carte : représente une carte du jeu.
 * - Deck : représente le jeu de cartes possédé par un joueur.
 * - Joueur / Virtuel : représente un joueur humain ou IA.
 * - Offre : représente une offre de cartes proposée par un joueur.
 * - PaquetCarte : représente un paquet de cartes utilisé pour la pioche.
 * - Partie : gère l’état global d’une partie.
 * - TypeCarte / CarteVariante : énumération des types de cartes du jeu.
 * - Variante, VariantePremierJoueurAleatoire, VarianteSansTrophees : gestion des règles optionnelles.
 * - Visitor : interface pour le calcul des points ou le traitement des éléments du jeu.
 * - CompteurPoint : classe utilitaire pour compter et gérer les points des joueurs.
 * - Observateur : interface pour le pattern Observer, permet aux vues d’être notifiées.
 * - GameException : exception spécifique au moteur de jeu pour gérer les erreurs.
 * - Strategie, StrategieBasique, StratégieDéfensive, StrategiesAggressive : classes représentant les stratégies des joueurs virtuels.
 * - Trophee : représente les trophées disponibles et leur attribution.
 * 
 * Rôle général :
 * Le modèle est responsable des règles du jeu, du suivi des joueurs, des cartes et des scores.
 */
package Modèle;

