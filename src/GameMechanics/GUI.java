package GameMechanics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;


    public GUI() {
        buildCharacterSelectWindow();
    }

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
                int count = 0;
                for (JRadioButton charac : characters) {
                    if (charac.isSelected()) {
                        count++;
                        /*JOptionPane option = new JOptionPane();
                        option.setOptionType(JOptionPane.DEFAULT_OPTION);
                        String username;

                        do {
                            username = JOptionPane.showInputDialog(option, "Please enter the user name for player playing as " + charac.getText() + ".");
                        } while (username.length() == 0);

                        option.setMessage(username + " is playing as " + charac.getText() + ".");
                        JDialog dialog = option.createDialog("Name");
                        dialog.pack();
                        dialog.setVisible(true);
                        if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                            dialog.setVisible(false);
                            //TODO do something with the username here, need to create a player object, passing username in
                        }*/

                    }
                }
                if (count >= 3) { //make sure there are enough players
                    buildGameBoard();
                } else {
                    JOptionPane option = new JOptionPane();
                    option.setOptionType(JOptionPane.DEFAULT_OPTION);
                    option.setMessage("You must have 3 or more players!");
                    JDialog dialog = option.createDialog("Not enough players.");
                    dialog.pack();
                    dialog.setVisible(true);
                    int choice = (Integer) option.getValue();
                    if (choice == JOptionPane.OK_OPTION) {
                        dialog.setVisible(false);
                    }
                }
            }
        });

        JLabel title = new JLabel("Select Players!");

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
        selectPanel.add(title);
        for (JRadioButton but : characters) {
            selectPanel.add(but);
        }
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
        JMenu menu = new JMenu("hewwo");
        menuBar.add(menu);

        JPanel upperPanel = new JPanel();

        Panel boardPanel = new Panel(new ImageIcon("./assets/cluedo_board.jpg").getImage());
        JButton dice = new JButton("Roll Dice!");
        dice.setPreferredSize(new Dimension(50,30));
        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints boardInsets = new GridBagConstraints();

        //Following insets should offset the edges of the boards image that aren't in the grid
        Insets left = new Insets(0,33,0,0);
        Insets right = new Insets(0,0,0,39);
        Insets top = new Insets(18,0,0,0);
        Insets bottom = new Insets(0,0,30,0);
        Insets topLeft = new Insets(18,33,0,0);
        Insets bottomLeft = new Insets(0,33,30,0);
        Insets topRight = new Insets(18,0,0,39);
        Insets bottomRight = new Insets(0,0,30,39);

        boardInsets.insets = new Insets(255, 235, 215, 215);
        boardPanel.add(dice, boardInsets);



        JPanel logPanel = new JPanel();
        JEditorPane log = new JEditorPane();
        log.setPreferredSize(new Dimension(290,490));
        log.setEditable(false);
        log.setText("wah wah wah \n hewwo");
        logPanel.add(log);

        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(boardPanel, BorderLayout.CENTER);
        upperPanel.add(logPanel, BorderLayout.LINE_END);


        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(800,200));

        JPanel componentPanel = new JPanel();
        componentPanel.setPreferredSize(new Dimension(100,200));

        //TODO might need an abstract void to get the current player?

        JLabel characterNameLabel = new JLabel("test");

        JLabel userNameLabel = new JLabel("test");

        JButton suggestButton = new JButton("Suggest!");
        suggestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton accuseButton = new JButton("Accuse!");
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        //TODO implement GridBagLayout and workout the necessary insets for each element

        componentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //add username
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 0, 10);
        componentPanel.add(userNameLabel, constraints);

        //add character
        constraints.gridy = 1;
        componentPanel.add(characterNameLabel, constraints);

        //add suggest
        constraints.gridy = 2;
        componentPanel.add(suggestButton, constraints);

        //add accuse
        constraints.gridy = 3;
        constraints.insets = new Insets(10, 10, 40, 10);
        componentPanel.add(accuseButton, constraints);


        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(700,200));
        //TODO figure this out
        //DO NOT NEED INTERACTION ON CARDS< JUST DISPLAY

        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(componentPanel, BorderLayout.LINE_START);
        infoPanel.add(cardPanel, BorderLayout.CENTER);


        //TODO format JPanels.

        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, infoPanel);


        gameWindow = new JFrame();
        selectWindow.setVisible(false);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO set layout
        //TODO set minimum size

        //TODO add elements to frame
        gameWindow.getContentPane().add(menuBar);
        gameWindow.getContentPane().add(mainPane);
        gameWindow.setJMenuBar(menuBar);


        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

    private class Panel extends JPanel {

        private Image img;

        public Panel(String img) {
            this(new ImageIcon(img).getImage());
        }

        public Panel(Image img) {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }

    }
}
