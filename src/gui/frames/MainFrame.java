package gui.frames;

import gui.entity.Circle;
import gui.handler.MouseHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the gui.main frame of the game application.
 * This JPanel displays the game interface, including the game board and user interaction elements.
 * TODO: get rid of "(int) SCREEN_WIDTH / 3;"
 */
public class MainFrame extends JPanel {
    private final int SCREEN_WIDTH = 1500;
    private final int SCREEN_HEIGHT = 900;

    private final int RADIUS_CYCLE = 50;
    private int xCountOfCircles;
    private int yCountOfCircles;

    public MainFrame() {
        xCountOfCircles = 7;
        yCountOfCircles = 6;

        this.setBackground(Color.BLACK);
        this.addMouseListener(new MouseHandler(this));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    /**
     * Overrides the paintComponent method of JPanel to customize the rendering of graphics within this panel.
     * his method is responsible for painting the graphical components of the gui.main frame, including the game board
     * and user interaction elements.
     *
     * @param graphics the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        renderInteractiveBoard(graphics2D);
        renderGameElements(graphics2D);

        graphics2D.dispose();
    }

    /**
     * Renders the graphical elements of the game on the panel.
     * This method draws circles representing game pieces on the board.
     *
     * @param graphics2D The Graphics2D object used for rendering.
     */
    private void renderGameElements(Graphics2D graphics2D) {
        int xCoordinates = (int) SCREEN_WIDTH / 3;
        int yCoordinates = (int) SCREEN_HEIGHT / 3;

        // Loop through rows
        for (int i = 0; i < yCountOfCircles; i++) {
            if (i != 0) yCoordinates += calculateWhereToPlaceCircleVertically() + 10;

            // Draw circles in the row
            drawCircle(graphics2D, new Circle(xCoordinates, yCoordinates, RADIUS_CYCLE, Color.WHITE));

            for (int j = 0; j < xCountOfCircles; j++) {
                if (j != 0) xCoordinates += calculateWhereToPlaceCircleHorizontally();

                drawCircle(graphics2D, new Circle(xCoordinates, yCoordinates, RADIUS_CYCLE, Color.WHITE));
            }

            // Reset x-coordinate for next row
            xCoordinates = (int) SCREEN_WIDTH / 3;
        }
    }

    /**
     * Renders the interactive elements on the game board for user interaction.
     * This method draws rectangles representing clickable areas on the board.
     * Only for developers with color.
     * might delete later on.
     *
     * @param graphics2D The Graphics2D object used for rendering.
     */
    private void renderInteractiveBoard(Graphics2D graphics2D) {
        int xCoordinates = (int) SCREEN_WIDTH / 3;
        int yCoordinates = (int) SCREEN_HEIGHT / 3;
        graphics2D.setColor(Color.green);

        // Loop through columns
        for (int i = 0; i < xCountOfCircles; i++) {
            if (i == 0) ; else xCoordinates += calculateWhereToPlaceCircleHorizontally();

            // Draw rectangle for each column
            graphics2D.fillRect(xCoordinates, yCoordinates - 50, RADIUS_CYCLE, 70);
        }
    }

    /**
     * Calculates the horizontal spacing between circles in a row on the game board.
     * This method determines the distance between circles based on the number of circles
     * and the available space on the horizontal axis.
     *
     * @return The horizontal spacing between circles.
     */
    private int calculateWhereToPlaceCircleHorizontally() {
        final double startBox = (double) SCREEN_WIDTH / 3;
        return (int) startBox / xCountOfCircles;
    }

    /**
     * Calculates the vertical spacing between circles in a column on the game board.
     * This method determines the distance between circles based on the number of circles
     * and the available space on the vertical axis.
     *
     * @return The vertical spacing between circles.
     */
    private int calculateWhereToPlaceCircleVertically() {
        final double startBox = (double) SCREEN_HEIGHT / 3;
        return (int) startBox / yCountOfCircles;
    }

    /**
     * Draws a circle on the game board with the specified attributes.
     *
     * @param graphics2D The Graphics2D object used for rendering.
     * @param circle     The Circle object representing the circle to be drawn.
     */
    private void drawCircle(Graphics2D graphics2D, Circle circle) {
        graphics2D.setColor(circle.getColor());
        graphics2D.fillOval(circle.getXCoordinate(), circle.getYCoordinate(), circle.getRadius(), circle.getRadius());
    }

    /**
     * Checks if the mouse click corresponds to a specific column on the game board.
     * This method calculates the column based on the mouse click position and
     * determines if it falls within the boundaries of any column.
     * TODO: check vertically too.
     *
     * @param mouseX The x-coordinate of the mouse click.
     */
    public void checkIfMouseClickedAColumn(int mouseX) {
        int xCordinatesForEachColumn = (int) SCREEN_WIDTH / 3;

        for (int i = 0; i < xCountOfCircles; i++) {
            if (mouseX >= xCordinatesForEachColumn - RADIUS_CYCLE && mouseX <= xCordinatesForEachColumn + RADIUS_CYCLE) {
                System.out.println("border number: " + i);
                break;
            }
            xCordinatesForEachColumn += calculateWhereToPlaceCircleHorizontally();
        }
    }
}
