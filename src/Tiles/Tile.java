package Tiles;

import GameMechanics.Player;

/**
 * Tiles which represent areas on the board grid.
 */
public class Tile {

    private Player playerOnThisTile;
    public Position position;

    /**
     * Creates a tile at position given.
     * @param position  Position to place tile at.
     */
    public Tile(Position position) {
        this.position = position;
    }

    public Player getPlayerOnThisTile() {
        return playerOnThisTile;
    }

    public void setPlayerOnThisTile(Player playerOnThisTile) {
        this.playerOnThisTile = playerOnThisTile;
    }

    public Position getPosition() {
        return this.position;
    }
}