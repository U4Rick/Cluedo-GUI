package Tiles;

import Cards.RoomCard;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

public class RoomTile extends AccessibleTile {

    private boolean isEntrance;
    private RoomCard.rooms room;

    public RoomTile(Position pos) {
        super(pos);
    }

    public RoomTile(RoomCard.rooms r, Position pos) {
        super(pos);
        room = r;
    }

    public void delete() {
        super.delete();
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    @Override
    public String toString() {
        if (isEntrance) {
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
        else { return  "  "; }
    }
}