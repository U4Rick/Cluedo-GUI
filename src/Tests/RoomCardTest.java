package Tests;

import Cards.RoomCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomCardTest {

    private final RoomCard card = new RoomCard(RoomCard.RoomEnum.KITCHEN);

    @Test
    void getFileName() {
        assertEquals(card.getFileName(), "Kitchen.png");
    }

    @Test
    void getRoom() {
        assertEquals(card.getRoom(), RoomCard.RoomEnum.KITCHEN);
    }

    @Test
    void testEquals() {
        RoomCard same = new RoomCard(RoomCard.RoomEnum.KITCHEN);
        RoomCard different = new RoomCard(RoomCard.RoomEnum.BALLROOM);

        assertEquals(card, same);
        assertNotEquals(card, different);
    }

    @Test
    void testToString() {
        assertEquals(card.toString(), "Kitchen");
    }
}