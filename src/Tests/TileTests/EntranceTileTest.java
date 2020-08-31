package Tests.TileTests;

import Tiles.EntranceTile;
import Tiles.Position;
import org.junit.jupiter.api.Test;

import static Cards.RoomCard.RoomEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class EntranceTileTest {

    EntranceTile entranceTile = new EntranceTile(BALLROOM, new Position(0, 0));

    @Test
    void getRoom() {
        assertEquals(entranceTile.getRoom(), BALLROOM);
    }

    @Test
    void testToString() {
        assertEquals(entranceTile.toString(), "BR");
    }
}