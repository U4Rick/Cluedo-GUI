package Tests.CardTests;

import Cards.RoomCard;
import org.junit.jupiter.api.Test;

import static Cards.RoomCard.RoomEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomCardTest {

    private final RoomCard card = new RoomCard(KITCHEN);

    @Test
    void getFileName() {
        assertEquals(card.getFileName(), "Kitchen.png");
    }

    @Test
    void getRoom() {
        assertEquals(card.getRoom(), KITCHEN);
    }

    @Test
    void testEquals() {
        RoomCard same = new RoomCard(KITCHEN);
        RoomCard different = new RoomCard(BALLROOM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    void testToString() {
        assertEquals(card.toString(), "Kitchen");
    }
}