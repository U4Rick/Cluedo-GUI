package Cards;

public class CharacterCard implements Card {

    characters character;
    public CharacterCard(characters c) {
        character = c;
    }

    public enum characters {
        SCARLETT, MUSTARD, WHITE, GREEN, PEACOCK, PLUM
    }

    //------------------------
    // INTERFACE
    //------------------------

    public void delete() {
    }

}