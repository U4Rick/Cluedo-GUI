package Tests.TileTests;

import Cards.CharacterCard;
import Cards.CharacterCard.CharacterEnum;
import GameMechanics.Board;
import GameMechanics.Player;
import Tiles.HallwayTile;
import Tiles.Position;
import org.junit.jupiter.api.Test;

import static Cards.CharacterCard.CharacterEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class HallwayTileTest {

    HallwayTile hallwayTile = new HallwayTile(new Position(0, 0));

    @Test
    void testToString_Empty() {
        assertEquals(hallwayTile.toString(), "__");
    }

    @Test
    void testToString_PlayerOnTile() {
        hallwayTile.setPlayerOnThisTile(new Player(new CharacterCard(SCARLETT), hallwayTile, new Board(), "test"));
        assertEquals(hallwayTile.toString(), "SC");
    }
}