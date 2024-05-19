import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class start{
    public static void main(String[] args) {

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
        AnordnungDerButtons.setLayout(new GridLayout(2,1));

        JPanel SpielerPanel = new JPanel();
        SpielerPanel.setLayout(new FlowLayout());
        JButton Spieler = new JButton("SpielerVSSpieler");
        SpielerPanel.add(Spieler);
        JPanel ComputerPanel = new JPanel();
        ComputerPanel.setLayout(new FlowLayout());
        JButton Computer = new JButton("SpielerVSComputer");
        ComputerPanel.add(Computer);

        Spieler.setPreferredSize(new Dimension(400,120));
        Computer.setPreferredSize(new Dimension(400,120));

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

        Spieler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
    
        });
        Auswahlfenster.setVisible(true);
        
    }
}