package gui.main;

import gui.frames.StartFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the application.
 * This class sets up the gui.frames JFrame to display the MainFrame JPanel, which contains the game interface.
 */
public class App {
    public static void main(String[] args) {
        new StartFrame();
    }
}