package Tiles;

import Cards.CharacterCard;

public class HallwayTile extends AccessibleTile {

    private boolean startingTile;
    private CharacterCard startingCharacter;

    //TODO: add two constructors, one for startingTile true, one for false, true has a characterCard param

    public HallwayTile() {
        super();
    }

    public void delete() {
        super.delete();
    }

}