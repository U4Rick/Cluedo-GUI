package GameMechanics;

import Tiles.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//TODO javadoc

public class Sprite {
    private Image icon;
    private Image activeIcon;
    public final int size = 18;
    private Position pos;

    public Sprite (String filename, Position pos, String type) {
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

        this.pos = pos;
    }

    public void updatePosition(Position p) { this.pos = p; }

    public Position getPos() {
        return pos;
    }

    public Image getIcon() { return icon; }

    public Image getActiveIcon() { return activeIcon; }
}
