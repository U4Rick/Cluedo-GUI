package Cards;

public class CharacterCard implements Card {

    private final CharacterEnum character;

    public CharacterCard(CharacterEnum character) {
        this.character = character;
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