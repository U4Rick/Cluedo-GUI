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

    public String getFileName() {
        return this.toString().replaceAll("\\s", "") + ".png";
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
        switch (character) {
            case SCARLETT:
                return "Miss Scarlett";
            case WHITE:
                return "Mrs White";
            case GREEN:
                return "Mr Green";
            case PLUM:
                return "Professor Plum";
            case MUSTARD:
                return "Colonel Mustard";
            case PEACOCK:
                return "Mrs Peacock";
            default:
                throw new IllegalArgumentException();
        }
    }
}