package GameMechanics;

import javax.swing.*;

public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;

    public GUI() { buildCharacterSelectWindow(); }

    private void buildCharacterSelectWindow() {
        //TODO radio buttons, action listener
        //TODO submit button, action listener
        //TODO jlabel title

        selectWindow = new JFrame("Select Players");

        selectWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO set layout
        //TODO set minimum size

        //TODO add elements to frame

        selectWindow.pack(); //makes all frame contents at or above their preferred size
        selectWindow.setLocationRelativeTo(null); //centers window on screen
        selectWindow.setVisible(true);

    }

    private void buildGameBoard() {

        //TODO make Jpanels, in split pane?

        gameWindow = new JFrame();

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO set layout
        //TODO set minimum size

        //TODO add elements to frame

        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}
