package Tests.CardTests;

import Game.Cards.RoomCard;
import org.junit.*;

import static Game.Cards.RoomCard.RoomEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class RoomCardTest {

    private final RoomCard card = new RoomCard(KITCHEN);

    @Test
    public void getFileName() {
        assertEquals(card.getFileName(), "Kitchen.png");
    }

    @Test
    public void getRoom() {
        assertEquals(card.getRoom(), KITCHEN);
    }

    @Test
    public void testEquals() {
        RoomCard same = new RoomCard(KITCHEN);
        RoomCard different = new RoomCard(BALLROOM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    public void testToString() {
        assertEquals(card.toString(), "Kitchen");
    }
}