package gui.main;

import gui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the application.
 * This class sets up the gui.frames JFrame to display the MainFrame JPanel, which contains the game interface.
 * ITS CURRENTLY UNDER CONSTRUCTION.
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel devPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("DEVELOPER MODE");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new MainFrame();

        devPanel.add(label, BorderLayout.NORTH);
        devPanel.add(panel, BorderLayout.CENTER);

        frame.add(devPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}