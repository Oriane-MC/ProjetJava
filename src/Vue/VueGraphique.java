package Vue;

import javax.swing.*;
import java.awt.*;
import Modèle.*;
import java.util.Map;
import java.util.List;

/**
 * Interface graphique principale du jeu Jest.
 * 
 * Cette classe implémente Observateur pour être notifiée des changements
 * dans le modèle Partie. Elle utilise Swing pour afficher les écrans
 * de configuration, de jeu et de résultats.
 */
public class VueGraphique extends JFrame implements Observateur {
	
    /** Référence à la Partie observé. */
    private Partie modele;

    // Composants de Saisie
    /**
     * Zone de saisie du nom du joueur à ajouter.
     */
    private JTextField fieldNom = new JTextField(15);

    /**
     * ComboBox pour choisir le type de joueur : "Humain" ou "Virtuel".
     */
    private JComboBox<String> comboType = new JComboBox<>(new String[]{"Humain", "Virtuel"});

    /**
     * ComboBox pour sélectionner la stratégie d'un joueur virtuel : 
     * "Aléatoire", "Basique", "Défensive" ou "Aggressive".
     */
    private JComboBox<String> comboStrat = new JComboBox<>(new String[]{"Aléatoire", "Basique", "Défensive", "Aggressive"});

    /**
     * Checkbox permettant d'activer l'extension, qui ajoute les cartes 6 et 7 au jeu.
     */
    private JCheckBox checkExtension = new JCheckBox("Activer l'extension (6 et 7)");

    /**
     * RadioButton pour sélectionner la variante "Premier Joueur Aléatoire".
     */
    private JRadioButton rbV1 = new JRadioButton("1 - Premier Aléatoire");

    /**
     * RadioButton pour sélectionner la variante "Sans Trophées".
     */
    private JRadioButton rbV2 = new JRadioButton("2 - Sans Trophées");

    /**
     * RadioButton pour ne sélectionner aucune variante (par défaut sélectionné).
     */
    private JRadioButton rbV3 = new JRadioButton("3 - Aucune", true);

    /**
     * Zone de texte affichant le récapitulatif des joueurs ajoutés.
     */
    private JTextArea areaRecap = new JTextArea(8, 30);

    /**
     * Label affichant le statut général ou les messages de la partie.
     */
    private JLabel labelMsg = new JLabel("Statut : En attente de joueurs...");

    /**
     * Constructeur principal.
     * Initialise la fenêtre, configure les panneaux et rend la fenêtre visible.
     * @param p la partie à observer et gérer graphiquement
     */
    public VueGraphique(Partie p) {
        this.modele = p;
        setTitle("Jest Game - Configuration MVC");
        setSize(850, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initialiserEcranConfiguration();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialise l'écran de configuration de la partie.
     * Permet de saisir les joueurs, choisir la stratégie IA, et configurer les variantes.
     */
    private void initialiserEcranConfiguration() {
        getContentPane().removeAll();
        JPanel panelNord = new JPanel();
        panelNord.setLayout(new BoxLayout(panelNord, BoxLayout.Y_AXIS));

        JPanel pOptions = new JPanel(new GridLayout(0, 1));
        pOptions.setBorder(BorderFactory.createTitledBorder("Règles de la partie"));
        pOptions.add(checkExtension);
        ButtonGroup group = new ButtonGroup();
        group.add(rbV1); group.add(rbV2); group.add(rbV3);
        JPanel pVar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pVar.add(new JLabel("Variante :"));
        pVar.add(rbV1); pVar.add(rbV2); pVar.add(rbV3);
        pOptions.add(pVar);

        JPanel pSaisie = new JPanel(new GridLayout(4, 2, 5, 5));
        pSaisie.setBorder(BorderFactory.createTitledBorder("Ajouter un Joueur"));
        pSaisie.add(new JLabel(" Nom :")); pSaisie.add(fieldNom);
        pSaisie.add(new JLabel(" Type :")); pSaisie.add(comboType);
        pSaisie.add(new JLabel(" Stratégie :")); pSaisie.add(comboStrat);
        comboStrat.setEnabled(false); 
        JButton btnAdd = new JButton("Ajouter le joueur");
        pSaisie.add(new JLabel()); pSaisie.add(btnAdd);

        panelNord.add(pOptions);
        panelNord.add(pSaisie);

        areaRecap.setEditable(false);
        JPanel pCentral = new JPanel(new BorderLayout());
        pCentral.setBorder(BorderFactory.createTitledBorder("Liste des joueurs"));
        pCentral.add(new JScrollPane(areaRecap), BorderLayout.CENTER);

        JPanel pSud = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnLancer = new JButton("LANCER LA PARTIE");
        btnLancer.setBackground(new Color(39, 174, 96));
        btnLancer.setForeground(Color.WHITE);
        
        JButton btnCharger = new JButton("CHARGER PARTIE");
        btnCharger.setBackground(new Color(41, 128, 185));
        btnCharger.setForeground(Color.WHITE);

        pSud.add(btnLancer);
        pSud.add(btnCharger);

        add(panelNord, BorderLayout.NORTH);
        add(pCentral, BorderLayout.CENTER);
        add(new JPanel(new BorderLayout()){{
            add(pSud, BorderLayout.NORTH);
            add(labelMsg, BorderLayout.SOUTH);
        }}, BorderLayout.SOUTH);

        comboType.addActionListener(e -> comboStrat.setEnabled(comboType.getSelectedItem().equals("Virtuel")));
        btnAdd.addActionListener(e -> {
            if(!fieldNom.getText().trim().isEmpty()){
                modele.ajouterJoueur(fieldNom.getText(), (String)comboType.getSelectedItem(), comboStrat.getSelectedIndex());
                fieldNom.setText("");
            }
        });
        btnLancer.addActionListener(e -> {
            int var = rbV1.isSelected() ? 1 : (rbV2.isSelected() ? 2 : 3);
            modele.configurerOptions(checkExtension.isSelected(), var);
            modele.lancerPartie();
        });
        
        btnCharger.addActionListener(e -> chargerPartie());       
        
    }
    
    /**
     * Charge une partie sauvegardée depuis un fichier.
     * Met à jour le modèle, réenregistre la vue comme observateur, et rafraîchit l'affichage.
     */
    private void chargerPartie() {
        // 1. Charger la partie depuis le fichier
        Partie partieChargee = Partie.charger("partie_jest.ser");
        
        if (partieChargee == null) {
            JOptionPane.showMessageDialog(this,
                "Impossible de charger la partie.\nLe fichier est introuvable ou corrompu.",
                "Erreur de chargement",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2️. Remplacer l'ancien modèle par le nouveau
        this.modele = partieChargee;

        // 3️. Ré-enregistrer cette vue comme observateur
        this.modele.enregistrerObservateur(this);

        // 4️. Réinitialiser le jeu après chargement
        if (this.modele != null) {
            try {
                this.modele.relancerLeJeuApresChargement();
            } catch (Exception ex) {
                System.err.println("Avertissement : relancerLeJeuApresChargement() a échoué");
                ex.printStackTrace();
            }
        }


        // 5️. Forcer la mise à jour de l'interface
        this.modele.notifier();

        // 6️. Message de confirmation
        String message = "Partie chargée avec succès !";
        if (modele.getJoueurActuel() != null) {
            message += "\n\nC'est au tour de : " + modele.getJoueurActuel().getNom();
            message += "\nTour n°" + modele.getNumeroTour();
        }
        
        JOptionPane.showMessageDialog(this, 
            message,
            "Chargement réussi",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mise à jour de la vue graphique suite à un changement du modèle.
     * @param p la partie mise à jour
     */
    public void update(Partie p) {
        SwingUtilities.invokeLater(() -> {
            if (p.getEtat() == Partie.EtatPartie.INITIALISATION) {
                labelMsg.setText("Statut : " + p.getDernierMessage());
                StringBuilder sb = new StringBuilder();
                for (Joueur j : p.getJoueur()) {
                    sb.append("• ").append(j.getNom()).append(" (").append(j.getTypeJoueur());
                    if (j instanceof Virtuel) sb.append(" - ").append(((Virtuel) j).getStrategie().toString());
                    sb.append(")\n");
                }
                areaRecap.setText(sb.toString());
            } else if (p.getEtat() == Partie.EtatPartie.FIN) {
                afficherEcranResultats();
            } else {
                afficherPlateauDeJeu();
            }
        });
    }

    /**
     * Crée un JPanel représentant un joueur et son état actuel sur le plateau.
     * 
     * Le panneau inclut :
     * - Le nom du joueur et son type (Humain ou Virtuel)
     * - Le nombre de cartes dans son "Jest"
     * - Les boutons pour sélectionner les cartes visibles ou cachées si c'est le tour d'un humain
     * - Une coloration différente si le joueur est actif ou si son offre est déjà prise
     *
     * @param j le joueur à représenter
     * @return un JPanel contenant les informations et actions possibles pour ce joueur
     */
    private JPanel creerPanelJoueur(Joueur j) {
        JPanel p = new JPanel(new BorderLayout());
        boolean estActif = (j == modele.getJoueurActuel());
        Offre offre = j.getOffre();
        p.setBorder(BorderFactory.createLineBorder(estActif ? Color.YELLOW : Color.GRAY, estActif ? 4 : 1));
        p.setBackground((offre != null && !offre.estDisponible()) ? new Color(100, 100, 100) : (estActif ? new Color(255, 255, 220) : Color.WHITE));

        String labelType = (j instanceof Virtuel) ? " [IA]" : "";
        JLabel nom = new JLabel(j.getNom().toUpperCase() + labelType + " | Jest: " + j.getJest().size(), SwingConstants.CENTER);
        nom.setFont(new Font("Arial", estActif ? Font.BOLD : Font.PLAIN, 13));
        if (offre != null && !offre.estDisponible()) nom.setForeground(Color.WHITE);
        p.add(nom, BorderLayout.NORTH);

        JPanel zoneCartes = new JPanel(new FlowLayout());
        zoneCartes.setOpaque(false);

        if (modele.getEtat() == Partie.EtatPartie.OFFRES && estActif && !(j instanceof Virtuel)) {
            List<Carte> attente = modele.getCartesEnAttente();
            if (attente != null) {
                for (Carte c : attente) {
                    if (c != null) {
                        JButton btn = new JButton(c.toString());
                        btn.addActionListener(e -> modele.validerChoixOffre(c));
                        zoneCartes.add(btn);
                    }
                }
            }
        } else if (offre != null) {
            if (offre.estDisponible()) {
                JButton btnV = new JButton(offre.getCarteVisible().toString());
                JButton btnC = new JButton("Cachée");
                boolean peutRamasser = (modele.getEtat() == Partie.EtatPartie.RAMASSAGE && modele.getJoueurActuel() != null && !(modele.getJoueurActuel() instanceof Virtuel));
                if (peutRamasser) {
                    if (modele.getJoueurActuel() == j) {
                        // On ne peut prendre chez soi que si c'est la seule offre
                        boolean resteAutre = false;
                        for (Joueur a : modele.getJoueur()) if (a != j && a.getOffre().estDisponible()) resteAutre = true;
                        if (resteAutre) peutRamasser = false;
                    }
                } else peutRamasser = false;

                btnV.setEnabled(peutRamasser); btnC.setEnabled(peutRamasser);
                btnV.addActionListener(e -> modele.ramasserAction(j, true));
                btnC.addActionListener(e -> modele.ramasserAction(j, false));
                zoneCartes.add(btnV); zoneCartes.add(btnC);
            } else {
                JLabel info = new JLabel("OFFRE DÉJÀ PRISE");
                info.setForeground(Color.LIGHT_GRAY);
                zoneCartes.add(info);
            }
        }
        p.add(zoneCartes, BorderLayout.CENTER);
        return p;
    }

    /**
     * Affiche le plateau de jeu complet dans la fenêtre graphique.
     * 
     * Cette méthode :
     * - Réinitialise la zone centrale
     * - Affiche les informations du tour en cours
     * - Affiche chaque joueur via {@link #creerPanelJoueur(Joueur)}
     * - Inclut un bouton pour sauvegarder manuellement la partie
     * - Affiche les trophées actuels
     */
    private void afficherPlateauDeJeu() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        JPanel panelInfos = new JPanel(new BorderLayout());
        panelInfos.setBackground(new Color(44, 62, 80));
        JLabel labelTitre = new JLabel("  JEST - Tour n°" + modele.getNumeroTour(), SwingConstants.LEFT);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitre.setForeground(Color.WHITE);
        panelInfos.add(labelTitre, BorderLayout.WEST);

        // Bouton de sauvegarde manuelle
        JButton btnSave = new JButton("💾 SAUVEGARDER");
        btnSave.addActionListener(e -> {
            Partie.sauvegarder(modele, "partie_jest.ser");
            JOptionPane.showMessageDialog(this, "Partie sauvegardée !");
        });
        panelInfos.add(btnSave, BorderLayout.EAST);

        JPanel tapisDeJeu = new JPanel(new GridLayout(2, 2, 15, 15));
        tapisDeJeu.setBackground(new Color(27, 94, 32)); 
        tapisDeJeu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Joueur j : modele.getJoueur()) tapisDeJeu.add(creerPanelJoueur(j));

        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBas.setPreferredSize(new Dimension(getWidth(), 80)); 
        
        labelMsg.setText(" Action : " + modele.getDernierMessage());
        labelMsg.setFont(new Font("Arial", Font.ITALIC, 13));
        panelBas.add(labelMsg, BorderLayout.CENTER);
        
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
        
        JPanel panelTrophee = new JPanel();
        panelTrophee.setBackground(new Color(44, 62, 80));
        panelTrophee.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); 


        JLabel labelTrophee = new JLabel("Trophée : " + (modele.getTrophees() != null ? modele.getTrophees() : "Aucun"));
        labelTrophee.setForeground(Color.WHITE);
        labelTrophee.setFont(new Font("SansSerif", Font.ITALIC, 14));
        
        panelTrophee.add(labelTrophee);

        
        panelCentre.add(panelTrophee);
        panelCentre.add(tapisDeJeu);
        
        add(panelInfos, BorderLayout.NORTH);
        add(panelCentre, BorderLayout.CENTER);
        add(panelBas, BorderLayout.SOUTH);

        revalidate(); repaint();
    }

    /**
     * Affiche l'écran final de la partie avec le classement des joueurs.
     *
     * La méthode :
     * - Réinitialise la fenêtre centrale
     * - Affiche le joueur gagnant, ou indique s'il n'y a pas de gagnant
     * - Affiche les scores finaux détaillés pour chaque joueur
     * - Ajoute un bouton pour quitter le jeu
     */
    private void afficherEcranResultats() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel pFin = new JPanel();
        pFin.setLayout(new BoxLayout(pFin, BoxLayout.Y_AXIS));
        pFin.setBackground(new Color(33, 47, 61));
        pFin.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titre = new JLabel("CLASSEMENT FINAL ");
        titre.setForeground(new Color(241, 196, 15)); 
        titre.setFont(new Font("Serif", Font.BOLD, 32));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        pFin.add(titre);
        pFin.add(Box.createRigidArea(new Dimension(0, 30)));
        
        Joueur gagnant = modele.joueurGagnant();
        String texteGagnant = (gagnant != null) ? "Le joueur gagnant est : " + gagnant.getNom() : "Aucun gagnant";
        JLabel labelGagnant = new JLabel(texteGagnant);
        labelGagnant.setForeground(Color.WHITE);
        labelGagnant.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelGagnant.setAlignmentX(Component.CENTER_ALIGNMENT);
        pFin.add(labelGagnant);
        pFin.add(Box.createRigidArea(new Dimension(0, 30)));        

        Map<Joueur, Integer> scores = modele.getScoresFinaux();
        if (scores != null) {
            scores.entrySet().stream()
                .sorted(Map.Entry.<Joueur, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    Joueur joueur = entry.getKey();
                    int pts = entry.getValue();

                    JPanel ligneScore = new JPanel(new BorderLayout(15, 0));
                    ligneScore.setMaximumSize(new Dimension(600, 100));
                    ligneScore.setBackground(new Color(44, 62, 80));
                    ligneScore.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(52, 73, 94)),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));

                    // Gauche : Nom et Détails du Jest
                    StringBuilder details = new StringBuilder("<html>" + joueur.getNom().toUpperCase());
                    details.append("<br><font size='2' color='#BDC3C7'>Cards: ");
                    for(Carte c : joueur.getJest()) details.append(c.toString()).append(" | ");
                    details.append("</font></html>");

                    JLabel lblNom = new JLabel(details.toString());
                    lblNom.setFont(new Font("SansSerif", Font.BOLD, 16));
                    lblNom.setForeground(Color.WHITE);

                    // Droite : Score
                    JLabel lblPts = new JLabel(pts + " PTS");
                    lblPts.setFont(new Font("SansSerif", Font.BOLD, 24));
                    lblPts.setForeground(new Color(46, 204, 113));

                    ligneScore.add(lblNom, BorderLayout.WEST);
                    ligneScore.add(lblPts, BorderLayout.EAST);

                    pFin.add(ligneScore);
                    pFin.add(Box.createRigidArea(new Dimension(0, 10)));
                });
        }

        pFin.add(Box.createVerticalGlue());
        
        JLabel msgGagnant = new JLabel(modele.getDernierMessage());
        msgGagnant.setForeground(Color.WHITE);
        msgGagnant.setFont(new Font("SansSerif", Font.ITALIC, 18));
        msgGagnant.setAlignmentX(Component.CENTER_ALIGNMENT);
        pFin.add(msgGagnant);
        pFin.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnQuitter = new JButton("QUITTER LE JEU");
        btnQuitter.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitter.setBackground(new Color(192, 57, 43));
        btnQuitter.setForeground(Color.WHITE);
        btnQuitter.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnQuitter.addActionListener(e -> System.exit(0));
        pFin.add(btnQuitter);

        add(new JScrollPane(pFin), BorderLayout.CENTER);
        revalidate(); repaint();
    }
}