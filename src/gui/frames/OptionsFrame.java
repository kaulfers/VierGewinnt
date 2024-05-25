package gui.frames;

import api.BoardInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OptionsFrame extends Frame {
    MainPanel mainPanel;
    BoardInterface boardInterface;
    JFrame parentFrame;
    OptionsFrame(MainPanel mainPanel,BoardInterface boardInterface, JFrame parentFrame){
        this.mainPanel=mainPanel;
        this.boardInterface=boardInterface;
        this.parentFrame=parentFrame;
        JFrame optionsFrame = new JFrame("Optionen");

        JPanel panel = new JPanel();

        JPanel goBackPanel = new JPanel();
        JButton goBackButton= new JButton("Zurück");
        JLabel emptyJLabel2 = new JLabel("");
        JLabel emptyJLabel3 = new JLabel("");

        JLabel height = new JLabel("Hoehe");
        JTextField heightInput= new JTextField("");

        JLabel width = new JLabel("Breite");
        JTextField widthInput= new JTextField("");
        JButton playAgainButton= new JButton("Bestätigen und Neustarten");

        JLabel mistakesLabel= new JLabel("");
        mistakesLabel.setForeground(Color.RED);
        JButton saveButton= new JButton("Speichern");
        JButton quitButton= new JButton("Verlassen");

        goBackPanel.setLayout(new GridLayout(1,3));
        goBackPanel.add(goBackButton);
        goBackPanel.add(emptyJLabel2);
        goBackPanel.add(emptyJLabel3);

        panel.setLayout(new GridLayout(9,1));

        goBackButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   optionsFrame.dispose();
                }
            });
            
        playAgainButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final boolean COMPUTER_PLAY_MODE_ENABLED = mainPanel.getComputerPlayMode();
                    //Neue Fenster mit neuer anzahl der Felder wird geöffnet
                    try{
                        int y_heightNew = Integer.parseInt(heightInput.getText());
                        int x_widthNew = Integer.parseInt(widthInput.getText());
                        optionsFrame.dispose();
                        parentFrame.dispose();

                        JFrame frame = new JFrame();
                        MainPanel mainPanel = new MainPanel(frame,COMPUTER_PLAY_MODE_ENABLED ,x_widthNew, y_heightNew);
                        frame.add(mainPanel);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setResizable(false);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                    //Wenn der Input falsch ist, wird der Benutzer informiert
                    catch (NumberFormatException ex) {
                        mistakesLabel.setText("Invalid values for height or width");
                    }
                }
            });

        saveButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boardInterface.saveBoard();
                }
            });

        quitButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    optionsFrame.dispose();
                }
            });

        panel.add(goBackPanel);
        panel.add(height);
        panel.add(heightInput);

        panel.add(width);
        panel.add(widthInput);
        panel.add(playAgainButton);

        panel.add(mistakesLabel);
        panel.add(saveButton);
        panel.add(quitButton);

        optionsFrame.add(panel);
        optionsFrame.pack();
        optionsFrame.setSize(450, 600);
        optionsFrame.setLocationRelativeTo(null);
        optionsFrame.setAlwaysOnTop(true);
        optionsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        optionsFrame.setVisible(true);
    }

    public OptionsFrame(Runnable runnable, BoardInterface boardInterface, JFrame parentFrame) {

    }
}

