package Tiles;

import Cards.RoomCard;

public class RoomTile extends AccessibleTile {

    boolean isEntrance;
    RoomCard.rooms room;

    public RoomTile() {
        super();
    }

    public RoomTile(RoomCard.rooms r) {
        super();
        room = r;
    }

    public void delete() {
        super.delete();
    }

}