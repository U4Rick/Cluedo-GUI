/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5099.60569f335 modeling language!*/


import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;

// line 84 "model.ump"
// line 119 "model.ump"
public class Hypothesis {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Hypothesis Attributes
    private WeaponCard weapon;
    private RoomCard room;
    private CharacterCard character;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Hypothesis(WeaponCard aWeapon, RoomCard aRoom, CharacterCard aCharacter) {
        weapon = aWeapon;
        room = aRoom;
        character = aCharacter;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setWeapon(WeaponCard aWeapon) {
        boolean wasSet = false;
        weapon = aWeapon;
        wasSet = true;
        return wasSet;
    }

    public boolean setRoom(RoomCard aRoom) {
        boolean wasSet = false;
        room = aRoom;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacter(CharacterCard aCharacter) {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public RoomCard getRoom() {
        return room;
    }

    public CharacterCard getCharacter() {
        return character;
    }

    public void delete() {
    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this) ? getWeapon().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this) ? getRoom().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this) ? getCharacter().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}