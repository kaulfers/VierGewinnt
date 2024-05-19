package logic;

    /** 
    *   Diese Klasse repräsentiert eine einzelne Kachel im Spielbrett.
    *   Jede Kachel hat einen Status, der den Zustand des Feldes darstellt.
    */
public class Tile {
    int status; // 0: leer, 1: Spieler1, 2: Spieler2

    
    /**     
    *   Konstruktor für eine Kachel mit einem bestimmten Status.
    *   @param s Der Status der Kachel (0 für leer, 1 für Spieler1, 2 für Spieler2).
    */
Tile(int s) {
    status = s;}

    /**
    *   Standardkonstruktor für eine leere Kachel.
    */
  Tile() {
      status = 0;}

    /** 
    *    Setzt den Status der Kachel.
    *    @param s Der neue Status der Kachel.
    */
void setStatus(int s) {
    status = s;}

    /**  
    *   Gibt den aktuellen Status der Kachel zurück.
    *   @return Der Status der Kachel (0 für leer, 1 für Spieler1, 2 für Spieler2).
    */
int getStatus() {
    return status;}

    /**
    *   Überprüft, ob die Kachel leer ist.
    *   @return True, wenn die Kachel leer ist, ansonsten False.
    */
boolean isEmpty() {
    return status == 0;}
}