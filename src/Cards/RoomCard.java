package Cards;

import java.util.Objects;

/**
 * Room card for the game of Cluedo.
 */
public class RoomCard implements Card {

    private final RoomEnum room;

    /**
     * Constructs a RoomCard object that contains a room.
     *
     * @param room the room to represent.
     */
    public RoomCard(RoomEnum room) {
        this.room = room;
    }

    /**
     * Possible rooms.
     */
    public enum RoomEnum {
        KITCHEN, BALLROOM, CONSERVATORY, DININGROOM, BILLIARDROOM, LIBRARY, STUDY, HALL, LOUNGE
    }

    /**
     * Gets the filename of the card as a string.
     *
     * @return Filename of the card as a string.
     */
    public String getFileName() {
        return this.toString().replaceAll("\\s", "") + ".png";
    }

    /**
     * Getter for the room.
     *
     * @return room.
     */
    public RoomEnum getRoom() {
        return room;
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
        switch (room) {
            case KITCHEN:
                return "Kitchen";
            case BALLROOM:
                return "Ballroom";
            case CONSERVATORY:
                return "Conservatory";
            case DININGROOM:
                return "Dining Room";
            case BILLIARDROOM:
                return "Billiard Room";
            case LIBRARY:
                return "Library";
            case STUDY:
                return "Study";
            case HALL:
                return "Hall";
            case LOUNGE:
                return "Lounge";
            default:
                throw new IllegalArgumentException();
        }
    }
}