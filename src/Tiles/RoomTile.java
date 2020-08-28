package Tiles;

import Cards.RoomCard;
import GameMechanics.Weapon;

/**
 * Textual placeholder for room tiles (which are inaccessible by direct player movement).
 */
public class RoomTile extends InaccessibleTile {

    public RoomCard.RoomEnum room;
    protected Weapon weaponOnThisTile;

    /**
     * Creates the tile at given position.
     * @param pos   Position of the tile.
     */
    public RoomTile(RoomCard.RoomEnum room, Position pos) {
        super(pos);
        this.room = room;
        weaponOnThisTile = null;
    }

    public Weapon getWeaponOnThisTile() { return weaponOnThisTile; }

    public void setWeaponOnThisTile(Weapon weaponOnThisTile) { this.weaponOnThisTile = weaponOnThisTile; }

    @Override
    public String toString() {
        return "  ";
    }
}
