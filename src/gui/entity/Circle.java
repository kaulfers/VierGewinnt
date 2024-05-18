package gui.entity;

import java.awt.*;

public class Circle {
    private final int ID;
    private final int X_COORDINATES;
    private final int Y_COORDINATES;
    private Color color;

    public Circle(int ID, int X_COORDINATES, int Y_COORDINATES, Color color) {
        this.ID = ID;
        this.X_COORDINATES = X_COORDINATES;
        this.Y_COORDINATES = Y_COORDINATES;
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public int getXCoordinate() {
        return X_COORDINATES;
    }

    public int getYCoordinate() {
        return Y_COORDINATES;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
