package Tests.CardTests;

import Game.Cards.CharacterCard;
import org.junit.Test;

import static Game.Cards.CharacterCard.CharacterEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CharacterCardTest {

    private final CharacterCard card = new CharacterCard(SCARLETT);

    @Test
    public void getFileName() {
        assertEquals(card.getFileName(), "MissScarlett.png");
    }

    @Test
    public void getCharacter() {
        assertEquals(card.getCharacter(), SCARLETT);
    }

    @Test
    public void testEquals() {
        CharacterCard same = new CharacterCard(SCARLETT);
        CharacterCard different = new CharacterCard(PLUM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    public void testToString() {
        assertEquals(card.toString(), "Miss Scarlett");
    }
}