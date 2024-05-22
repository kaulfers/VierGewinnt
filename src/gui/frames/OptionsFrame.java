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
    OptionsFrame(MainPanel mainPanel){
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
        JTextField heightInput= new JTextField("...");

        JLabel width = new JLabel("Breite");
        JTextField widthInput= new JTextField("...");
        JButton playAgainButton= new JButton("Bestätigen und Neustarten");

        JLabel emptyLabel1= new JLabel("");
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
                };
            });
            
        playAgainButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer y_heightNew = Integer.parseInt(heightInput.getText());
                    Integer x_widthNew = Integer.parseInt(widthInput.getText());
                    parentFrame.dispose();
                    new MainPanel(x_widthNew,y_heightNew);

                };
            });

        saveButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //boardInterface.saveBoard();
                };
            });

        quitButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    optionsFrame.dispose();
                };
            });

        panel.add(goBackPanel);
        panel.add(height);
        panel.add(heightInput);

        panel.add(width);
        panel.add(widthInput);
        panel.add(playAgainButton);

        panel.add(emptyLabel1);
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
}

