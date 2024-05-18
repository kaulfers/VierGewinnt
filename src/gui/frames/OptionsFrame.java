import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OptionsFrame {
    OptionsFrame(){
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
                   optionsFrame.setVisible(false);
                };
            });

        playAgainButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                };
            });    
        
        saveButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                };
            }); 
            
        quitButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  
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
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setVisible(true);

    }
}

