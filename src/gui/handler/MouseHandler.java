package gui.handler;

import gui.frames.MainPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles mouse click events on the game board.
 * This class extends MouseAdapter to provide functionality for detecting mouse clicks
 * and passing the coordinates to the MainFrame for processing.
 */
public class MouseHandler extends MouseAdapter {
    MainPanel mainPanel;

    /**
     * Constructs a MouseHandler object with a reference to the MainFrame.
     *
     * @param mainPanel The MainFrame object to which mouse click events will be forwarded.
     */
    public MouseHandler(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    /**
     * Overrides the mouseClicked method to handle mouse click events.
     * Extracts the mouse coordinates and forwards them to the MainFrame for processing.
     *
     * @param e The MouseEvent containing information about the mouse click event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int mouseX = e.getX();
        int mouseY = e.getY();

        mainPanel.checkIfMouseClickedAColumn(mouseX);
        mainPanel.isInsideButton(mouseX, mouseY);
    }
}
