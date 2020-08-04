package Tiles;

import Cards.RoomCard;

public class RoomTile extends AccessibleTile {

    boolean isEntrance;
    RoomCard.rooms room;

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

}