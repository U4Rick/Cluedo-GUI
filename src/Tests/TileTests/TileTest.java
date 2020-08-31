package Tests.TileTests;

import Game.Cards.CharacterCard;
import Game.Board.Board;
import Game.Entities.Player;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.Test;

import static Game.Cards.CharacterCard.CharacterEnum.SCARLETT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TileTest {

    Tile tile = new Tile(new Position(0, 0));

    @Test
    public void getPlayerOnThisTile() {
        assertNull(tile.getPlayerOnThisTile());
    }

    @Test
    public void setPlayerOnThisTile() {
        Player player = new Player(new CharacterCard(SCARLETT), tile, new Board(), "test");
        tile.setPlayerOnThisTile(player);
        assertEquals(tile.getPlayerOnThisTile(), player);
    }

    @Test
    public void getPosition() {
        assertEquals(tile.getPosition(), new Position(0, 0));
    }
}