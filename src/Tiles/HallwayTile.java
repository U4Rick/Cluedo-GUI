package Tiles;

import Cards.CharacterCard;
import GameMechanics.Player;

public class HallwayTile extends AccessibleTile {

    private boolean startingTile;
    private CharacterCard startingCharacter;
    private Player playerOnThisTile = null;

    //TODO: add two constructors, one for startingTile true, one for false, true has a characterCard param

    public HallwayTile(Position pos) {
        super(pos);
        startingTile = false;
    }

    public HallwayTile(CharacterCard c, Position pos) {
        super(pos);
        startingTile = true;
        startingCharacter = c;
    }

    public void delete() {
        super.delete();
    }

    public void setPlayerOnThisTile(Player playerOnThisTile) {
        this.playerOnThisTile = playerOnThisTile;
    }

    @Override
    public String toString() {
        if (playerOnThisTile != null) { return playerOnThisTile.toString(); }
        else { return "__"; }
    }
}