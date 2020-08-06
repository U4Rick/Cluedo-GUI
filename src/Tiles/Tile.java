package Tiles;

public class Tile {

    public Position position;

    public Tile(Position position) {
        this.position = position;
    }




    public void draw(){}

    //todo implement
    public boolean hasPlayer() {
        return false;
    }

    public Position getPosition() {
        return this.position;
    }
}