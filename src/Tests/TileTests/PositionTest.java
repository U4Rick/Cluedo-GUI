package Tests.TileTests;

import Game.Tiles.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class PositionTest {

    Position position = new Position(5, 6);

    @Test
    public void add() {
        Position newPosition = position.add(2, 3);
        assertEquals(newPosition, new Position(7, 9));
    }

    @Test
    public void getX() {
        assertEquals(position.getX(), 5);
    }

    @Test
    public void getY() {
        assertEquals(position.getY(), 6);
    }

    @Test
    public void testEquals() {
        Position same = new Position(5, 6);
        Position different = new Position(2, 3);

        assertEquals(position, same);
        assertNotEquals(position, different);
    }
}