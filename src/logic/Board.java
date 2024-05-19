package logic;

import api.BoardInterface;
import api.BoardTestInterface;

public class Board implements BoardInterface,BoardTestInterface {
    boolean isPlayer1sTurn; //ist Spieler eins am Zug?
    Tile[][] board; // das Spielfeld 	//Array[rows][columns]
    int whoHasWon; //0: niemand     1: Spieler1     2:Spieler2
    boolean isFull; // wenn das Spielfeld voll ist und keiner gewonnen hat

    //vollständiger Konstruktor
    public Board(boolean iP1T, int columns, int rows, int wHW, boolean iF){
        isPlayer1sTurn = iP1T;
        board = new Tile[rows][columns];
        whoHasWon = wHW;
        isFull = iF;
    }
    // legt nur die Größe fest
    public Board(int columns, int rows){
        isPlayer1sTurn = true;
        board = new Tile[rows][columns];
        whoHasWon = 0;
        isFull = false;
    }
    // Standard Konstruktor, macht das Spielfeld in normaler Größe
    public Board(){
        isPlayer1sTurn = true;
        board = new Tile[6][7];
        whoHasWon = 0;
        isFull = false;
    }

    @Override
    public void checkStatus(int column) {
        //eine Methode die checkt wie der Status vom Spiel ist und ggf. anpasst
        isWon(column);
        isFull();
    }

    void isWon(int column){
        int actualRow = isTopOfColumn(column);
        int player = board[actualRow][column].status;

        if (player !=0){
            //Diagonal
            if (count(player, actualRow, column, 1, 1) + count(player, actualRow, column, -1, -1) + 1 >= 4){
                whoHasWon=player;
            }
            //Diagonal
            else if (count(player, actualRow, column, -1, 1) + count(player, actualRow, column, 1, -1) + 1 >= 4){
                whoHasWon=player;
            }
            //Horizontal
            else if (count(player, actualRow, column, 0, 1) + count(player, actualRow, column, 0, -1) + 1 >= 4){
                whoHasWon=player;
            }
            //Vertical
            else if (count(player, actualRow, column, 1, 0) + count(player, actualRow, column, -1, 0) + 1 >= 4){
                whoHasWon=player;
            }
        }
    }

    //An auxiliary Function to help the method "void isWon(int column)""
    int count(int player, int rowToCheck, int columnToCheck, int rowDirection, int columnDirection){
        int numberOfRows = board.length;
        int numberOfColumns = board[0].length;
        int count = 0;
        while (true)
        {
            rowToCheck += rowDirection;
            columnToCheck += columnDirection;
            if (rowToCheck < 0 || columnToCheck < 0 || rowToCheck >= numberOfRows || columnToCheck >= numberOfColumns)
                break;
            if (board[rowToCheck][columnToCheck].status == player)
                count++;
            else
                break;
        }
        return count;
    }

    void isFull(){
        int numberOfRows=board.length;
        int numberOfColumns = board[0].length;
        for (int row= 0; row < numberOfRows; row++){
            for (int column=0; column < numberOfColumns; column++){
                if (board[row][column].status==0){
                    this.isFull=false;
                    return;
                }
            }
        }
        isFull=true;
    }

    @Override
    public void changePlayer() {
        this.setTurn(!(this.getTurn()));
    }

    /**
    *   Platziert einen Stein des aktuellen Spielers in die angegebene Spalte.
    *   Diese Methode überprüft zuerst, ob die Spalte voll ist.
    *   Wenn nicht, wird der Stein in die oberste leere Position der Spalte platziert.
    *   Dann wird der Spielstatus überprüft und der Spieler gewechselt.
    *   @param column Die Spalte, in die der Stein platziert werden soll.
    *   @throws IllegalArgumentException Wenn die Spalte voll ist.
    */

    @Override
    public void placeStone(int column) {
        int row = isTopOfColumn(column);
        if (row == -1) {
            throw new IllegalArgumentException("Column is full");}
        if (isPlayer1sTurn) {
            board[row][column] = new Tile(1); // Spieler 1
        } else {
            board[row][column] = new Tile(2); // Spieler 2
        }
        checkStatus(column);
        changePlayer();
    }

    /**
    *    Gibt die Reihe des obersten Steins einer Spalte zurück.
    *    @param column Die Spalte, für die die oberste Reihe zurückgegeben werden soll.
    *    @return Die Reihe des obersten Steins in der Spalte, oder -1, wenn die Spalte voll ist.
    */
    int isTopOfColumn(int column) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][column] == null || board[row][column].isEmpty()) {
                return row;
            }
        }
        return -1; // Spalte ist voll
    }

    /** 
    *   Setzt den Spieler, der am Zug ist.
    *   @param player1sTurn True, wenn Spieler eins am Zug ist, ansonsten False.
    */
    @Override
    public void setTurn(boolean player1sTurn) {
        this.isPlayer1sTurn = player1sTurn;
    }

    /**
    *   Setzt das Spielfeld mit einer neuen Matrix von Kacheln.
    *   @param board Die neue Matrix von Kacheln für das Spielfeld.
    */
    @Override
    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    /**  
    *   Setzt die Anzahl der Spalten des Spielfelds.
    *   @param columns Die neue Anzahl der Spalten.
    */
    @Override
    public void setColumns(int columns) {// Hier kann zusätzliche Logik implementiert werden, um das Spielfeld entsprechend der Anzahl der Spalten anzupassen
        this.board = new Tile[this.board.length][columns];
    }

    @Override
    public void setrows(int rows) {

    }

    /**
    *    Setzt die Anzahl der Reihen des Spielfelds.
    *    @param rows Die neue Anzahl der Reihen.
    */

    /*
    @Override
    public void setRows(int rows) {// Hier kann zusätzliche Logik implementiert werden, um das Spielfeld entsprechend der Anzahl der Reihen anzupassen
        this.board = new Tile[rows][this.board[0].length];
    }*/

    /**     
    *    Setzt den Status, wer das Spiel gewonnen hat.
    *    @param whoWon Der Status, wer das Spiel gewonnen hat (0 für niemand, 1 für Spieler1, 2 für Spieler2).
    */
    @Override
    public void setWhoHasWon(int whoWon) {
        this.whoHasWon = whoWon;
    }

    /**
    *    Setzt den Status, ob das Spielfeld voll ist oder nicht.
    *   @param isFull True, wenn das Spielfeld voll ist, ansonsten False.
    */
    @Override
    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }    
    
    @Override
    public boolean getTurn(){
        return this.isPlayer1sTurn;
    }

    @Override
    public Tile[][] getBoard(){
        return this.board;
    }

    @Override
    public int getrows(){
        return board.length;
    }

    @Override
    public int getColumns(){
        return board[0].length;
    }

    @Override
    public boolean getIsFull(){
        return this.isFull;
    }

    @Override
    public int getWhoHasWon(){
        return this.whoHasWon;
    }
}
