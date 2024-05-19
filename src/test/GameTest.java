package test;

import api.BoardTestInterface;
import logic.Board;
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
    @DisplayName("Test GetTurn")
    void testGetTurn(){
        assertEquals(true, board.getTurn(), "Falscher Player");
    }


}
