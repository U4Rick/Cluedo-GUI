package Tiles;

import static Cards.RoomCard.*;

/**
 * Entrance tiles, allows multiple players to be on and
 * is the only way to access a room, which are inaccessible.
 */
public class EntranceTile extends AccessibleTile {

    private final RoomEnum room;

    /**
     * Creates the EntranceTile object.
     * @param r     The room the entrance gives access to
     * @param pos   Position on the tile on the board
     */
    public EntranceTile(RoomEnum r, Position pos) {
        super(pos);
        room = r;
    }

    /**
     * Get the room this entrance leads to.
     * @return  the room
     */
    public RoomEnum getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return switch (room) {
            case HALL -> "HL";
            case STUDY -> "ST";
            case LOUNGE -> "LG";
            case KITCHEN -> "KT";
            case LIBRARY -> "LB";
            case BALLROOM -> "BR";
            case DININGROOM -> "DR";
            case BILLIARDROOM -> "BL";
            case CONSERVATORY -> "CT";
        };
    }
}