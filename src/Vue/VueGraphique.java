package Vue;

import javax.swing.*;
import java.awt.*;
import Modèle.*;

public class VueGraphique extends JFrame implements Observateur {
    private Partie modele;

    // Composants Saisie Joueur
    private JTextField fieldNom = new JTextField(15);
    private JComboBox<String> comboType = new JComboBox<>(new String[]{"Humain", "Virtuel"});
    private JComboBox<String> comboStrat = new JComboBox<>(new String[]{"Aléatoire", "Basique", "Défensive", "Aggressive"});
    
    // Composants Options
    private JCheckBox checkExtension = new JCheckBox("Activer l'extension (6 et 7)");
    private JRadioButton rbV1 = new JRadioButton("1 - Premier Aléatoire");
    private JRadioButton rbV2 = new JRadioButton("2 - Sans Trophées");
    private JRadioButton rbV3 = new JRadioButton("3 - Aucune", true);

    // Affichage
    private JTextArea areaRecap = new JTextArea(8, 30);
    private JLabel labelMsg = new JLabel("Statut : En attente de joueurs...");

    public VueGraphique(Partie p) {
        this.modele = p;
        setTitle("Jest Game - Configuration MVC");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. PANEL NORD : Options et Saisie
        JPanel panelNord = new JPanel();
        panelNord.setLayout(new BoxLayout(panelNord, BoxLayout.Y_AXIS));

        // Sous-panel : Extension & Variantes
        JPanel pOptions = new JPanel(new GridLayout(0, 1));
        pOptions.setBorder(BorderFactory.createTitledBorder("Règles de la partie"));
        pOptions.add(checkExtension);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbV1); group.add(rbV2); group.add(rbV3);
        JPanel pVar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pVar.add(new JLabel("Variante :"));
        pVar.add(rbV1); pVar.add(rbV2); pVar.add(rbV3);
        pOptions.add(pVar);

        // Sous-panel : Ajout Joueur
        JPanel pSaisie = new JPanel(new GridLayout(4, 2, 5, 5));
        pSaisie.setBorder(BorderFactory.createTitledBorder("Ajouter un Joueur"));
        pSaisie.add(new JLabel(" Nom :")); pSaisie.add(fieldNom);
        pSaisie.add(new JLabel(" Type :")); pSaisie.add(comboType);
        pSaisie.add(new JLabel(" Stratégie :")); pSaisie.add(comboStrat);
        comboStrat.setEnabled(false); // Désactivé par défaut pour Humain

        JButton btnAdd = new JButton("Ajouter le joueur");
        pSaisie.add(new JLabel()); pSaisie.add(btnAdd);

        panelNord.add(pOptions);
        panelNord.add(pSaisie);

        // 2. PANEL CENTRAL : Liste des joueurs
        areaRecap.setEditable(false);
        JPanel pCentral = new JPanel(new BorderLayout());
        pCentral.setBorder(BorderFactory.createTitledBorder("Liste des joueurs (3 ou 4 requis)"));
        pCentral.add(new JScrollPane(areaRecap), BorderLayout.CENTER);

        // 3. PANEL SUD : Lancement
        JPanel pSud = new JPanel(new BorderLayout());
        JButton btnLancer = new JButton("LANCER LA PARTIE");
        btnLancer.setFont(new Font("Arial", Font.BOLD, 16));
        btnLancer.setBackground(new Color(39, 174, 96));
        btnLancer.setForeground(Color.WHITE);
        pSud.add(btnLancer, BorderLayout.NORTH);
        pSud.add(labelMsg, BorderLayout.SOUTH);

        // AJOUTS FINAUX
        add(panelNord, BorderLayout.NORTH);
        add(pCentral, BorderLayout.CENTER);
        add(pSud, BorderLayout.SOUTH);

        // --- LISTENERS ---
        comboType.addActionListener(e -> comboStrat.setEnabled(comboType.getSelectedItem().equals("Virtuel")));

        btnAdd.addActionListener(e -> {
            modele.ajouterJoueur(fieldNom.getText(), (String)comboType.getSelectedItem(), comboStrat.getSelectedIndex());
            fieldNom.setText("");
        });

        btnLancer.addActionListener(e -> {
            int var = rbV1.isSelected() ? 1 : (rbV2.isSelected() ? 2 : 3);
            modele.configurerOptions(checkExtension.isSelected(), var);
            modele.lancerPartie();
        });

        setLocationRelativeTo(null);
    }

    @Override
    public void update(Partie p) {
        labelMsg.setText("Message : " + p.getDernierMessage());
        StringBuilder sb = new StringBuilder();
        for (Joueur j : p.getJoueur()) {
            sb.append("• ").append(j.getNom()).append(" (").append(j.getTypeJoueur()).append(")\n");
            if (j instanceof Virtuel) {
                Virtuel v = (Virtuel) j;
                if (v.getStrategie() != null) {
                    // On récupère le nom de la classe (ex: StrategieBasique) 
                    // et on le nettoie pour l'affichage
                    String nomStrat = v.getStrategie().getClass().getSimpleName();
                    sb.append(" [Stratégie : ").append(nomStrat).append("]");
                }
            }
            sb.append("\n");
        }
        areaRecap.setText(sb.toString());

        if (p.getEtat() == Partie.EtatPartie.OFFRES) {
            getContentPane().removeAll();
            add(new JLabel("PLATEAU DE JEU CHARGÉ", SwingConstants.CENTER));
            revalidate(); repaint();
        }
    }
}