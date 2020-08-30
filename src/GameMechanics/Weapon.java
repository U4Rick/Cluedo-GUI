package GameMechanics;

import Cards.WeaponCard;
import Tiles.Tile;

//TODO weapon and tile fields don't do anything but might be needed if we update weapon position on suggestion? Otherwise safe to delete.

/**
 * Representation of a weapon for the game of Cluedo.
 */
public class Weapon {

    private WeaponCard weapon;
    private Tile tile;
    private final Sprite icon;

    /**
     * Constructor for a Weapon object.
     *
     * @param weapon WeaponCard that this weapon represents.
     */
    public Weapon(WeaponCard weapon) {
        this.weapon = weapon;
        icon = new Sprite(weapon.getFileName(), null,"w");
    }

    /**
     * Getter for icon.
     *
     * @return Sprite of weapon.
     */
    public Sprite getIcon() {
        return icon;
    }

    /**
     * Setter for tile field.
     *
     * @param tile Tile to update position to.
     */
    public void setTile(Tile tile) {
        icon.updatePosition(tile.position);
        this.tile = tile;
    }
}
