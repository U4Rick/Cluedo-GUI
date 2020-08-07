package Cards;

import java.util.Objects;

/**
 * Character card for the game of Cluedo.
 */
public class CharacterCard implements Card {

    private final CharacterEnum character;

    public CharacterCard(CharacterEnum character) {
        this.character = character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterCard that = (CharacterCard) o;
        return character == that.character;
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

    public enum CharacterEnum {
        SCARLETT, MUSTARD, WHITE, GREEN, PEACOCK, PLUM
    }

    public CharacterEnum getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return switch (character) {
            case SCARLETT -> "Miss Scarlett";
            case WHITE -> "Mrs White";
            case GREEN -> "Mr Green";
            case PLUM -> "Professor Plum";
            case MUSTARD -> "Colonel Mustard";
            case PEACOCK -> "Mrs Peacock";
        };
    }
}