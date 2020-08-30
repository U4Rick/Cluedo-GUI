package GameMechanics;

import Cards.WeaponCard;
import Tiles.Tile;

/**
 * Representation of a weapon for the game of Cluedo.
 */
public class Weapon {

    private final WeaponCard weapon;
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
     * Getter for weapon.
     *
     * @return Weapon.
     */
    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * Getter for tile.
     *
     * @return Tile.
     */
    public Tile getTile() {
        return tile;
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
