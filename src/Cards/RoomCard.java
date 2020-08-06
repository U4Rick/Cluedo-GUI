package Cards;

public class RoomCard implements Card {

    private RoomEnum room;

    public RoomCard(RoomEnum room) {
        this.room = room;
    }

    public enum RoomEnum {
        KITCHEN, BALLROOM, CONSERVATORY, DININGROOM, BILLIARDROOM, LIBRARY, STUDY, HALL, LOUNGE
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
                return "";
        }
    }
}