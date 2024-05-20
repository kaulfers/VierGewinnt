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
    @Test
    @DisplayName("Test für zwei normale Züge, auf neuem Spielfeld")
    void testPlaceStone1(){
        board.placeStone(0);    // Spieler 1
        board.placeStone(0);    // Spieler 2
        Tile[][] t = board.getBoard();
        assertEquals(1, t[5][0].getStatus(), "Falscher Status im Feld 5/0");
        assertEquals(2, t[4][0].getStatus(), "Falscher Status im Feld 4/0");
        assertEquals(0, t[5][1].getStatus(), "Falscher Status im Feld 5/1");
        assertEquals(0, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    @Test
    @DisplayName("Sieg Spieler 1")
    void testPlaceStone2(){
        board.placeStone(0);    // Spieler 1    
        board.placeStone(0);    // Spieler 2     
        board.placeStone(1);    // Spieler 1
        board.placeStone(1);    // Spieler 2
        board.placeStone(2);    // Spieler 1
        board.placeStone(2);    // Spieler 2
        board.placeStone(3);    // Spieler 1
        assertEquals(1, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    @Test
    @DisplayName("Volle Row ohne Sieger")
    void testFullRow(){
        board.placeStone(0);    // Spieler 1    
        board.placeStone(0);    // Spieler 2     
        board.placeStone(0);    // Spieler 1
        board.placeStone(0);    // Spieler 2
        board.placeStone(0);    // Spieler 1
        board.placeStone(0);    // Spieler 2
        board.placeStone(0);    // Spieler 1
        assertEquals(0, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }

}
