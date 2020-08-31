package Tests.TileTests;

import Game.Tiles.EntranceTile;
import Game.Tiles.Position;
import org.junit.Test;

import static Game.Cards.RoomCard.RoomEnum.*;
import static org.junit.Assert.assertEquals;


public class EntranceTileTest {

    EntranceTile entranceTile = new EntranceTile(BALLROOM, new Position(0, 0));

    @Test
    public void getRoom() {
        assertEquals(entranceTile.getRoom(), BALLROOM);
    }

    @Test
    public void testToString() {
        assertEquals(entranceTile.toString(), "BR");
    }
}