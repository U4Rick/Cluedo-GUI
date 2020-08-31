package Tests;

import Cards.WeaponCard;
import Cards.WeaponCard.WeaponEnum;
import GameMechanics.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponCardTest {

    private final WeaponCard card = new WeaponCard(WeaponEnum.CANDLESTICK);

    @Test
    void getFileName() {
        assertEquals(card.getFileName(), "Candlestick.png");
    }

    @Test
    void testEquals() {
        WeaponCard same = new WeaponCard(WeaponEnum.CANDLESTICK);
        WeaponCard different = new WeaponCard(WeaponEnum.DAGGER);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    void testToString() {
        assertEquals(card.toString(), "Candlestick");
    }
}