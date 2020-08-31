package Game.Tiles;

import Game.Entities.Player;

/**
 * GameMechanics.Tiles which represent areas on the board grid.
 */
public class Tile {

    protected Player playerOnThisTile;
    public final Position position;

    /**
     * Creates a tile at position given.
     * @param position  Position to place tile at.
     */
    public Tile(Position position) {
        this.position = position;
    }

    /**
     * Get the player, if any, that's on this tile.
     * @return  returns a Player if one is present, else null
     */
    public Player getPlayerOnThisTile() {
        return playerOnThisTile;
    }

    /**
     * Set the player on the tile.
     * @param playerOnThisTile  Player to set
     */
    public void setPlayerOnThisTile(Player playerOnThisTile) {
        this.playerOnThisTile = playerOnThisTile;
    }

    /**
     * Get the positioning of this tile.
     * @return the position
     */
    public Position getPosition() {
        return this.position;
    }
}