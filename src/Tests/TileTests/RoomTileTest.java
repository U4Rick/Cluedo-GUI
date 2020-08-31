package Tests.TileTests;

import Game.Cards.WeaponCard;
import Game.Entities.Weapon;
import Game.Tiles.Position;
import Game.Tiles.RoomTile;
import org.junit.Test;

import static Game.Cards.RoomCard.RoomEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoomTileTest {

    RoomTile roomTile = new RoomTile(BALLROOM, new Position(0, 0));

    @Test
    public void getRoom() {
        assertEquals(roomTile.getRoom(), BALLROOM);
    }

    @Test
    public void getWeaponOnThisTile() {
        assertNull(roomTile.getWeaponOnThisTile());
    }

    @Test
    public void setWeaponOnThisTile() {
        Weapon weapon = new Weapon(new WeaponCard(WeaponCard.WeaponEnum.CANDLESTICK));
        roomTile.setWeaponOnThisTile(weapon);
        assertEquals(roomTile.getWeaponOnThisTile(), weapon);
    }
}