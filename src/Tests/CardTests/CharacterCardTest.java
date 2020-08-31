package Tests.CardTests;

import Game.Cards.CharacterCard;
import org.junit.jupiter.api.Test;

import static Game.Cards.CharacterCard.CharacterEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    private final CharacterCard card = new CharacterCard(SCARLETT);

    @Test
    void getFileName() {
        assertEquals(card.getFileName(), "MissScarlett.png");
    }

    @Test
    void getCharacter() {
        assertEquals(card.getCharacter(), SCARLETT);
    }

    @Test
    void testEquals() {
        CharacterCard same = new CharacterCard(SCARLETT);
        CharacterCard different = new CharacterCard(PLUM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    void testToString() {
        assertEquals(card.toString(), "Miss Scarlett");
    }
}