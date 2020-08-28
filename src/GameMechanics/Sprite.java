package GameMechanics;

import Tiles.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private Image icon;
    private Image activeIcon;
    public final int size = 18;
    private Position pos;

    public Sprite (String filename, Position pos) {
        try {
            icon = ImageIO.read(new File("./assets/characterSprites/" + filename));
            activeIcon = ImageIO.read(new File("./assets/characterSprites/A" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        icon = icon.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        activeIcon = activeIcon.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        this.pos = pos;
    }

    public void updatePosition(Position p) { this.pos = p; }

    public Position getPos() {
        return pos;
    }

    public Image getIcon() { return icon; }

    public Image getActiveIcon() { return activeIcon; }
}
