package gui.frames;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;

public class StartFrame {
    public StartFrame() {
        JFrame Auswahlfenster = new JFrame();
        Auswahlfenster.setTitle("4-Gewinnt");
        Auswahlfenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Auswahlfenster.setSize(1000, 500);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Label.background", Color.WHITE);

        JPanel GrundstrukturDesAuswahlfensters = new JPanel();
        GrundstrukturDesAuswahlfensters.setLayout(new BorderLayout());

        JPanel AnordnungDerButtons = new JPanel();
        AnordnungDerButtons.setLayout(new GridLayout(2, 1));

        JPanel SpielerPanel = new JPanel();
        SpielerPanel.setLayout(new FlowLayout());
        ImageIcon SpielerImage = new ImageIcon("res/SpielerVsSpieler.png");
        JButton Spieler = new JButton(SpielerImage);
        Spieler.addActionListener(e -> {
            JFrame frame = new JFrame();
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Auswahlfenster.dispose();
        });

        SpielerPanel.add(Spieler);
        JPanel ComputerPanel = new JPanel();
        ComputerPanel.setLayout(new FlowLayout());
        ImageIcon ComputerImage = new ImageIcon("res/SpielerVsComputer.png");
        JButton Computer = new JButton(ComputerImage);
        ComputerPanel.add(Computer);

        Spieler.setPreferredSize(new Dimension(400, 120));
        Computer.setPreferredSize(new Dimension(400, 120));

        JPanel aktuellerSpielstandPanel = new JPanel();
        aktuellerSpielstandPanel.setLayout(new FlowLayout());
        JButton aktuellerSpielstand = new JButton("letzter Spielstand laden");
        aktuellerSpielstand.setForeground(Color.WHITE);
        aktuellerSpielstandPanel.add(aktuellerSpielstand);
        aktuellerSpielstand.setBackground(Color.BLUE);

        aktuellerSpielstand.setPreferredSize(new Dimension(300, 50));

        Auswahlfenster.add(GrundstrukturDesAuswahlfensters);
        GrundstrukturDesAuswahlfensters.add(AnordnungDerButtons, BorderLayout.CENTER);
        GrundstrukturDesAuswahlfensters.add(aktuellerSpielstandPanel, BorderLayout.SOUTH);
        AnordnungDerButtons.add(SpielerPanel);
        AnordnungDerButtons.add(ComputerPanel);

        Auswahlfenster.setVisible(true);
        Auswahlfenster.setLocationRelativeTo(null);
    }
}