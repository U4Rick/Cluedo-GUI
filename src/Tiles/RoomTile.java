package Tiles;

/**
 * Textual placeholder for room tiles (which are inaccessible by the player).
 */
public class RoomTile extends InaccessibleTile {

    /**
     * Creates the tile at given position.
     * @param pos   Position of the tile.
     */
    public RoomTile(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "  ";
    }
}
