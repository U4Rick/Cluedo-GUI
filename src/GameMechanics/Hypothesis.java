package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;

import java.util.Objects;

public class Hypothesis {

    //GameMechanics.Hypothesis Attributes
    private CharacterCard character;
    private WeaponCard weapon;
    private RoomCard room;

    public Hypothesis(CharacterCard character, WeaponCard weapon, RoomCard room) {
        this.character = character;
        this.weapon = weapon;
        this.room = room;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hypothesis that = (Hypothesis) o;
        return character.equals(that.character) &&
                weapon.equals(that.weapon) &&
                room.equals(that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, weapon, room);
    }
}