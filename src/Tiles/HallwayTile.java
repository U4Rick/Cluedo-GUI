package Tiles;

import Cards.CharacterCard;

public class HallwayTile extends AccessibleTile {

    private boolean startingTile;
    private CharacterCard startingCharacter;

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

}