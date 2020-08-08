package Cards;

import java.util.Objects;

/**
 * Character card for the game of Cluedo.
 */
public class CharacterCard implements Card {

    private final CharacterEnum character;

    /**
     * Initialise the CharacterCard.
     * @param character  Character to initialise as.
     */
    public CharacterCard(CharacterEnum character) {
        this.character = character;
    }

    /**
     * Enum values for all possible characters.
     */
    public enum CharacterEnum {
        SCARLETT, MUSTARD, WHITE, GREEN, PEACOCK, PLUM
    }

    /**
     * Get the character associated with this card.
     * @return  the character
     */
    public CharacterEnum getCharacter() {
        return character;
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