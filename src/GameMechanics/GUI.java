package GameMechanics;

import Cards.Card;
import Tiles.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;
    private JFrame suggestWindow;
    private JFrame accuseWindow;
    private JFrame refuteWindow;

    private final double cellSize = 18.2;
    private final int left = 33;
    private final int top = 18;

    Player currentPlayer;
    private JPanel cardPanel;


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
                create();
                int count = 0;
                for (JRadioButton charac : characters) {
                    if (charac.isSelected()) {
                        count++;
                        //this is just commented out for debugging
                        JOptionPane option = new JOptionPane();
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
                            createPlayer(charac.getText(), username);
                        }

                    }
                }
                if (count >= 3) { //make sure there are enough players
                    setupCardsAndGame();
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

        selectWindow.pack(); //makes all frame contents at or above their preferred size
        selectWindow.setLocationRelativeTo(null); //centers window on screen
        selectWindow.setVisible(true);

    }

    /**
     * Create all of the cards, select a solution, then deal the rest of the cards to the players.
     */
    protected abstract void setupCardsAndGame();

    protected abstract void create();


    private void buildGameBoard() {
        playerUpdate();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("hewwo");
        //TODO Start menu button
        //TODO Rules menu
        menuBar.add(menu);


        JPanel logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(300, 500));
        logPanel.setLayout(new BorderLayout());
        JEditorPane log = new JEditorPane();
        log.setPreferredSize(new Dimension(290,450));
        log.setEditable(false);
        log.setText("wah wah wah \nhewwo");
        logPanel.add(log, BorderLayout.PAGE_START);

        JTextField chatBox = new JTextField();
        chatBox.setText("Hewwo");
        logPanel.add(chatBox, BorderLayout.CENTER);

        JButton okButton = new JButton("OK!");
        okButton.setPreferredSize(new Dimension(60,40));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatBox.getText().length() != 0) {
                    appendToLog(currentPlayer.getUsername() + ": " + chatBox.getText(), log);
                    chatBox.setText("");
                }
            }
        });
        logPanel.add(okButton, BorderLayout.LINE_END);

        JPanel upperPanel = new JPanel();

        Panel boardPanel = new Panel(new ImageIcon("./assets/cluedo_board.jpg").getImage());

        JButton dice = new JButton("Roll");
        dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendToLog(rollDice(), log);
                dice.setEnabled(false);
                //TODO find an appropriate place to re-enable dice for next player
            }
        });
        dice.setPreferredSize(new Dimension(80,30));



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

        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(boardPanel, BorderLayout.CENTER);
        upperPanel.add(logPanel, BorderLayout.LINE_END);


        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(800,150));

        JPanel componentPanel = new JPanel();
        componentPanel.setPreferredSize(new Dimension(100,150));

        JLabel characterNameLabel = new JLabel(currentPlayer.getCharacter().toString());

        JLabel userNameLabel = new JLabel(currentPlayer.getUsername());

        JButton suggestButton = new JButton("Suggest!");
        suggestButton.setEnabled(false);
        suggestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildSuggestWindow();
            }
        });

        JButton accuseButton = new JButton("Accuse!");
        accuseButton.setEnabled(false);
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO if statement for if no preexisting suggestions, dialog pops up instead
                buildAccuseWindow();
            }
        });

        JButton nextButton = new JButton("Next!");
        nextButton.setEnabled(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

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


        //JPanel cardPanel = new JPanel();

        setupCardPanel();

        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(componentPanel, BorderLayout.LINE_START);
        infoPanel.add(cardPanel, BorderLayout.CENTER);


        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, infoPanel);
        mainPane.setEnabled(false);

        boardPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!dice.isEnabled()) {
                    if (processPlayerTurn(getPositionAtClick(e.getX(), e.getY()))) {
                        if (currentPlayer.isInRoom()) {
                            accuseButton.setEnabled(true);
                            suggestButton.setEnabled(true);
                        }
                        else {
                            updateCurrentPlayer();
                            dice.setEnabled(true);
                            playerUpdate();
                            infoPanel.remove(cardPanel);
                            setupCardPanel();
                            infoPanel.add(cardPanel, BorderLayout.CENTER);
                            characterNameLabel.setText(currentPlayer.getCharacter().toString());
                            userNameLabel.setText(currentPlayer.getUsername());

                        }
                        redraw();
                    }
                    else {

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        gameWindow = new JFrame();
        selectWindow.setVisible(false);

        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameWindow.getContentPane().add(menuBar);
        gameWindow.getContentPane().add(mainPane);
        gameWindow.setJMenuBar(menuBar);

        gameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(gameWindow, "Are you sure you want to leave?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                super.windowClosing(e);
            }
        });

        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

    private void setupCardPanel() {
        cardPanel = new JPanel();
        Dimension cardPanelSize = new Dimension(700,150);
        cardPanel.setPreferredSize(cardPanelSize);
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.RIGHT);
        cardPanel.setLayout(layout);
        for (Card card : currentPlayer.getHand()) {
            //all of this is awful math to make sure the cards size nicely on the panel
            ImageIcon icon = new ImageIcon("./assets/cards/" + card.getFileName());
            int width = (int)(((double)(cardPanelSize.width/currentPlayer.getHand().size()))*0.7);
            double ratio = ((double)width/icon.getIconWidth());
            ratio = cardPanelSize.height > ratio*icon.getIconHeight() ? ratio : ((double) cardPanelSize.height/icon.getIconHeight())*0.9;
            icon = new ImageIcon(icon.getImage().getScaledInstance((int)(icon.getIconWidth()*ratio), (int)(icon.getIconHeight()*ratio), Image.SCALE_DEFAULT));

            cardPanel.add(new JLabel(icon));
        }
    }

    protected abstract void updateCurrentPlayer();

    protected abstract boolean processPlayerTurn(Position cellToMoveTO);


    private void appendToLog(String s, JEditorPane log) {
        log.setText(log.getText() + "\n" + s);
    }


    private void buildAccuseWindow() {
        //TODO list of suggestions
    }

    private void buildSuggestWindow() {
        //TODO combobox of rooms (cannot be interracted with)
        //TODO combobox of characters in play
        //TODO combobox of weapons.
        //TODO go button
        //TODO cancel button (consider as making no suggestion)
    }

    private void buildRefuteWindow(Player p) {
        //TODO display cards
        //TODO combobox with options (may be empty)
        //TODO label with refute possibilities (no refute, one refute, pick a refute)
        //TODO go button
    }

    private Position getPositionAtClick(int x, int y) {
        int col = (int)((x - left)/cellSize);
        int row = (int)((y - top)/cellSize);
        return new Position(col, row);

    }

    public void redraw() {
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    public void playerUpdate() { currentPlayer = getCurrentPlayer(); }


    protected abstract Player getCurrentPlayer();

    protected abstract void createPlayer(String text, String username);

    protected abstract String rollDice();

    protected abstract ArrayList<Sprite> getPlayerIcons();



    private class Panel extends JPanel {

        private Image img;

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
            for (Sprite s : getPlayerIcons()) {
                g.drawImage(s.getIcon(), (int)(s.getPos().getX() * cellSize) + left, (int)(s.getPos().getY()* cellSize) + top, null);
            }
            Sprite activePlayer = currentPlayer.getPlayerIcon();
            g.drawImage(activePlayer.getActiveIcon(), (int)(activePlayer.getPos().getX() * cellSize) + left, (int)(activePlayer.getPos().getY()* cellSize) + top, null);
        }

    }


}
