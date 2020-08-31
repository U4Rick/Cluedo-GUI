package Tests.TileTests;

import Game.Tiles.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position position = new Position(5, 6);

    @Test
    void add() {
        Position newPosition = position.add(2, 3);
        assertEquals(newPosition, new Position(7, 9));
    }

    @Test
    void getX() {
        assertEquals(position.getX(), 5);
    }

    @Test
    void getY() {
        assertEquals(position.getY(), 6);
    }

    @Test
    void testEquals() {
        Position same = new Position(5, 6);
        Position different = new Position(2, 3);

        assertEquals(position, same);
        assertNotEquals(position, different);
    }
}