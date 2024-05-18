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

        JPanel panel = new MainFrame();

        frame.add(panel);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}