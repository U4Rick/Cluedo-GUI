package Tiles;

public class Tile {

    Position p;

    public Tile(Position pos) {
        p = pos;
    }

    public void draw(){}

    //todo implement
    public boolean hasPlayer() {
        return false;
    }

    public Position getP() {
        return this.p;
    }
}