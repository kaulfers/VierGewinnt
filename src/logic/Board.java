package logic;

import api.BoardInterface;
import api.BoardTestInterface;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
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

    private Board(Board board) {
        int rows = board.board.length;
        int columns = board.board[0].length;
        this.board = new Tile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.board[i][j] = new Tile();
                this.board[i][j].setStatus(board.board[i][j].getStatus());
            }
        }
        this.isPlayer1sTurn = board.isPlayer1sTurn;
        this.whoHasWon = board.whoHasWon;
        this.isFull = board.isFull;
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
     * Calculates the next move for the computer player using the minimax algorithm.
     * For better explanation of the algorithm, see the method miniMax.
     * @see #miniMax(Board, int, int, int, int)
     * @param board Das aktuelle Spielbrett
     * @return Die Spalte in dem der Token platziert werden soll
     */
    public int getComputerMove(Board board){
        int move;
        if (-1 != (move = canMakeWinningMove(board))) {
            return move;
        } else if (-1 != (move = canBlockOpponent(board))) {
            return move;
        }
        int currentPlayer = board.isPlayer1sTurn ? 1 : 2;
        return miniMax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer).column;
    }

    /**
     * Uses the minimax algorithm to determine the best move for the computer.
     * The algorithm simulates all possible moves and evaluates the resulting board states.
     * @param board the current game board
     * @param depth the maximum depth of the search tree
     * @param alpha the best value that the maximizer currently can guarantee at that level or above
     * @param beta the best value that the minimizer currently can guarantee at that level or above
     * @param maximizingPlayer the player who is currently maximizing their score
     * @return a Result object containing the best move and its score
     */
    private Result miniMax(Board board, int depth, int alpha, int beta, int maximizingPlayer) {
        Random random = new Random();
        if (depth == 0 || board.isFull) {
            if (board.whoHasWon == maximizingPlayer) {
                return new Result(-1, 100);
            } else if (board.whoHasWon == (maximizingPlayer == 1 ? 2 : 1)) {
                return new Result(-1, -100);
            } else if (board.isFull){
                return new Result(-1, 0);
            } else {
                return (new Result(-1, scorePosition(board, maximizingPlayer) + random.nextInt(-10, 10)));
            }
        }
        if (maximizingPlayer == (board.isPlayer1sTurn ? 1 : 2)) {
            int maxEval = Integer.MIN_VALUE;
            List<Integer> possibleMoves = getAllPossibleMoves(board);
            int column = possibleMoves.get(random.nextInt(possibleMoves.size()));
            for (int i = 0; i < possibleMoves.size(); i++) {
                Board newBoard = new Board(board);
                newBoard.placeStone(possibleMoves.get(i));
                int eval = miniMax(newBoard, depth - 1, alpha, beta,maximizingPlayer).score;
                if (eval > maxEval) {
                    maxEval = eval;
                    column = possibleMoves.get(i);
                }
                alpha = Math.max(alpha, eval);

                if (alpha >= beta) {
                    break;
                }
            }
            return new Result(column, maxEval);
        } else {
            int minEval = Integer.MAX_VALUE;
            List<Integer> possibleMoves = getAllPossibleMoves(board);
            int column = possibleMoves.get(random.nextInt(possibleMoves.size()));
            for (int i = 0; i < possibleMoves.size(); i++) {
                Board newBoard = new Board(board);
                newBoard.placeStone(possibleMoves.get(i));
                int eval = miniMax(newBoard, depth - 1, alpha, beta, maximizingPlayer).score;
                if (eval < minEval) {
                    minEval = eval;
                    column = possibleMoves.get(i);
                }
                beta = Math.min(beta, eval);

                if (alpha >= beta) {
                    break;
                }
            }
            return new Result(column, minEval);
        }
    }

    private int canMakeWinningMove(Board board) {
        List<Integer> possibleMoves = getAllPossibleMoves(board);
        for (int i = 0; i < possibleMoves.size(); i++) {
            Board newBoard = new Board(board);
            newBoard.placeStone(possibleMoves.get(i));
            if (newBoard.whoHasWon == (board.isPlayer1sTurn ? 1 : 2)) {
                return possibleMoves.get(i);
            }
        }
        return -1;
    }

    private int canBlockOpponent(Board board) {
        List<Integer> possibleMoves = getAllPossibleMoves(board);
        for (int i = 0; i < possibleMoves.size(); i++) {
            Board newBoard = new Board(board);
            newBoard.changePlayer();
            newBoard.placeStone(possibleMoves.get(i));
            if (newBoard.whoHasWon == (board.isPlayer1sTurn ? 2 : 1)) {
                return possibleMoves.get(i);
            }
        }
        return -1;
    }

    /**
     * Scores the position of the maximizing player depending on the position of the tokens on the board
     * Gives higher score to tokens closer to the middle of the board and closer to the bottom, prioritizing the middle
     * @param board the current board
     * @param maximizingPlayer the player for whom the position is scored
     * @return the score of the position for the maximizing player
     */
    private int scorePosition(Board board, int maximizingPlayer) {
        int score = 0;
        int opponent = maximizingPlayer == 1 ? 2 : 1;
        int middleColumn = board.getBoard()[0].length / 2;

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                if (board.getBoard()[i][j].getStatus() == maximizingPlayer) {
                    score += (middleColumn - Math.abs(middleColumn - j) * 2) + (board.getBoard().length - (board.getBoard().length - i));
                } else if (board.getBoard()[i][j].getStatus() == opponent) {
                    score -= (middleColumn - Math.abs(middleColumn - j) * 2) + (board.getBoard().length - (board.getBoard().length - i));
                }
            }
        }
        return score;
    }

    private List<Integer> getAllPossibleMoves(Board board) {
        int columns = board.getBoard()[0].length;
        List<Integer> possibleMoves = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            if (board.board[0][i].isEmpty()) {
                possibleMoves.add(i);
            }
        }
        return possibleMoves;
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

    private class Result {
        private int column;
        private int score;

        private Result(int column, int score) {
            this.column = column;
            this.score = score;
        }
    }
}
