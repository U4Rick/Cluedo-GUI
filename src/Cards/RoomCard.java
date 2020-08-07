package Cards;

import java.util.Objects;

/**
 * Room card for the game of Cluedo.
 */
public class RoomCard implements Card {

    private final RoomEnum room;

    public RoomCard(RoomEnum room) {
        this.room = room;
    }

    public enum RoomEnum {
        KITCHEN, BALLROOM, CONSERVATORY, DININGROOM, BILLIARDROOM, LIBRARY, STUDY, HALL, LOUNGE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCard roomCard = (RoomCard) o;
        return room == roomCard.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }

    @Override
    public String toString() {
        return switch (room) {
            case KITCHEN -> "Kitchen";
            case BALLROOM -> "Ballroom";
            case CONSERVATORY -> "Conservatory";
            case DININGROOM -> "Dining Room";
            case BILLIARDROOM -> "Billiard Room";
            case LIBRARY -> "Library";
            case STUDY -> "Study";
            case HALL -> "Hall";
            case LOUNGE -> "Lounge";
        };
    }
}