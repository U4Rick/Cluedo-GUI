package Cards;

public class WeaponCard implements Card {

    private WeaponEnum weapon;

    public WeaponCard(WeaponEnum weapon) {
        this.weapon = weapon;
    }

    public enum WeaponEnum {
        CANDLESTICK, LEADPIPE, DAGGER, REVOLVER, ROPE, SPANNER
    }

    @Override
    public String toString() {
        switch (weapon) {
            case CANDLESTICK:
                return "Candlestick";
            case LEADPIPE:
                return "Leadpipe";
            case DAGGER:
                return "Dagger";
            case REVOLVER:
                return "Revolver";
            case ROPE:
                return "Rope";
            case SPANNER:
                return "Spanner";
            default:
                return "";
        }
    }
}