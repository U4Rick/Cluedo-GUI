package Tiles;

public class InaccessibleTile extends Tile {

    public InaccessibleTile(Position pos) {
        super(pos);
    }

    public void delete() {
    }

    @Override
    public void draw() {
        return;
    }

    @Override
    public String toString() {
        return "~~";
    }
}