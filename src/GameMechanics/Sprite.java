package GameMechanics;

import Tiles.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private Image icon;
    public final int size = 18;
    private Position pos;

    public Sprite (String imagePath, Position pos) {
        try {
            icon = ImageIO.read(new File(imagePath));
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
}
