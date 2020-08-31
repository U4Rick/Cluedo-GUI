package Tests;

import Cards.CharacterCard;

import static Cards.CharacterCard.CharacterEnum;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    private final CharacterCard card = new CharacterCard(CharacterEnum.SCARLETT);

    @org.junit.jupiter.api.Test
    void getFileName() {
        assertEquals(card.getFileName(), "MissScarlett.png");
    }

    @org.junit.jupiter.api.Test
    void getCharacter() {
        assertEquals(card.getCharacter(), CharacterEnum.SCARLETT);
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        CharacterCard same = new CharacterCard(CharacterEnum.SCARLETT);
        CharacterCard different = new CharacterCard(CharacterEnum.PLUM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals(card.toString(), "Miss Scarlett");
    }
}