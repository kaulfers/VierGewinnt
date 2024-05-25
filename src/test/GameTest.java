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
        //board.placeStone(0);    // Spieler 1
        assertEquals(0, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    @Test
    @DisplayName("Sieg Spieler 2")
    void testPlaceStone3(){
        board.placeStone(0);    // Spieler 1    
        board.placeStone(3);    // Spieler 2     
        board.placeStone(1);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        board.placeStone(2);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        board.placeStone(1);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        assertEquals(2, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }

    @Test
    @DisplayName("Board Voll ohne Sieger")
    void testFullBoardWithoutWinner() {
        for(int i = 0; i < 6 ; i++) board.placeStone(0);
        for(int i = 0; i < 6 ; i++) board.placeStone(1);
        for(int i = 0; i < 6 ; i++) board.placeStone(2);
        board.placeStone(4);
        board.placeStone(3);
        for(int i = 0; i < 5 ; i++) board.placeStone(3);
        for(int i = 0; i < 5 ; i++) board.placeStone(4);
        for(int i = 0; i < 6 ; i++) board.placeStone(5);
        for(int i = 0; i < 6 ; i++) board.placeStone(6);
        assertEquals(0, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(true, board.getIsFull(), "Falscher IsFull Status");
    }
    @Test
    
    @DisplayName("Sieg Spieler 1 - Horizontal")
    void testHorizontalWin() {
        // Horizontaler Sieg für Spieler 1
        board.placeStone(0);    // Spieler 1    
        board.placeStone(1);    // Spieler 2     
        board.placeStone(0);    // Spieler 1
        board.placeStone(2);    // Spieler 2
        board.placeStone(0);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        board.placeStone(0);    // Spieler 1 (horizontaler Sieg)
    
    
    assertEquals(1, board.getWhoHasWon(), "Falscher Gewinner Status");
    assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }

    @Test
    @DisplayName("Sieg Spieler 1 - Diagonal")
    void testDiagonalWin() {
        // Diagonaler Sieg für Spieler 1
        board.placeStone(0);    // Spieler 1    
        board.placeStone(1);    // Spieler 2     
        board.placeStone(1);    // Spieler 1
        board.placeStone(2);    // Spieler 2
        board.placeStone(2);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        board.placeStone(2);    // Spieler 1
        board.placeStone(3);    // Spieler 2
        board.placeStone(3);    // Spieler 1
        board.placeStone(4);    // Spieler 2
        board.placeStone(3);    // Spieler 1 (diagonaler Sieg)
    
        assertEquals(1, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    @RepeatedTest(5)
    @DisplayName("Test ComputerMove, einfacher Sieg möglich durch setzen in Spalte 4")
    void testKiGegner1(){
        board.placeStone(1);                   // Spieler 1
        board.placeStone(1);                   // Spieler 2
        board.placeStone(2);                   // Spieler 1
        board.placeStone(2);                   // Spieler 2
        board.placeStone(3);                   // Spieler 1
        board.placeStone(3);                   // Spieler 2
        board.placeStone(3);                   // Spieler 1
        board.placeStone(0);                   // Spieler 2
        board.placeStone(board.getComputerMove());    // PC
        Tile[][] t = board.getBoard();
        gameprint(t);
        assertEquals(1, t[5][4].getStatus(), "PC ist dumm!");
        assertEquals(1, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    // Ausgabe auf der Debug Console
    void gameprint(Tile[][] t){
        String s = "Spielfeld: \n";
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                s += "| " + t[i][j].getStatus() + " |";
            }
            s += "\n";
        }
        System.out.println(s);
    }
    @RepeatedTest(5)
    @DisplayName("Test ComputerMove")
    void testKiGegner2(){
        board.placeStone(0);                   // Spieler 1
        board.placeStone(1);                   // Spieler 2
        board.placeStone(1);                   // Spieler 1
        board.placeStone(2);                   // Spieler 2
        board.placeStone(3);                   // Spieler 1
        board.placeStone(2);                   // Spieler 2
        board.placeStone(3);                   // Spieler 1
        board.placeStone(3);                   // Spieler 2
        board.placeStone(3);                   // Spieler 1
        board.placeStone(4);                   // Spieler 2
        board.placeStone(board.getComputerMove());    // PC
        Tile[][] t = board.getBoard();
        gameprint(t);
        assertEquals(1, t[3][2].getStatus(), "PC ist dumm!");
        assertEquals(1, board.getWhoHasWon(), "Falscher Gewinner Status");
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
    @RepeatedTest(5)
    @DisplayName("Test ComputerMove")
    void testKiGegner3(){
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove()); 
        board.placeStone(board.getComputerMove());    // PC
        Tile[][] t = board.getBoard();
        gameprint(t);                                 // hier muss man leider im moment selber schauen (Debug-Console)
        assertEquals(false, board.getIsFull(), "Falscher IsFull Status");
    }
}
