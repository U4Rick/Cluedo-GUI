package Cards;

public class WeaponCard implements Card {

    weapons weapon;
    public WeaponCard(weapons weapon) {
        this.weapon = weapon;
    }

    public enum weapons {
        CANDLESTICK, LEADPIPE, DAGGER, REVOLVER, ROPE, SPANNER
    }

    public void delete() {
    }

}