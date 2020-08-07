package Tiles;

/**
 * Tiles around the board that cannot be accessed by the player but are necessary
 * for the board layout.
 */
public class InaccessibleTile extends Tile {

    /**
     * Creates a inaccessible tile at the position given.
     * @param pos   Position which the tile is at.
     */
    public InaccessibleTile(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "~~";
    }
}