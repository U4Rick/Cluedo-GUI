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

    public void delete() {
        super.delete();
    }

    @Override
    public String toString() {
        switch (room) {
                case HALL:
                    return "HL";
                case STUDY:
                    return "ST";
                case LOUNGE:
                    return "LG";
                case KITCHEN:
                    return "KT";
                case LIBRARY:
                    return "LB";
                case BALLROOM:
                    return "BR";
                case DININGROOM:
                    return "DR";
                case BILLIARDROOM:
                    return  "BL";
                case CONSERVATORY:
                    return "CT";
                default:
                    return "  ";
        }
    }
}