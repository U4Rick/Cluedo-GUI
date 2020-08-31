package Tests.TileTests;

import Game.Cards.CharacterCard;
import Game.Board.Board;
import Game.Entities.Player;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.jupiter.api.Test;

import static Game.Cards.CharacterCard.CharacterEnum.SCARLETT;
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