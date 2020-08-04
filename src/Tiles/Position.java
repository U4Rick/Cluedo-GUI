package Tiles;

public class Position {

    //Tiles.Position Attributes
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean setX(int x) {
        boolean wasSet = false;
        this.x = x;
        wasSet = true;
        return wasSet;
    }

    public boolean setY(int y) {
        boolean wasSet = false;
        this.y = y;
        wasSet = true;
        return wasSet;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void delete() {
    }


    public String toString() {
        return super.toString() + "[" +
                "x" + ":" + getX() + "," +
                "y" + ":" + getY() + "]";
    }
}