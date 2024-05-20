package test;

import api.BoardTestInterface;
import logic.*;
import static org.junit.jupiter.api.Assertions.assertEquals;    
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class GameTest {
    BoardTestInterface board;

    @BeforeEach
    void setUp(){
        board = new Board();
    }    
    // das ist nur ein Beispiel um junit testen, hier soll später was anderes stehen
    @Test
    @DisplayName("Erster Zug")
    void testPlaceStone(){
        board.placeStone(0);
        Tile[][] t = board.getBoard();
        assertEquals(1, t[5][0], "Falscher Status im Feld 5/0");
    }

    @Test
    @DisplayName("Test GetTurn")
    void testGetTurn(){
        Board board2 = new Board();
        assertEquals(true, board.getTurn(), "Falscher Player");
    }


}
