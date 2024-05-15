package Logik;

import api.BoardInterface;
import api.BoardTestInterface;

public class Board implements BoardInterface,BoardTestInterface {
    boolean isPlayer1sTurn; //ist Spieler eins am Zug?
    Tile[][] board; // das Spielfeld
    int whoHasWon; //0: niemand     1: Spieler1     2:Spieler2
    boolean isfull; // wenn das Spielfeld voll ist und keiner gewonnen hat

    //TODO Konstruktoren

    //TODO Setter und Getter

    @Override
    public void checkStatus(int column) {
        // TODO eine Methode die check wie der Status vom Spiel ist ung ggf. anpasst
        isWon(column);
        isfull();
   }

   void isWon(int column){

   }

   void isFull(){

   }

    @Override
    public void changePlayer() {
        // TODO eine Methode, die den Spieler wechselt
    }

    @Override
    public void placeStone(int column) {
        // TODO eine Methode, die einen Stein an die richtige Stelle setzt
        //  this.checkStatus(1);
    }
   
}
