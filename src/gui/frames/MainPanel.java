package gui.frames;

import api.BoardInterface;
import gui.entity.BordersForCircle;
import gui.entity.Circle;
import gui.handler.MouseHandler;
import logic.Board;
import logic.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents the gui.main frame of the game application.
 * This JPanel displays the game interface, including the game board and user interaction elements.
 *
 * @author Konrad
 */
public class MainPanel extends JPanel {
    private final int SCREEN_WIDTH = 1300;
    private final int SCREEN_HEIGHT = 900;

    private int circleDiameter;
    private int NUM_COLUMNS;
    private int NUM_ROWS;

    private final Color COLOR_PLAYER_1 = new Color(218, 114, 116);
    private final Color COLOR_PLAYER_2 = new Color(85, 187, 139);

    private String PLAYER_1_NAME = "Spieler 1";
    private String PLAYER_2_NAME = "Spieler 2";

    private int amountOfCirclesPlayer1;
    private int amountOfCirclesPlayer2;

    private boolean turnPlayer1 = true;
    private boolean createdStorageBox = false;
    private boolean playersSetNames = false;
    private boolean computerPlayMode;
    private boolean saveGame = false;

    private final ArrayList<Circle> CIRCLES = new ArrayList<>();
    private final ArrayList<BordersForCircle> STORAGE_CIRCLES_PLAYER_1 = new ArrayList<>();
    private final ArrayList<BordersForCircle> STORAGE_CIRCLES_PLAYER_2 = new ArrayList<>();

    private final int GAME_BOARD_WIDTH = 600;
    private final int GAME_BOARD_HEIGHT = 400;
    private final int GAME_BOEARD_ARC = 30;
    private int centerX;
    private int centerY;

    final private int BUTTON_WIDTH = 100;
    final private int BUTTON_HEIGHT = 30;
    final private int BUTTON_X_POSITION = SCREEN_WIDTH - BUTTON_WIDTH - 30;
    final private int BUTTON_Y_POSITION = 25;

    private MainPanel mainPanel;
    private BoardInterface boardInterface;
    private JFrame parentFrame;


    public MainPanel(JFrame parentFrame, boolean computerPlayMode, boolean saveGame) {
        this.mainPanel = this;
        this.parentFrame = parentFrame;
        this.computerPlayMode = computerPlayMode;
        this.saveGame = saveGame;

        if (!saveGame) {
            this.NUM_ROWS=7;
            this.NUM_COLUMNS=7;
            boardInterface = new Board(NUM_COLUMNS, NUM_ROWS);
        } else {
            boardInterface = new Board();
            boardInterface.overwriteVariableWithSavestats();
            NUM_COLUMNS = boardInterface.getColumns();
            NUM_ROWS = boardInterface.getrows();
        }


        if (computerPlayMode) {
            PLAYER_2_NAME = "Computer";
        }

        this.setBackground(new Color(245, 246, 222, 255));
        this.addMouseListener(new MouseHandler(this));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    public MainPanel(JFrame parentFrame, boolean computerPlayMode, int NUM_COLUMNS, int NUM_ROWS, boolean saveGame) {
        this.mainPanel = this;
        this.parentFrame = parentFrame;
        this.computerPlayMode = computerPlayMode;
        this.NUM_COLUMNS=NUM_COLUMNS;
        this.NUM_ROWS=NUM_ROWS;
        this.saveGame = saveGame;

        boardInterface = new Board(NUM_ROWS, NUM_COLUMNS);

        this.setBackground(new Color(245, 246, 222, 255));
        this.addMouseListener(new MouseHandler(this));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    }


    private void getNamesFromUser() {
        PLAYER_1_NAME = JOptionPane.showInputDialog(this, "Enter name for Player 1:", "Player 1", JOptionPane.PLAIN_MESSAGE);
        if (PLAYER_1_NAME == null || PLAYER_1_NAME.trim().isEmpty()) {
            PLAYER_1_NAME = "Spieler 1"; // Default name if input is empty or cancelled
        }

        PLAYER_2_NAME = JOptionPane.showInputDialog(this, "Enter name for Player 2:", "Player 2", JOptionPane.PLAIN_MESSAGE);
        if (PLAYER_2_NAME == null || PLAYER_2_NAME.trim().isEmpty()) {
            PLAYER_2_NAME = "Spieler 2"; // Default name if input is empty or cancelled
        }
        repaint();
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
        initializeCircles(graphics2D);

        if (!CIRCLES.isEmpty()) {
            calculateAmountOfCirclesForEachPlayer();
            if (!createdStorageBox) {
                createLocationsOfCirclesInsideOfStorageBox();
                createdStorageBox = true;

            }
        }

        if (saveGame) {
            loadSaveFile();
            saveGame = false;
        }

        renderCircleInStorage(graphics2D);
        drawTextLabel(graphics2D, "left", PLAYER_1_NAME);
        drawTextLabel(graphics2D, "right", PLAYER_2_NAME);
        renderPlayersTurnText(graphics2D);
        renderSettingsButton(graphics2D);

        graphics2D.dispose();

        /*
        if (!playersSetNames) {
            getNamesFromUser();
            playersSetNames = true;
            repaint();
        }*/
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
            xOfBox = 170;
            int xOfCircle;
            int yOfCircle;

            // Randomize the position of circles within the storage area
            xOfCircle = random.nextInt((xOfBox - WIDTH_OF_BOX / 2), (xOfBox + WIDTH_OF_BOX / 2));
            yOfCircle = random.nextInt((Y_OF_BOX - HEIGHT_OF_BOX / 2), (Y_OF_BOX + HEIGHT_OF_BOX / 2));

            STORAGE_CIRCLES_PLAYER_1.add(new BordersForCircle(i, xOfCircle, yOfCircle, COLOR_PLAYER_1, 2));
        }

        // Render circles for Player 2's storage area
        for (int i = 0; i < amountOfCirclesPlayer2; i++) {
            int SPACE_BETWEEN_CIRCLES = 10;
            xOfBox = SCREEN_WIDTH - 190;
            int xOfCircle;
            int yOfCircle;

            // Randomize the position of circles within the storage area
            xOfCircle = random.nextInt((xOfBox - WIDTH_OF_BOX / 2), (xOfBox + WIDTH_OF_BOX / 2));
            yOfCircle = random.nextInt((Y_OF_BOX - HEIGHT_OF_BOX / 2), (Y_OF_BOX + HEIGHT_OF_BOX / 2));

            STORAGE_CIRCLES_PLAYER_2.add(new BordersForCircle(i, xOfCircle, yOfCircle, COLOR_PLAYER_2, 2));
        }
    }

    /**
     * Renders the text indicating the current player's turn on the game board.
     * The text displays either "Player 1's turn" or "Player 2's turn" based on the current turn.
     *
     * @param graphics2D The Graphics2D object used for rendering the text.
     */

    private void renderPlayersTurnText(Graphics2D graphics2D) {
        final int Y_POSITION = 120;
        final String TEXT = turnPlayer1 ? PLAYER_1_NAME + " ist dran!" : PLAYER_2_NAME + " ist dran!";
        final int FONT_SIZE = 40; // Choose the desired font size

        // Set the font size
        Font originalFont = graphics2D.getFont();
        graphics2D.setFont(new Font(originalFont.getName(), Font.BOLD, FONT_SIZE));

        // Get the width of the text
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(TEXT);

        // Calculate the X position to center the text horizontally
        int X_POSITION = (SCREEN_WIDTH - textWidth) / 2;

        // Draw the text at the calculated position
        graphics2D.drawString(TEXT, X_POSITION, Y_POSITION);

        // Restore the original font
        graphics2D.setFont(originalFont);
    }


    /**
     * Renders circles in the storage area for both Player 1 and Player 2.
     * Circles are drawn with borders and filled with specified colors.
     *
     * @param graphics2D The Graphics2D object used for rendering.
     */
    private void renderCircleInStorage(Graphics2D graphics2D) {
        // Render circles for Player 1's storage area
        for (BordersForCircle border: STORAGE_CIRCLES_PLAYER_1) {
            // Draw the circle border for Player 1's storage area
            graphics2D.setColor(new Color(6, 71, 151));
            graphics2D.fillOval(border.getXCoordinate(), border.getYCoordinate(), circleDiameter + border.getBORDER_PADDING(), circleDiameter + border.getBORDER_PADDING());
            drawCircle(border, graphics2D);
        }

        // Render circles for Player 2's storage area
        for (BordersForCircle border: STORAGE_CIRCLES_PLAYER_2) {
            // Draw the circle border for Player 1's storage area
            graphics2D.setColor(new Color(165, 17, 17));
            graphics2D.fillOval(border.getXCoordinate(), border.getYCoordinate(), circleDiameter + border.getBORDER_PADDING(), circleDiameter + border.getBORDER_PADDING());
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
     * Renders a settings button with specified text.
     *
     * @param graphics2D The Graphics2D object used for rendering.
     */
    private void renderSettingsButton(Graphics2D graphics2D) {
        final String BUTTON_TEXT = "Optionen";

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRoundRect(BUTTON_X_POSITION, BUTTON_Y_POSITION, BUTTON_WIDTH, BUTTON_HEIGHT, 30, 30);

        graphics2D.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        final int TEXT_WIDTH = fontMetrics.stringWidth(BUTTON_TEXT);
        final int TEXT_X = BUTTON_X_POSITION + (BUTTON_WIDTH - TEXT_WIDTH) / 2;
        final int TEXT_Y = BUTTON_Y_POSITION + ((BUTTON_HEIGHT - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(BUTTON_TEXT, TEXT_X, TEXT_Y);
    }

    /**
     * Checks if the given mouse coordinates are inside the bounds of the settings button.
     * If the mouse coordinates are inside the button, it invokes the method to handle the settings button click.
     *
     * @param mouseX The x-coordinate of the mouse pointer.
     * @param mouseY The y-coordinate of the mouse pointer.
     */
    public void isInsideButton(int mouseX, int mouseY) {
        if (mouseX >= BUTTON_X_POSITION && mouseX <= BUTTON_X_POSITION + BUTTON_WIDTH &&
                mouseY >= BUTTON_Y_POSITION && mouseY <= BUTTON_Y_POSITION + BUTTON_HEIGHT) {
            handleSettingsButtonClick();
        }
    }

    /**
     * Handles the click event for the settings button by creating and displaying the OptionsFrame.
     * The OptionsFrame provides various options for the user to configure settings.
     */
    private void handleSettingsButtonClick() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OptionsFrame(mainPanel,boardInterface,parentFrame);
            }
        });
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
            x = 130;
        } else {
            x = 1080;
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
    private void initializeCircles(Graphics2D graphics2D) {
        if (!CIRCLES.isEmpty()) {
            for (Circle circle: CIRCLES) {
                drawCircle(circle, graphics2D);
            }
        } else {
            calculateCircleDiameter();
            centerX = (getWidth() - GAME_BOARD_WIDTH) / 2;
            centerY = (getHeight() - GAME_BOARD_HEIGHT) / 2;
            int horizontalGap = (GAME_BOARD_WIDTH - NUM_COLUMNS * circleDiameter) / (NUM_COLUMNS + 1);
            int verticalGap = (GAME_BOARD_HEIGHT - NUM_ROWS * circleDiameter) / (NUM_ROWS + 1);
            int circleID = 1;

            for (int row = 0; row < NUM_ROWS; row++) {
                for (int col = 0; col < NUM_COLUMNS; col++) {
                    int x = centerX + horizontalGap + col * (circleDiameter + horizontalGap);
                    int y = centerY + verticalGap + row * (circleDiameter + verticalGap);
                    Circle circle = new Circle(circleID, x, y, Color.WHITE);
                    CIRCLES.add(circle);
                    circleID++;
                }
            }

            for (Circle circle: CIRCLES) {
                drawCircle(circle, graphics2D);
            }
        }
    }

    void calculateCircleDiameter() {
        int horizontalGap = (GAME_BOARD_WIDTH - NUM_COLUMNS * circleDiameter) / (NUM_COLUMNS + 1);
        int verticalGap = (GAME_BOARD_HEIGHT - NUM_ROWS * circleDiameter) / (NUM_ROWS + 1);

        // Choose the minimum gap (horizontal or vertical) to determine the maximum diameter
        int maxGap = Math.min(horizontalGap, verticalGap);
        circleDiameter = maxGap;
    }

    /**
     * Renders the game board on the panel.
     * This method draws the background of the game board, including its boundaries and fill color.
     *
     * @param graphics2D The Graphics2D object used for drawing.
     */
    private void renderGameBoard(Graphics2D graphics2D) {
        centerX = (getWidth() - GAME_BOARD_WIDTH) / 2;
        centerY = (getHeight() - GAME_BOARD_HEIGHT) / 2;

        graphics2D.setColor(new Color(69, 147, 175));
        graphics2D.fillRoundRect(centerX, centerY, GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT, GAME_BOEARD_ARC, GAME_BOEARD_ARC);
    }

    /**
     * Draws a circle on the game board with the specified attributes.
     *
     * @param circle     The Circle object representing the circle to be drawn.
     */
    private void drawCircle(Circle circle, Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(circle.getXCoordinate(), circle.getYCoordinate(), circleDiameter + 2, circleDiameter + 2);

        graphics2D.setColor(circle.getColor());
        graphics2D.fillOval(circle.getXCoordinate(), circle.getYCoordinate(), circleDiameter, circleDiameter);
    }

    /**
     * Checks if the mouse click corresponds to a specific column on the game board.
     * This method calculates the column based on the mouse click position and
     * determines if it falls within the boundaries of any column.
     *
     * @param mouseX The x-coordinate of the mouse click.
     */
    public void checkIfMouseClickedAColumn(int mouseX) {
        for (int i = 0; i < NUM_COLUMNS; i++) {
            if (CIRCLES.get(i).getXCoordinate() + circleDiameter >= mouseX && CIRCLES.get(i).getXCoordinate() - circleDiameter <= mouseX) {
                boardInterface.placeStone(i);
                changeColorOfCircleInClickedColumn(i);
                checkGameStatus();
                if (computerPlayMode) {
                    int computerMove = boardInterface.getComputerMove();
                    changeColorOfCircleInClickedColumn(computerMove);
                    boardInterface.placeStone(computerMove);
                    checkGameStatus();
                }
                break;
            }
        }
    }

    private void checkGameStatus() {
        String gameOverMessage = null;

        if (boardInterface.getIsFull()) {
            gameOverMessage = "Unentschieden!";
            return;
        }

        // Check who has won
        switch (boardInterface.getWhoHasWon()) {
            case 1:
                gameOverMessage = PLAYER_1_NAME + " hat gewonnen!";
                break;
            case 2:
                gameOverMessage = PLAYER_2_NAME + " hat gewonnen!";
                break;
            default:
                // No one has won yet, or invalid state
                break;
        }

        if (gameOverMessage!=null) {
            parentFrame.dispose();
            new EndFrame(gameOverMessage, mainPanel, boardInterface, parentFrame);
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
        int circleIDOfCircleInColumn = (NUM_ROWS * NUM_COLUMNS) - (NUM_COLUMNS - (column + 1));
        int targedCicleID = 0;

        // Find the first uncolored circle in the clicked row
        for (int i = 0; i < NUM_ROWS; i++) {
            if (checkIfCircleIsNotColored(circleIDOfCircleInColumn)) {
                targedCicleID = circleIDOfCircleInColumn;
                break;
            } else {
                circleIDOfCircleInColumn -= NUM_COLUMNS;
            }
        }

        // Change the color of the target circle based on the current player's turn && remove storage circle
        for (Circle circle : CIRCLES) {
            if (circle.getID() == targedCicleID) {
                if (turnPlayer1) {
                    circle.setColor(COLOR_PLAYER_1);
                    STORAGE_CIRCLES_PLAYER_1.removeLast();
                    turnPlayer1 = false;
                }
                else {
                    circle.setColor(COLOR_PLAYER_2);
                    STORAGE_CIRCLES_PLAYER_2.removeLast();
                    turnPlayer1 = true;
                }
                break;
            }
        }

        repaint();
    }

    public void loadSaveFile() {
        Tile[][] myBoard = boardInterface.getBoard();
        int lastColumn = NUM_COLUMNS - 1;
        int lastRow = NUM_ROWS - 1;

        for (int i = lastColumn; i >= 0; i--) {
            for (int j = lastRow; j >= 0; j--) {
                if (myBoard[j][i].getStatus() == 1) {
                    turnPlayer1 = true;
                    changeColorOfCircleInClickedColumn(i); // i represents the column index
                } else if (myBoard[j][i].getStatus() == 2) {
                    turnPlayer1 = false;
                    changeColorOfCircleInClickedColumn(i); // i represents the column index
                }
            }
        }
        turnPlayer1 = boardInterface.getTurn();
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

    public boolean getComputerPlayMode() {
        return computerPlayMode;
    }
}
