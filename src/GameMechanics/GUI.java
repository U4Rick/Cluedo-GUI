package GameMechanics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;


    public GUI() { buildCharacterSelectWindow(); }

    private void buildCharacterSelectWindow() {

        JRadioButton char1 = new JRadioButton("Peacock");
        JRadioButton char2 = new JRadioButton("Mustard");
        JRadioButton char3 = new JRadioButton("Scarlett");
        JRadioButton char4 = new JRadioButton("Plum");
        JRadioButton char5 = new JRadioButton("White");
        JRadioButton char6 = new JRadioButton("Green");
        char1.setSelected(true);


        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { buildGameBoard(); }
        });

        JLabel title = new JLabel("Select Players!");

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
        selectPanel.add(title);
        selectPanel.add(char1);
        selectPanel.add(char2);
        selectPanel.add(char3);
        selectPanel.add(char4);
        selectPanel.add(char5);
        selectPanel.add(char6);
        selectPanel.add(submit);
        selectWindow = new JFrame("Select Players");

        selectWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectWindow.add(selectPanel);

        //TODO set minimum size

        selectWindow.pack(); //makes all frame contents at or above their preferred size
        selectWindow.setLocationRelativeTo(null); //centers window on screen
        selectWindow.setVisible(true);

    }

    private void buildGameBoard() {
        JMenuBar menuBar = new JMenuBar();

        Panel boardPanel = new Panel();
        final BufferedImage image;
        try {
            image = ImageIO.read(new File("./assets/cluedo_board.jpg"));
            boardPanel.paintComponent(image.getGraphics(), image);
        } catch (IOException e) {
            e.printStackTrace();
        }



        JPanel optionPanel = new JPanel();



        JPanel cardPanel = new JPanel();




        //TODO format JPanels.

        JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, optionPanel);
        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, cardPanel);


        gameWindow = new JFrame();
        selectWindow.setVisible(false);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO set layout
        //TODO set minimum size

        //TODO add elements to frame
        gameWindow.add(menuBar);
        gameWindow.add(mainPane);


        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

    private class Panel extends JPanel {

        protected void paintComponent(Graphics g, BufferedImage image) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }
}
