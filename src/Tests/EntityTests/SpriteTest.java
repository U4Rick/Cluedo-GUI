package Tests.EntityTests;

import Game.Entities.Sprite;
import Game.Tiles.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SpriteTest {

    Sprite sprite = new Sprite("MissScarlett.png", new Position(0, 0), "c");

    @Test
    public void updatePosition() {
        Position newPosition = new Position(1, 1);
        sprite.updatePosition(newPosition);
        assertEquals(sprite.getPosition(), newPosition);
    }

    @Test
    public void getPosition() {
        assertEquals(sprite.getPosition(), new Position(0, 0));
    }
}