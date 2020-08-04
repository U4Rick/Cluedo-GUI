package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;

public class Hypothesis {

    //GameMechanics.Hypothesis Attributes
    private WeaponCard weapon;
    private RoomCard room;
    private CharacterCard character;

    public Hypothesis(WeaponCard weapon, RoomCard room, CharacterCard character) {
        this.weapon = weapon;
        this.room = room;
        this.character = character;
    }

    public boolean setWeapon(WeaponCard weapon) {
        boolean wasSet = false;
        this.weapon = weapon;
        wasSet = true;
        return wasSet;
    }

    public boolean setRoom(RoomCard room) {
        boolean wasSet = false;
        this.room = room;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacter(CharacterCard character) {
        boolean wasSet = false;
        this.character = character;
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