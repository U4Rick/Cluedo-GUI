package GameMechanics.Action;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;

import java.util.Objects;

public class Hypothesis {

    //GameMechanics.Action.Hypothesis Attributes
    private CharacterCard character;
    private WeaponCard weapon;
    private RoomCard room;

    public Hypothesis(CharacterCard character, WeaponCard weapon, RoomCard room) {
        this.character = character;
        this.weapon = weapon;
        this.room = room;
    }

    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    public void setRoom(RoomCard room) {
        this.room = room;
    }

    public CharacterCard getCharacter() {
        return character;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public RoomCard getRoom() {
        return room;
    }


    @Override
    public String toString() {
        return character + ", " + weapon +
                ", " + room;
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