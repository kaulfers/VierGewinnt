package gui.frames;

import gui.entity.BordersForCircle;
import gui.entity.Circle;
import gui.handler.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the gui.main frame of the game application.
 * This JPanel displays the game interface, including the game board and user interaction elements.
 * TODO: get rid of "(int) SCREEN_WIDTH / 3;"
 */
public class MainFrame extends JPanel {
    private final int SCREEN_WIDTH = 1300;
    private final int SCREEN_HEIGHT = 900;

    private final int RADIUS_CYCLE = 50;
    private final int SPACE_BETWEEN_CIRCLES = 10;
    private final int X_COUNT_OF_CIRCLES;
    private final int Y_COUNT_OF_CIRCLES;

    private final Color COLOR_PLAYER_1 = new Color(68, 122, 196);
    private final Color COLOR_PLAYER_2 = new Color(245, 117, 166);

    private int amountOfCirclesPlayer1;
    private int amountOfCirclesPlayer2;

    private boolean turnPlayer1 = true;

    private final ArrayList<Circle> CIRCLES = new ArrayList<>();
    private final ArrayList<BordersForCircle> BORDER_CIRCLES_PLAYER_1 = new ArrayList<>();
    private final ArrayList<BordersForCircle> BORDER_CIRCLES_PLAYER_2 = new ArrayList<>();

    public MainFrame() {
        X_COUNT_OF_CIRCLES = 7;
        Y_COUNT_OF_CIRCLES = 6;

        initializeCircles();
        calculateAmountOfCirclesForEachPlayer();

        createLocationsOfCirclesInsideOfStorageBox();

        this.setBackground(new Color(215, 246, 218, 255));
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

        renderGameBoard(graphics2D);
        //renderInteractiveBoard(graphics2D);

        renderGameElements(graphics2D);

        //drawMiddleLines(graphics2D);

        renderCircleInStorage(graphics2D);

        drawTextLabel(graphics2D, "left", "Player 1");
        drawTextLabel(graphics2D, "right", "Player 2");

        graphics2D.dispose();
    }

    /**
     * Renders the storage areas for circles of each player on the panel.
     * This method draws circles representing the stored circles for each player,
     * indicating the remaining circles available for gameplay.
     */
    private void createLocationsOfCirclesInsideOfStorageBox() {
        int xOfBox;
        final int Y_OF_BOX = SCREEN_HEIGHT / 2;
        final int WIDTH_OF_BOX = 100;
        final int HEIGHT_OF_BOX = 100;

        Random random = new Random();

        // Render circles for Player 1's storage area
        for (int i = 0; i < amountOfCirclesPlayer1; i++) {
            xOfBox = (SCREEN_WIDTH / 2) - ((X_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((X_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES) * 2);
            int xOfCircle;
            int yOfCircle;

            // Randomize the position of circles within the storage area
            xOfCircle = random.nextInt((xOfBox - WIDTH_OF_BOX / 2), (xOfBox + WIDTH_OF_BOX / 2));
            yOfCircle = random.nextInt((Y_OF_BOX - HEIGHT_OF_BOX / 2), (Y_OF_BOX + HEIGHT_OF_BOX / 2));

            BORDER_CIRCLES_PLAYER_1.add(new BordersForCircle(i, xOfCircle, yOfCircle, COLOR_PLAYER_1, 2));
        }

        // Render circles for Player 2's storage area
        for (int i = 0; i < amountOfCirclesPlayer2; i++) {
            xOfBox = (SCREEN_WIDTH / 2) + ((X_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((X_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES) / 2);
            int xOfCircle;
            int yOfCircle;

            // Randomize the position of circles within the storage area
            xOfCircle = random.nextInt((xOfBox - WIDTH_OF_BOX / 2), (xOfBox + WIDTH_OF_BOX / 2));
            yOfCircle = random.nextInt((Y_OF_BOX - HEIGHT_OF_BOX / 2), (Y_OF_BOX + HEIGHT_OF_BOX / 2));

            BORDER_CIRCLES_PLAYER_2.add(new BordersForCircle(i, xOfCircle, yOfCircle, COLOR_PLAYER_2, 2));
        }
    }

    private void renderCircleInStorage(Graphics2D graphics2D) {
        // Render circles for Player 1's storage area
        for (BordersForCircle border: BORDER_CIRCLES_PLAYER_1) {
            // Draw the circle border for Player 1's storage area
            graphics2D.setColor(new Color(6, 71, 151));
            graphics2D.fillOval(border.getXCoordinate(), border.getYCoordinate(), RADIUS_CYCLE + border.getBORDER_PADDING(), RADIUS_CYCLE + border.getBORDER_PADDING());
            drawCircle(border, graphics2D);
        }

        // Render circles for Player 2's storage area
        for (BordersForCircle border: BORDER_CIRCLES_PLAYER_2) {
            // Draw the circle border for Player 1's storage area
            graphics2D.setColor(new Color(165, 17, 17));
            graphics2D.fillOval(border.getXCoordinate(), border.getYCoordinate(), RADIUS_CYCLE + border.getBORDER_PADDING(), RADIUS_CYCLE + border.getBORDER_PADDING());
            drawCircle(border, graphics2D);
        }
    }

    /**
     * Calculates the number of circles each player should have based on the total number of circles.
     * If the total number of circles is odd, player 1 will have one more circle than player 2.
     * This method updates the instance variables amountOfCirclesPlayer1 and amountOfCirclesPlayer2.
     */
    private void calculateAmountOfCirclesForEachPlayer() {
        final int totalCircles = CIRCLES.size();

        if (totalCircles % 2 != 0) {
            amountOfCirclesPlayer1 = (totalCircles / 2) + 1;
            amountOfCirclesPlayer2 = (totalCircles / 2);
        } else {
            amountOfCirclesPlayer1 = amountOfCirclesPlayer2 = totalCircles / 2;
        }
    }

    /**
     * For GUI Developers. will be deleted.
     *
     */
    private void drawMiddleLines(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);

        // Draw horizontal line
        int middleY = SCREEN_HEIGHT / 2;
        graphics2D.drawLine(0, middleY, SCREEN_WIDTH, middleY);

        // Draw vertical line
        int middleX = SCREEN_WIDTH / 2;
        graphics2D.drawLine(middleX, 0, middleX, SCREEN_HEIGHT);
    }

    /**
     * Draws a text label on the game board at a specified position.
     * The label can be positioned either to the left or right of the game board.
     *
     * @param graphics2D             The Graphics2D object used for drawing.
     * @param rightOrLeftOfTheScreen Specifies whether the label should be drawn to the left or right of the screen.
     * @param text                   The text to be displayed in the label.
     */
    private void drawTextLabel(Graphics2D graphics2D, String rightOrLeftOfTheScreen, String text) {
        final int x;
        final int y = SCREEN_HEIGHT / 3;

        if (rightOrLeftOfTheScreen.equals("left")) {
            x = (SCREEN_WIDTH / 2) - ((X_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((X_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES) * 2);
        } else {
            x = (SCREEN_WIDTH / 2) + ((X_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((X_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES) / 2);
        }

        // Set up font and color for the label
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.setColor(Color.BLACK);

        // Draw the label
        graphics2D.drawString(text, x, y);
    }


    /**
     * Creates and initializes the circles on the game board.
     * Circles are positioned in a grid layout, with each row containing circles horizontally aligned.
     * The method calculates the coordinates for each circle and creates Circle objects accordingly.
     */
    private void initializeCircles() {
        int xCoordinates = (int) SCREEN_WIDTH / 3;
        int yCoordinates = (int) SCREEN_HEIGHT / 3;
        int circleID = 1;

        // Loop through rows
        for (int i = 0; i < Y_COUNT_OF_CIRCLES; i++) {
            if (i != 0) yCoordinates += calculateWhereToPlaceCircleVertically() + SPACE_BETWEEN_CIRCLES;

            // Draw circles in the row
            for (int j = 0; j < X_COUNT_OF_CIRCLES; j++) {
                if (j != 0) xCoordinates += calculateWhereToPlaceCircleHorizontally();

                Circle circle = new Circle(circleID, xCoordinates, yCoordinates, Color.WHITE);
                CIRCLES.add(circle);
                circleID++;
            }

            // Reset x-coordinate for next row
            xCoordinates = (int) SCREEN_WIDTH / 3;
        }
    }

    /**
     * Renders the graphical elements of the game on the panel.
     * This method draws circles representing game pieces on the board.
     *
     */
    private void renderGameElements(Graphics2D graphics2D) {
        for (Circle circle : CIRCLES) {
            drawCircle(circle, graphics2D);
        }
    }

    /**
     * Renders the interactive elements on the game board for user interaction.
     * This method draws rectangles representing clickable areas on the board.
     * Only for developers with color.
     * might delete later on.
     */
    private void renderInteractiveBoard(Graphics2D graphics2D) {
        int xCoordinates = (int) SCREEN_WIDTH / 3;
        int yCoordinates = (int) SCREEN_HEIGHT / 3;
        graphics2D.setColor(Color.green);

        // Loop through columns
        for (int i = 0; i < X_COUNT_OF_CIRCLES; i++) {
            if (i != 0) xCoordinates += calculateWhereToPlaceCircleHorizontally();

            // Draw rectangle for each column
            graphics2D.fillRect(xCoordinates, yCoordinates - 50, RADIUS_CYCLE, 70);
        }
    }

    /**
     * TODO: THIS METHOD HAS TO BE REDONE SINCE IT DOES NOT DYNAMICALLY CHANGE.
     * Renders the game board on the panel.
     * This method draws the background of the game board, including its boundaries and fill color.
     *
     * @param graphics2D The Graphics2D object used for drawing.
     */
    private void renderGameBoard(Graphics2D graphics2D) {
        final int WIDTH = (int) (X_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((X_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES);
        final int HEIGHT = (int) (Y_COUNT_OF_CIRCLES * RADIUS_CYCLE) + ((Y_COUNT_OF_CIRCLES - 1) * SPACE_BETWEEN_CIRCLES);
        final int CENTER_COORDINATE_X = (int) SCREEN_WIDTH / 2;
        final int CENTER_COORDINATE_Y = (int) SCREEN_HEIGHT / 2;
        final int ARC_WIDTH = 30;
        final int ARC_HEIGHT = 30;

        graphics2D.setColor(new Color(28, 167, 236));

        // Draw the game board background
        graphics2D.fillRoundRect(CENTER_COORDINATE_X - 256, CENTER_COORDINATE_Y - 170, WIDTH + 80, HEIGHT + 40, ARC_WIDTH, ARC_HEIGHT);
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
        return (int) startBox / X_COUNT_OF_CIRCLES;
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
        return (int) startBox / Y_COUNT_OF_CIRCLES;
    }

    /**
     * Draws a circle on the game board with the specified attributes.
     *
     * @param circle     The Circle object representing the circle to be drawn.
     */
    private void drawCircle(Circle circle, Graphics2D graphics2D) {
        graphics2D.setColor(circle.getColor());
        graphics2D.fillOval(circle.getXCoordinate(), circle.getYCoordinate(), RADIUS_CYCLE, RADIUS_CYCLE);
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

        for (int i = 0; i < X_COUNT_OF_CIRCLES; i++) {
            if (mouseX >= xCordinatesForEachColumn - RADIUS_CYCLE && mouseX <= xCordinatesForEachColumn + RADIUS_CYCLE) {
                changeColorOfCircleInClickedColumn(i);
                break;
            }
            xCordinatesForEachColumn += calculateWhereToPlaceCircleHorizontally();
        }
    }

    /**
     * This method could not be permanent, if we consider implementing a drag-and-drop feature.
     * Changes the color of the circle in the clicked column.
     * This method identifies the circle in the specified column that should change color,
     * updates its color based on the current player's turn, and adjusts the corresponding
     * player's circle count.
     *
     * @param column The index of the clicked column.
     */
    private void changeColorOfCircleInClickedColumn(int column) {
        // +1 because the ids of the circles start with 1 and not with a 0 (its easier for the math)
        int circleIDOfCircleInColumn = (Y_COUNT_OF_CIRCLES * X_COUNT_OF_CIRCLES) - (X_COUNT_OF_CIRCLES - (column + 1));
        int targedCicleID = 0;

        // Find the first uncolored circle in the clicked row
        for (int i = 0; i < Y_COUNT_OF_CIRCLES; i++) {
            if (checkIfCircleIsNotColored(circleIDOfCircleInColumn)) {
                targedCicleID = circleIDOfCircleInColumn;
                break;
            } else {
                circleIDOfCircleInColumn -= X_COUNT_OF_CIRCLES;
            }
        }

        // Change the color of the target circle based on the current player's turn
        for (Circle circle : CIRCLES) {
            if (circle.getID() == targedCicleID) {
                if (turnPlayer1) {
                    circle.setColor(COLOR_PLAYER_1);
                    BORDER_CIRCLES_PLAYER_1.removeLast();
                    turnPlayer1 = false;
                }
                else {
                    circle.setColor(COLOR_PLAYER_2);
                    BORDER_CIRCLES_PLAYER_2.removeLast();
                    turnPlayer1 = true;
                }
                break;
            }
        }

        repaint();
    }

    /**
     * Checks if the circle with the specified ID is not yet colored.
     * This method determines whether the circle identified by the given ID
     * has its color set to Color.WHITE, indicating that it has not been
     * colored by any player.
     *
     * @param id The ID of the circle to check.
     * @return {@code true} if the circle is not yet colored, {@code false} otherwise.
     */
    private boolean checkIfCircleIsNotColored(int id) {
        for (Circle circle : CIRCLES) {
            if (circle.getID() == id && circle.getColor().equals(Color.WHITE)) return true;
        }
        return false;
    }
}
