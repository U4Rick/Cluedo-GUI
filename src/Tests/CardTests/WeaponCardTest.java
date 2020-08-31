package Tests.CardTests;

import Game.Cards.WeaponCard;
import org.junit.Test;

import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WeaponCardTest {

    private final WeaponCard card = new WeaponCard(CANDLESTICK);

    @Test
    public void getFileName() {
        assertEquals(card.getFileName(), "Candlestick.png");
    }

    @Test
    public void testEquals() {
        WeaponCard same = new WeaponCard(CANDLESTICK);
        WeaponCard different = new WeaponCard(DAGGER);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    public void testToString() {
        assertEquals(card.toString(), "Candlestick");
    }
}