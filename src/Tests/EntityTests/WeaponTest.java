package Tests.EntityTests;

import Game.Cards.WeaponCard;
import Game.Entities.Weapon;
import org.junit.Before;
import org.junit.Test;


import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class WeaponTest {

    Weapon weapon = new Weapon(new WeaponCard(CANDLESTICK));

    @Before
    public void setUp() {
    }

    @Test
    public void getIcon() {
        assertNotNull(weapon.getIcon());
    }

    @Test
    public void getWeapon() {
        assertEquals(weapon.getWeapon(), new WeaponCard(CANDLESTICK));
    }
}