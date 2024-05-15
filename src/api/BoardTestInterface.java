package api;

public interface BoardTestInterface {
    void placeStone(int column); // diese Methode legt einen Stein, überprüft den Status und wechselt den Spieler
    void checkStatus(int column); // überprüft den Status des Spiels, ob gewonnen oder voll
    void changePlayer(); // wechselt den Spieler
    // sagt uns falls ihr noch was braucht
}
