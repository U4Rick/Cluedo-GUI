package Tiles;

import Cards.RoomCard;

public class EntranceTile extends AccessibleTile {

    private RoomCard.rooms room;

    public EntranceTile(RoomCard.rooms r, Position pos) {
        super(pos);
        room = r;
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