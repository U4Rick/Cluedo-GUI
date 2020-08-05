package Cards;

public class RoomCard implements Card {

    rooms room;
    public RoomCard(rooms room) {
        this.room = room;
    }

    public enum rooms {
        KITCHEN, BALLROOM, CONSERVATORY, DININGROOM, BILLIARDROOM, LIBRARY, STUDY, HALL, LOUNGE
    }

    public void delete() {
    }

}