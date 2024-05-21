package gui.frames;

import logic.Board;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndFrame extends JFrame {
    private JButton closeButton;
    private JButton againPlayButton;
    private JButton settingButton;
    private JLabel messageLabel;


    public EndFrame() {
        // Basic view window
        setTitle("4Gewinnt");
        setSize(900, 700);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new GridBagLayout());


        // Components setting
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Label
        messageLabel = new RoundedLabel(whoWon(), SwingConstants.CENTER);
        Dimension labelSize = new Dimension(450, 110);
        messageLabel.setPreferredSize(labelSize);
        messageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                new EmptyBorder(15, 10, 18, 10)
        ));
        Font newFont = new Font("Arial", Font.BOLD, 24);
        messageLabel.setFont(newFont);
        messageLabel.setOpaque(false);


        mainPanel.add(messageLabel, gbc);


        // Buttons
        Dimension btnSize = new Dimension(120, 35);
        Border grayBorder = new LineBorder(Color.GRAY, 1, true);

        closeButton = new JButton("Verlassen");
        closeButton.addActionListener(new CloseButtonListener());
        closeButton.setPreferredSize(btnSize);
        closeButton.setBorder(grayBorder);

        againPlayButton = new JButton("Neuestarten");
        againPlayButton.addActionListener(new AgainPlayButtonListener());
        againPlayButton.setPreferredSize(btnSize);
        againPlayButton.setBorder(grayBorder);

        settingButton = new JButton("Einstellungen");
        settingButton.addActionListener(new SettingButtonListener());
        settingButton.setPreferredSize(btnSize);
        settingButton.setBorder(grayBorder);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30));
        buttonPanel.add(closeButton);
        buttonPanel.add(againPlayButton);
        buttonPanel.add(settingButton);


        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }



    // Listeners
    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }
    private class SettingButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            openOptionsFrame();
        }
    }

    private class AgainPlayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            restartApplication();
        }
    }

    // methodies for listeners
    private void openOptionsFrame() {
        OptionsFrame optionsFrame = new OptionsFrame();
        optionsFrame.setVisible(true);
    }

    private void restartApplication() {
        JFrame auswahlfenster = StartFrame.getAuswahlfenster();
        auswahlfenster.setVisible(true);
    }

    // who won methody
    private String whoWon() {
        System.out.println(determineWinner());
        int winner = determineWinner();
        if (winner == 1) {
            return ("Spieler 1 hat gewonnen");
        } if (winner == 2) {
            return "Spieler 2 hat gewonnen";
        } else {
            return "Unentschieden";
        }
    }
    Board board = new Board();
    private int determineWinner() {
        System.out.println(board.getWhoHasWon());
        return board.getWhoHasWon();
    }


    // Rounded borders class
    public class RoundedLabel extends JLabel {
        public RoundedLabel(String text, int horizontalAlignment) {
            super(text, horizontalAlignment);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            int arcSize = (getHeight() - 5) / 2;
            g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, arcSize, arcSize);
            g2.setColor(getForeground());
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        public void setBorder(Border border) {
            super.setBorder(new EmptyBorder(15, 10, 18, 10));
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}