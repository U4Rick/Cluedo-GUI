package Tests.BoardTests;

import Game.Board.Node;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Node node;
    Node same;
    Node differentDist;
    Node differentTile;

    @BeforeEach
    void setUp() {
        Tile tile = new Tile(new Position(0, 0));
        Tile diffTile = new Tile(new Position(1, 1));

        node = new Node(tile, 1);
        same = new Node(tile, 1);
        differentDist = new Node(tile, 2);
        differentTile = new Node(diffTile, 1);
    }

    @Test
    void compareTo() {
        assertEquals(node, same);
        assertNotEquals(node, differentDist);
        assertNotEquals(node, differentTile);
    }

    @Test
    void testEquals() {
        assertEquals(node, same);
        assertNotEquals(node, differentDist);
        assertNotEquals(node, differentTile);
    }
}