package Tiles;

import Cards.RoomCard;

/**
 * Textual placeholder for room tiles (which are inaccessible by the player).
 */
public class RoomTile extends InaccessibleTile {

    public RoomCard.RoomEnum room;

    /**
     * Creates the tile at given position.
     * @param pos   Position of the tile.
     */
    public RoomTile(RoomCard.RoomEnum room, Position pos) {
        super(pos);
        this.room = room;
    }

    @Override
    public String toString() {
        return "  ";
    }
}
