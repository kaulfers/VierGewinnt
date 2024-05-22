package logic;

import api.BoardInterface;
import api.BoardTestInterface;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import java.io.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Board implements BoardInterface,BoardTestInterface {
    boolean isPlayer1sTurn; //ist Spieler eins am Zug?
    Tile[][] board; // das Spielfeld 	//Array[rows][columns]
    int whoHasWon; //0: niemand     1: Spieler1     2:Spieler2
    boolean isFull; // wenn das Spielfeld voll ist und keiner gewonnen hat
    final private String savepath = "savestats.txt"; //filepath + name, where the savefile is placed

    //vollständiger Konstruktor
    public Board(boolean iP1T, int columns, int rows, int wHW, boolean iF){
        isPlayer1sTurn = iP1T;
        board = new Tile[rows][columns];
        whoHasWon = wHW;
        isFull = iF;
        fillBoard();
    }
    // legt nur die Größe fest
    public Board(int columns, int rows){
        isPlayer1sTurn = true;
        board = new Tile[rows][columns];
        whoHasWon = 0;
        isFull = false;
        fillBoard();
    }
    // Standard Konstruktor, macht das Spielfeld in normaler Größe
    public Board(){
        isPlayer1sTurn = true;
        board = new Tile[6][7];
        whoHasWon = 0;
        isFull = false;
        fillBoard();
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

    private void fillBoard(){
        for(int i = 0; i < board.length; i++){
            for(int l = 0; l < board[0].length; l++){
                board[i][l] = new Tile();
            }
        }
    }

    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < board.length; i++){
            for(int l = 0; l < board[0].length; l++){
                ret = ret + board[i][l].toString();
            }
            ret = ret + "\n";
        }
        return ret;
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
        if (row == 0) {
            throw new IllegalArgumentException("Column is full");}
        if (isPlayer1sTurn) {
            board[row-1][column].setStatus(1);   // Spieler 1
        } else {
            board[row-1][column].setStatus(2);   // Spieler 2
        }
        checkStatus(column);
        changePlayer();
    }

    /**
    *    Gibt die Reihe des obersten Steins einer Spalte zurück.
    *    @param column Die Spalte, für die die oberste Reihe zurückgegeben werden soll.
    *    @return Die Reihe des obersten Steins in der Spalte, oder AnzahlRows + 1, wenn leer 
    */
    int isTopOfColumn(int column) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][column].isEmpty()) {
                return row+1;
            }
        }
        return 0; // Spalte ist voll
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
    *   @param value Die neue Matrix von Kacheln für das Spielfeld.
    */
    @Overwrite
    public void setBoard(Tile[][] value){
		int row = value.length;
		int column = value[0].length;
		
		this.board = new Tile[row][column];
		
		for (int r=0;r<row;r++){
			for (int c=0;c<column;c++){
				this.board[r][c] = value[r][c];
			}
		}
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
     * Saves the Board-Class with all their variables in a textfile.
     * 
     */
    @Overwrite
	public void saveBoard(){

		File savefile = new File(this.savepath);
		String savecode;
		
		//löschen des alten savefiles
		if (savefile.exists()){
			savefile.delete();
		}
		
		//erstellen speicherfile, wenn noch nicht vorhanden
		try{
			
			if (savefile.createNewFile() ){
				System.out.println("File created in: '" + this.savepath + "'");
			}
			else{
				System.out.println("File '" + this.savepath + "' exist.");
			}
			
		} catch(Exception e){
			//throw new IOException("File konnte nicht erstellt werden.");
			System.err.println(e);
		}

		//erstellen speichercode
		savecode = this.createSavecode();
		
		//schreiben des speichercodes in die speicherfile
		try{ 
			FileWriter fw = new FileWriter(savefile);
			fw.write(savecode);
			fw.close();						//schließen der speicherfile
		} catch (Exception e){ 
			 e.getStackTrace();
		}
	
	}

    /**
     * Creates a String, which contains all the information of the Board
     * 
     * Example: 2a3a4a0aB0100201101
     * a is a seperator for the numbers
     * 2 -> which players turn it is   | isPlayer1sTurn
     * 3 -> rows
     * 4 -> columns
     * 0 -> value of isFull            | isFull
     * number after 'B' -> status of Tile from top left to bottom right | board
     * 
     */
	private String createSavecode(){
		int row = getrows();
		int column = getColumns();
		String seperator = "a";
		
		String output = "";
		
		//hinzufügen von playerturn
		if (this.isPlayer1sTurn){
			output += "1";
		}
		else{
			output += "2";
		}
		output += seperator;		//seperator einfügen
 
		//hinzufügen von row
		output += Integer.toString(row);
		output += seperator;		//seperator einfügen
		
		//hinzufügen von column
		output += Integer.toString(column);
		output += seperator;		//seperator einfügen
		
		//hinzufügen von isFull
		if (this.isFull){
			output += "1";
		}
		else{
			output += "0";
		}
		output += seperator;		//seperator einfügen
		
		
		//einfügen des Boards
		output += "B";		//seperator einfügen
		
		for(Tile[] r: this.board){
			for(Tile c: r){
				output += c.toString();
			}
		}
		
		return output;
		
		
	}

    /**
     * Overwrites the variables of the Board with the values from the savefile
     * 
     */
    @Overwrite
    public void overwriteVariableWithSavestats(){
		String code = getSavecodeFromFile();
		
		if (!isSavecodeValid(code)){
			System.out.println("Speichercode ist fehlerhaft.");
			return;
		}
		
		setValuesFromSavecode(code);
		
		
	}

    /**
     * gets the param from the savecode and puts them into the variables of the instance
     */
	private void setValuesFromSavecode(String savecode){
		Tile[][] spFeld;
		boolean player1Turn = false;
		boolean full = false;
		int row = -1;
		int column = -1;
		
		char[] scCh = savecode.toCharArray();	//string zu array von chars
		int countA = 0;
		String v = "";
		boolean checkB = false;
		int r=0;
		int c=0;
		
		//iteration über jeden einzelnen char von String savecode
		for (char ch : scCh){
			//wenn a
			if (ch == 'a'){
				
				switch(countA){
				case 0:
					if (v == "1"){
						player1Turn = true;
					}
					else if (v == "2"){
						player1Turn = false;
					}
					break;
				case 1:
					row = Integer.parseInt(v);
					break;
				case 2:
					column = Integer.parseInt(v);
					break;
				case 3:
					if (v == "1"){
						full = true;
					}
					else if (v == "0"){
						full = false;
					}
					break;
				default:
					System.out.println("countA in werteVonSavecode ist größer als 3: " + countA);
					break;
				}
				
				countA += 1;
				v = "";		//zurücksetzen von v
			}
			//wenn B
			else if (ch == 'B'){
				break;
			}
			else{
				v += Character.toString(ch);
			}
		}
    //füllen des spielfeldVektors
		spFeld = new Tile[row][column];
		for (char ch : scCh){
			
			if (checkB == false){
				if (ch == 'B'){
					checkB = true;

				}
			}
			else{

				spFeld[r][c] = Character.getNumericValue(ch);
				
				if (c < column-1){
					c += 1;
				}
				else{
					c = 0;
					r +=1;
				}

			}
			
		}
		
		this.setBoard(spFeld);
        this.setIsFull(full);
        this.setTurn(player1Turn);
	}

    /**
     * Reads the Savefile and retrieves the code from it.
     * @return the String in the savefile
     */
	private String getSavecodeFromFile(){
		
		File savefile = new File(savepath);
		FileReader fr = null;
		int ch;
		String output = "";
		
		// check if File exists or not 
		try
		{ 
			fr = new FileReader(savefile);
		} 
		catch (FileNotFoundException fe) 
		{ 
			System.out.println("File not found"); 
		} 
		
		// read from FileReader till the end of file 
		try{
			while ((ch=fr.read())!=-1) 
				output += (char)ch;
			
			fr.close();		//close the file 
		} catch(Exception e){
			System.err.println(e);
		} 

	
		return output;
	}

    /**
     * Checks, if the given String is useable as Savecode.
     * @return if the String can be used as Savecode
     */
    private boolean isSavecodeValid(String code){
		
		Pattern savecodePattern = Pattern.compile("[1-2]a[0-9]+a[0-9]+a[0-1]aB[0-2]*");	//factory, Builder-Pattern -> verhindert falsches Instanziieren; compile .. funktion, die das instanziieren übernimmt-
		Matcher matcher = savecodePattern.matcher(code);
		
		return matcher.find();
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

    /**
     * overwrites the board with a given input array
     */
    @Overwrite
    public void setBoard(Tile[][] value){
		int row = value.length;
		int column = value[0].length;
		
		this.board = new Tile[row][column];
		
		for (int r=0;r<row;r++){
			for (int c=0;c<column;c++){
				this.board[r][c] = value[r][c];
			}
		}
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
