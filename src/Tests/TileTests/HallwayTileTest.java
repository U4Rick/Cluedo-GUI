package Tests.TileTests;

import Game.Cards.CharacterCard;
import Game.Board.Board;
import Game.Entities.Player;
import Game.Tiles.HallwayTile;
import Game.Tiles.Position;
import org.junit.Test;

import static Game.Cards.CharacterCard.CharacterEnum.*;
import static org.junit.Assert.assertEquals;

public class HallwayTileTest {

    HallwayTile hallwayTile = new HallwayTile(new Position(0, 0));

    @Test
    public void testToString_Empty() {
        assertEquals(hallwayTile.toString(), "__");
    }

    @Test
    public void testToString_PlayerOnTile() {
        hallwayTile.setPlayerOnThisTile(new Player(new CharacterCard(SCARLETT), hallwayTile, new Board(), "test"));
        assertEquals(hallwayTile.toString(), "SC");
    }
}