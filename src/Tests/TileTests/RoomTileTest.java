package Tests.TileTests;

import Cards.WeaponCard;
import GameMechanics.Weapon;
import Tiles.Position;
import Tiles.RoomTile;
import org.junit.jupiter.api.Test;

import static Cards.RoomCard.RoomEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomTileTest {

    RoomTile roomTile = new RoomTile(BALLROOM, new Position(0, 0));

    @Test
    void getRoom() {
        assertEquals(roomTile.getRoom(), BALLROOM);
    }

    @Test
    void getWeaponOnThisTile() {
        assertNull(roomTile.getWeaponOnThisTile());
    }

    @Test
    void setWeaponOnThisTile() {
        Weapon weapon = new Weapon(new WeaponCard(WeaponCard.WeaponEnum.CANDLESTICK));
        roomTile.setWeaponOnThisTile(weapon);
        assertEquals(roomTile.getWeaponOnThisTile(), weapon);
    }
}