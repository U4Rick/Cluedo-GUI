package Tiles;

import Cards.CharacterCard;
import GameMechanics.Player;

/**
 * Hallway Tiles, can only have one player on tile, way to move
 * around the board.
 */
public class HallwayTile extends AccessibleTile {

    private boolean startingTile;
    private CharacterCard startingCharacter;
    private Player playerOnThisTile;

    public Player getPlayerOnThisTile() {
        return playerOnThisTile;
    }

    /**
     * Creates a non starting tile.
     * @param pos   Position of the tile.
     */
    public HallwayTile(Position pos) {
        super(pos);
        startingTile = false;
    }

    /**
     * Creates a starting tile with a specific character.
     * @param c     Character that starts there.
     * @param pos   Position of the tile.
     */
    public HallwayTile(CharacterCard c, Position pos) {
        super(pos);
        startingTile = true;
        startingCharacter = c;
    }


    /**
     * Sets there to be a player on the tile, can be null if no player.
     * @param playerOnThisTile  The player (or null value) to place here.
     */
    public void setPlayerOnThisTile(Player playerOnThisTile) {
        this.playerOnThisTile = playerOnThisTile;
    }

    @Override
    public String toString() {
        if (playerOnThisTile != null) { return playerOnThisTile.toString(); }
        else { return "__"; }
    }
}