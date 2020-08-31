package Tests.EntityTests;

import Game.Entities.Sprite;
import Game.Tiles.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    Sprite sprite = new Sprite("MissScarlett.png", new Position(0, 0), "c");

    @Test
    void updatePosition() {
        Position newPosition = new Position(1, 1);
        sprite.updatePosition(newPosition);
        assertEquals(sprite.getPosition(), newPosition);
    }

    @Test
    void getPosition() {
        assertEquals(sprite.getPosition(), new Position(0, 0));
    }
}