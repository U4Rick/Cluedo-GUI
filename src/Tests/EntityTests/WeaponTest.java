package Tests.EntityTests;

import Game.Cards.WeaponCard;
import Game.Entities.Weapon;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    Weapon weapon = new Weapon(new WeaponCard(CANDLESTICK));

    @BeforeEach
    void setUp() {
    }

    @Test
    void getIcon() {
        assertNotNull(weapon.getIcon());
    }

    @Test
    void getWeapon() {
        assertEquals(weapon.getWeapon(), new WeaponCard(CANDLESTICK));
    }
}