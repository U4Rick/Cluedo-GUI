package GameMechanics;

import Tiles.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Sprite for a Character or a Weapon.
 */
public class Sprite {

    private Image icon;
    private Image activeIcon;
    public final int size = 18;
    private Position position;

    /**
     * Constructor for Sprite.
     *
     * @param filename Name of file to get icon from.
     * @param position Initial position.
     * @param type Distinguish between weapons and characters.
     */
    public Sprite (String filename, Position position, String type) {
        try {
            if (type.equals("c")) {
                icon = ImageIO.read(new File("./assets/characterSprites/" + filename));
                activeIcon = ImageIO.read(new File("./assets/characterSprites/A" + filename));
                activeIcon = activeIcon.getScaledInstance(size, size, Image.SCALE_DEFAULT);
            }
            else {
                icon = ImageIO.read(new File("./assets/weaponSprites/" + filename));
                activeIcon = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        icon = icon.getScaledInstance(size, size, Image.SCALE_DEFAULT);

        this.position = position;
    }

    /**
     * Update position with passed position.
     *
     * @param position Position to update to.
     */
    public void updatePosition(Position position) {
        this.position = position;
    }

    /**
     * Getter for position.
     *
     * @return Current Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Getter for icon.
     *
     * @return icon.
     */
    public Image getIcon() {
        return icon;
    }

    /**
     * Getter for activeIcon.
     *
     * @return activeIcon.
     */
    public Image getActiveIcon() {
        return activeIcon;
    }
}
