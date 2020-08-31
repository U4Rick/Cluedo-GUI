package Tests.TileTests;

import Cards.CharacterCard;
import Cards.WeaponCard;
import GameMechanics.Board;
import GameMechanics.Player;
import GameMechanics.Weapon;
import Tiles.Position;
import Tiles.Tile;
import org.junit.jupiter.api.Test;

import static Cards.CharacterCard.CharacterEnum.SCARLETT;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile tile = new Tile(new Position(0, 0));

    @Test
    void getPlayerOnThisTile() {
        assertNull(tile.getPlayerOnThisTile());
    }

    @Test
    void setPlayerOnThisTile() {
        Player player = new Player(new CharacterCard(SCARLETT), tile, new Board(), "test");
        tile.setPlayerOnThisTile(player);
        assertEquals(tile.getPlayerOnThisTile(), player);
    }

    @Test
    void getPosition() {
        assertEquals(tile.getPosition(), new Position(0, 0));
    }
}