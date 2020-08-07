package Tiles;

import Cards.RoomCard;

/**
 * Entrance tiles, allows multiple players to be on and
 * is the only way to access a room, which are inaccessible.
 */
public class EntranceTile extends AccessibleTile {

    private final RoomCard.RoomEnum room;


    /**
     * Creates the EntranceTile object.
     * @param r     The room the entrance gives access to
     * @param pos   Position on the tile on the board
     */
    public EntranceTile(RoomCard.RoomEnum r, Position pos) {
        super(pos);
        room = r;
    }

    public RoomCard.RoomEnum getRoom() {
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