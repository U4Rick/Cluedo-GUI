package Tiles;


import GameMechanics.Board;
import GameMechanics.BoardParser;
import GameMechanics.Game;
import GameMechanics.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Tiles which represent areas on the board grid.
 */
public class Tile {

    private Player playerOnThisTile;

    public Position position;
    private Board board;
    private Set<String> characters = new HashSet<>(Arrays.asList("MU", "WH", "GR", "PC", "PL", "SC"));


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