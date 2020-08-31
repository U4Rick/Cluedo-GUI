package Tests.CardTests;

import Game.Cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class WeaponCardTest {

    private final WeaponCard card = new WeaponCard(CANDLESTICK);

    @Test
    void getFileName() {
        assertEquals(card.getFileName(), "Candlestick.png");
    }

    @Test
    void testEquals() {
        WeaponCard same = new WeaponCard(CANDLESTICK);
        WeaponCard different = new WeaponCard(DAGGER);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    void testToString() {
        assertEquals(card.toString(), "Candlestick");
    }
}