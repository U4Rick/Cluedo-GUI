package Tiles;

public class RoomTile extends InaccessibleTile {

    public RoomTile(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "  ";
    }
}
