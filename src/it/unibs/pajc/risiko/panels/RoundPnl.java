package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import src.it.unibs.pajc.risiko.RisikoLocalCntrl;

public class RoundPnl extends JPanel {
    private RisikoLocalCntrl cntrl;
    private ChronoPnl chronoPnl;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JPanel reinforcementPanel;
    private JPanel attackPanel;
    private JPanel movementPanel;

    private JLabel bonusTanksLabel;
    private JLabel selectAttackFromLabel;
    private JLabel selectAttackToLabel;
    private JLabel moveFromLabel;
    private JLabel moveToLabel;

    private JButton nextToAttackButton;
    private JButton nextToMovementButton;
    private JButton nextTurnButton;

    public RoundPnl(RisikoLocalCntrl cntrl, ChronoPnl chronoPnl) {
        this.cntrl = cntrl;
        this.chronoPnl = chronoPnl;

        setLayout(new BorderLayout());
        setBackground(new Color(80, 50, 40));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Creiamo un CardLayout per gestire il cambio di pannelli
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Creiamo i pannelli delle fasi
        reinforcementPanel = createReinforcementPanel();
        attackPanel = createAttackPanel();
        movementPanel = createMovementPanel();

        // Aggiungiamo i pannelli alla cardPanel
        cardPanel.add(reinforcementPanel, "REINFORCEMENT");
        cardPanel.add(attackPanel, "ATTACK");
        cardPanel.add(movementPanel, "MOVEMENT");

        // Aggiungiamo tutto al RoundPnl
        add(cardPanel, BorderLayout.CENTER);

        // Mostra la fase iniziale
        updatePhase();
    }

    //per la grafica
    private void styleComponent(JComponent c) {
        c.setFont(new Font("Serif", Font.BOLD, 16));
        c.setForeground(Color.WHITE);
        if (c instanceof JLabel lbl) {
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        if (c instanceof JButton btn) {
            btn.setBackground(new Color(200, 160, 60));
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
    
    
   
    private JPanel createReinforcementPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usa FlowLayout con uno spazio tra gli elementi
        panel.setBackground(new Color(80, 70, 50)); // look “retro”
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "REINFORCE PHASE", 0, 0, new Font("Serif", Font.BOLD, 18), Color.WHITE));
    
        bonusTanksLabel = new JLabel("Bonus Tanks: 0");
        JLabel instructionLabel = new JLabel("Posiziona i tanks cliccando sui tuoi territori.");
        nextToAttackButton = new JButton("ATTACK");
        nextToAttackButton.setPreferredSize(new Dimension(130, 40));
        
        // Imposta la dimensione del pulsante
        nextToAttackButton.setPreferredSize(new Dimension(130, 40));
        
        // Applichiamo lo stile al testo e al pulsante
        for (JComponent comp : Arrays.asList(bonusTanksLabel, instructionLabel, nextToAttackButton)) {
            styleComponent(comp);
        }
    
        // Icona spade
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/it/unibs/pajc/risiko/resources/spade_incrociate.png"));
            Image scaled = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            nextToAttackButton.setIcon(new ImageIcon(scaled));
            nextToAttackButton.setHorizontalTextPosition(SwingConstants.RIGHT);
            nextToAttackButton.setIconTextGap(10); // Spazio tra icona e testo
        } catch (Exception ex) {
            System.err.println("Icona non trovata!");
            ex.printStackTrace();
        }
    
        // Aggiungi componenti al pannello
        panel.add(bonusTanksLabel);
        panel.add(instructionLabel);
        panel.add(nextToAttackButton);
        
    
        // Aggiungi l'azione al pulsante
        nextToAttackButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });
    
        return panel;
    }
    
    

    private JPanel createAttackPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usa FlowLayout con uno spazio tra gli elementi
        panel.setBackground(new Color(80, 70, 50)); // look "retro"
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "ATTACK PHASE", 0, 0, new Font("Serif", Font.BOLD, 18), Color.WHITE));
    
        selectAttackFromLabel = new JLabel("SELEZIONA DA DOVE ATTACCARE");
        selectAttackToLabel = new JLabel("SELEZIONA IL BERSAGLIO");
        nextToMovementButton = new JButton("REINFORCE");
        nextToMovementButton.setPreferredSize(new Dimension(130, 40));
    
        // Applichiamo lo stile
        for (JComponent comp : Arrays.asList(selectAttackFromLabel, selectAttackToLabel, nextToMovementButton)) {
            styleComponent(comp);
        }
    
        // Sezione per l'animazione dei dadi
        JPanel dicePanel = new JPanel();
        dicePanel.setPreferredSize(new Dimension(150, 100)); // Puoi regolare la dimensione
        dicePanel.setBackground(Color.WHITE); // Color background placeholder per l'animazione dei dadi
    
        // Aggiungi componenti al pannello
        panel.add(selectAttackFromLabel);
        panel.add(selectAttackToLabel);
        panel.add(dicePanel); // Aggiungiamo il pannello dei dadi
        panel.add(nextToMovementButton);
    
        nextToMovementButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });
    
        return panel;
    }

    private JPanel createMovementPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usa FlowLayout con uno spazio tra gli elementi
        panel.setBackground(new Color(80, 70, 50)); // look "retro"
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "MOVEMENT PHASE", 0, 0, new Font("Serif", Font.BOLD, 18), Color.WHITE));
    
        moveFromLabel = new JLabel("Seleziona lo stato da cui prelevare carri armati");
        moveToLabel = new JLabel("Seleziona lo stato in cui spostarli");
        nextTurnButton = new JButton("END TURN");
        nextTurnButton.setPreferredSize(new Dimension(130, 40));
    
        // Applichiamo lo stile
        for (JComponent comp : Arrays.asList(moveFromLabel, moveToLabel, nextTurnButton)) {
            styleComponent(comp);
        }
    
        // Aggiungi componenti al pannello
        panel.add(moveFromLabel);
        panel.add(moveToLabel);
        panel.add(nextTurnButton);
    
        // Aggiungi l'azione al pulsante
        nextTurnButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });
    
        return panel;
    }
    

    public void updatePhase() {
        switch (cntrl.getStatus().getCurrentPhase()) {
            case REINFORCEMENT -> {cardLayout.show(cardPanel, "REINFORCEMENT");
                chronoPnl.addToChrono("Fase di rinforzo");
            }
            case ATTACK -> cardLayout.show(cardPanel, "ATTACK");
            case MOVEMENT -> cardLayout.show(cardPanel, "MOVEMENT");
        }
    }

    public void updateBonusTanks(int tanks) {
        bonusTanksLabel.setText("Bonus Tanks: " + tanks);
    }

    public void updateAttackSelection(String from, String to) {
        selectAttackFromLabel.setText("Attacco da: " + (from == null ? "Seleziona" : from));
        selectAttackToLabel.setText("Attacco a: " + (to == null ? "Seleziona" : to));
    }

    public void updateMovementSelection(String from, String to) {
        moveFromLabel.setText("Sposta da: " + (from == null ? "Seleziona" : from));
        moveToLabel.setText("Sposta a: " + (to == null ? "Seleziona" : to));
    }
}
