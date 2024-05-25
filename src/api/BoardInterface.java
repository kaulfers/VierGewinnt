package api;

import logic.Board;
import logic.Tile;

public interface BoardInterface {
    void placeStone(int column); // diese Methode legt einen Stein, überprüft den Status und wechselt den Spieler
    boolean getIsFull();
    int getWhoHasWon(); //0: niemand     1: Spieler1     2:Spieler2
    void saveBoard();
    void overwriteVariableWithSavestats(); //übernimmt die Werte aus dem savefile(Werte, die angepasst werden:board,isFull,isPlayer1sTurn)
    int getComputerMove();
    Tile[][] getBoard();
    int getColumns(); //gibt die Anzahl der Spalten
    int getrows();//gibt die Anzahl der Reihen
    boolean getTurn();
    // sagt uns falls ihr noch was braucht
}
