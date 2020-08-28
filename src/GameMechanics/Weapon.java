package GameMechanics;

import Cards.WeaponCard;
import Tiles.Position;
import Tiles.Tile;

public class Weapon {

    private WeaponCard weapon;
    private Tile tile;
    private Sprite icon;

    public Weapon(WeaponCard weapon) {
        this.weapon = weapon;
        icon = new Sprite(weapon.getFileName(), null,"w");
    }

    public Sprite getIcon() {
        return icon;
    }

    public void setTile(Tile tile) {
        icon.updatePosition(tile.position);
        this.tile = tile;
    }
}
