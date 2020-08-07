package Cards;

import java.util.Objects;

/**
 * Weapon card for the game of Cluedo.
 */
public class WeaponCard implements Card {

    private final WeaponEnum weapon;

    public WeaponCard(WeaponEnum weapon) {
        this.weapon = weapon;
    }

    public enum WeaponEnum {
        CANDLESTICK, LEADPIPE, DAGGER, REVOLVER, ROPE, SPANNER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeaponCard that = (WeaponCard) o;
        return weapon == that.weapon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weapon);
    }

    @Override
    public String toString() {
        return switch (weapon) {
            case CANDLESTICK -> "Candlestick";
            case LEADPIPE -> "Leadpipe";
            case DAGGER -> "Dagger";
            case REVOLVER -> "Revolver";
            case ROPE -> "Rope";
            case SPANNER -> "Spanner";
        };
    }
}