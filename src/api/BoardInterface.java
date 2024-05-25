package api;

import logic.Board;

public interface BoardInterface {
    void placeStone(int column); // diese Methode legt einen Stein, überprüft den Status und wechselt den Spieler
    boolean getIsFull();
    int getWhoHasWon(); //0: niemand     1: Spieler1     2:Spieler2
    void saveBoard();
    void overwriteVariableWithSavestats(); //übernimmt die Werte aus dem savefile(Werte, die angepasst werden:board,isFull,isPlayer1sTurn)
    int getComputerMove();
    // sagt uns falls ihr noch was braucht
}
