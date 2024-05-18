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

   }

   void isFull(){

   }

    @Override
    public void changePlayer() {
        this.setTurn(!(this.getTurn()));
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
    public boolean getTurn() {
        return false;
    }

    @Override
    public Tile[][] getBoard() {
        return new Tile[0][];
    }

    @Override
    public int getColumns() {
        return 0;
    }

    @Override
    public int getrows() {
        return 0;
    }

    @Override
    public int getWhoHasWon() {
        return 0;
    }

    @Override
    public boolean getIsFull() {
        return false;
    }

    @Override
    public void placeStone(int column) {
        // TODO eine Methode, die einen Stein an die richtige Stelle setzt
        this.checkStatus(column);
        this.changePlayer();
    }
   
   int isTopOfColumn(int column){ // gibt die reihe des obersten Steins einer Spalte wieder.
        return 0;
   }

}
