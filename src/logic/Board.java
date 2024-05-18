package logic;

import api.BoardInterface;
import api.BoardTestInterface;
//TODO restliche Interface-Methoden implementieren
public class Board implements BoardInterface,BoardTestInterface {
    boolean isPlayer1sTurn; //ist Spieler eins am Zug?
    Tile[][] board; // das Spielfeld 	//Array[rows][columns]
    int whoHasWon; //0: niemand     1: Spieler1     2:Spieler2
    boolean isFull; // wenn das Spielfeld voll ist und keiner gewonnen hat

    //vollständiger Konstruktor
    Board(boolean iP1T, int columns, int rows, int wHW, boolean iF){
        isPlayer1sTurn = iP1T;
        board = new Tile[rows][columns];
        whoHasWon = wHW;
        isFull = iF;
    }
    // legt nur die Größe fest
    Board(int columns, int rows){
        isPlayer1sTurn = true;
        board = new Tile[rows][columns];
        whoHasWon = 0;
        isFull = false;
    }
    // Standard Konstruktor, macht das Spielfeld in normaler Größe
    Board(){
        isPlayer1sTurn = true;
        board = new Tile[6][7];
        whoHasWon = 0;
        isFull = false;
    }

    //TODO Setter und Getter

    @Override
    public void checkStatus(int column) {
        // TODO eine Methode die checkt wie der Status vom Spiel ist und ggf. anpasst
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

    @Override
    public void placeStone(int column) {
        // TODO eine Methode, die einen Stein an die richtige Stelle setzt
        this.checkStatus(column);
        this.changePlayer();
    }

    @Override
    public void setTurn(boolean player1sTurn) {

    }

    @Override
    public void setBoard(Tile[][] board) {

    }

    @Override
    public void setColumns(int columns) {

    }

    @Override
    public void setrows(int rows) {

    }

    @Override
    public void setWhoHasWon(int whoWon) {

    }

    @Override
    public void setIsFull(boolean isFull) {

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

    int isTopOfColumn(int column){ // gibt die reihe des obersten Steins einer Spalte wieder.
        return 0;
    }

}
