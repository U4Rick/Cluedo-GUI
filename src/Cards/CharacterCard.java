package Cards;

public class CharacterCard implements Card {

    characters character;
    public CharacterCard(characters c) {
        character = c;
    }

    public enum characters {
        SCARLETT, MUSTARD, WHITE, GREEN, PEACOCK, PLUM
    }

    public String convertToFullName(characters c) {
        switch (c) {
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

    //------------------------
    // INTERFACE
    //------------------------

    public void delete() {
    }

    public characters getCharacter() { return character; }
}