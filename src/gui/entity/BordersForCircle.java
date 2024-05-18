package gui.entity;

import java.awt.*;

public class BordersForCircle extends Circle {
    private final int BORDER_PADDING;

    public BordersForCircle(int ID, int X_COORDINATES, int Y_COORDINATES, Color color, int borderPadding) {
        super(ID, X_COORDINATES, Y_COORDINATES, color);
        BORDER_PADDING = borderPadding;
    }

    public int getBORDER_PADDING() {
        return BORDER_PADDING;
    }
}
