package Tiles;

/**
 * Hallway Tiles, can only have one player on tile, way to move
 * around the board.
 */
public class HallwayTile extends AccessibleTile {

    /**
     * Creates a non starting tile.
     * @param pos   Position of the tile.
     */
    public HallwayTile(Position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        if (playerOnThisTile != null) { return playerOnThisTile.toString(); }
        else { return "__"; }
    }
}