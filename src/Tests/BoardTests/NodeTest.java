package Tests.BoardTests;

import Game.Board.Node;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NodeTest {

    Node node;
    Node same;
    Node differentDist;
    Node differentTile;

    @Before
    public void setUp() {
        Tile tile = new Tile(new Position(0, 0));
        Tile diffTile = new Tile(new Position(1, 1));

        node = new Node(tile, 1);
        same = new Node(tile, 1);
        differentDist = new Node(tile, 2);
        differentTile = new Node(diffTile, 1);
    }

    @Test
    public void compareTo() {
        assertEquals(node, same);
        assertNotEquals(node, differentDist);
        assertNotEquals(node, differentTile);
    }

    @Test
    public void testEquals() {
        assertEquals(node, same);
        assertNotEquals(node, differentDist);
        assertNotEquals(node, differentTile);
    }
}