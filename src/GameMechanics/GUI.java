package GameMechanics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;


    public GUI() { buildCharacterSelectWindow(); }

    private void buildCharacterSelectWindow() {

        ArrayList<JRadioButton> characters = new ArrayList<>();
        characters.add(new JRadioButton("Peacock"));
        characters.add(new JRadioButton("Mustard"));
        characters.add(new JRadioButton("Scarlett"));
        characters.add(new JRadioButton("Plum"));
        characters.add(new JRadioButton("White"));
        characters.add(new JRadioButton("Green"));

        characters.get(0).setSelected(true);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JRadioButton charac : characters) {
                    if (charac.isSelected()) {
                        JOptionPane option = new JOptionPane();
                        option.setOptionType(JOptionPane.DEFAULT_OPTION);
                        String username;
                        do {
                            username = JOptionPane.showInputDialog(option, "Please enter the user name for player playing as " + charac.getText() + ".");
                        } while (username.length() == 0);
                        option.setMessage(username + " is playing as " +  charac.getText() + ".");
                        JDialog dialog = option.createDialog("Name");
                        dialog.pack();
                        dialog.setVisible(true);
                        if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                            dialog.setVisible(false);
                            //TODO do something with the username here
                        }

                    }
                }
                buildGameBoard();
            }
        });

        JLabel title = new JLabel("Select Players!");

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
        selectPanel.add(title);
        for (JRadioButton but : characters) { selectPanel.add(but); }
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



        JPanel suggestionPane = new JPanel();



        JPanel cardPanel = new JPanel();




        //TODO format JPanels.

        JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, suggestionPane);
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
