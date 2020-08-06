package Cards;

public class CharacterCard implements Card {

    private CharacterEnum character;

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
                return "";
        }
    }
}