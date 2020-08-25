package Tiles;

import java.util.Objects;

/**
 *  Position on the board.
 */
public class Position {

    private final int x;
    private final int y;

    /**
     * Create a new position.
     * @param x     X coordinate
     * @param y     Y coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds values to this position
     * @param x     The x position to add
     * @param y     The y position to add
     * @return      The new position
     */
    public Position add(int x, int y){
        return new Position(this.x + x, this.y + y);
    }

    /**
     * Return the x coordinate.
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y coordinate.
     * @return  y value
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
                "x" + ":" + getX() + "," +
                "y" + ":" + getY() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}