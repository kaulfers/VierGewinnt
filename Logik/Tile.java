package Logik;

public class Tile { // Das ist die Kachel, also das wo dich "Chips" reinfallen
    int status; //0:leer 1:Spieler1 2:Spieler2

    Tile(int s){
        status = s;
    }
    Tile(){
        status = 0;
    }

    void setStatus(int s){
        status = s;
    }
    int getStatus(){
        return status;
    }
}
