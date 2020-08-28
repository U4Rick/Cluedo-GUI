package GameMechanics;

import Cards.Card;
import Cards.RoomCard;
import GameMechanics.Action.Hypothesis;
import Tiles.EntranceTile;
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
    private JDialog refuteWindow;

    private final double cellSize = 18.2;
    private final int left = 33;
    private final int top = 18;

    Player currentPlayer;
    private JPanel cardPanel;
    private boolean checkBool;
    private JRadioButton selectedCard;
    JButton suggestButton;
    JButton accuseButton;
    JEditorPane log;
    boolean currentPlayerMoveEnded = false;
    ArrayList<Hypothesis> unrefutedSuggestions = new ArrayList<>();


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
                    JOptionPane option = new JOptionPane(JOptionPane.DEFAULT_OPTION);
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
        currentPlayer = getCurrentPlayer();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        //TODO Rules menu
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("A text-only menu item");
        menu.add(menuItem);
        menu.addSeparator();

        JMenu submenu = new JMenu("Rules");
        menuItem = new JMenuItem("Don't do drugs");
        submenu.add(menuItem);
        menuItem = new JMenuItem("Wear clothes when in public");
        submenu.add(menuItem);

        menu.add(submenu);



        JPanel logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(300, 500));
        logPanel.setLayout(new BorderLayout());
        log = new JEditorPane();
        log.setPreferredSize(new Dimension(290,450));
        log.setEditable(false);
        log.setText("Text log begins here.");
        logPanel.add(log, BorderLayout.PAGE_START);
        appendToLog(currentPlayer.getCharacter().toString() + " begins their turn.", log);


        JTextField chatBox = new JTextField();
        chatBox.setText("");
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

        JButton nextButton = new JButton("Next!");
        nextButton.setEnabled(false);


        JButton dice = new JButton("Roll");
        dice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendToLog(rollDice(), log);
                dice.setEnabled(false);
                nextButton.setEnabled(true);
            }
        });
        dice.setPreferredSize(new Dimension(80,30));

        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints boardInsets = new GridBagConstraints();

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

        suggestButton = new JButton("Suggest!");
        suggestButton.setEnabled(false);
        suggestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildSuggestWindow();
            }
        });

        accuseButton = new JButton("Accuse!");
        accuseButton.setEnabled(false);
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (unrefutedSuggestions.size() == 0) {
                    JOptionPane option = new JOptionPane();
                    option.setOptionType(JOptionPane.DEFAULT_OPTION);
                    option.setMessage("There are no suggestions to accuse based on!");
                    JDialog dialog = option.createDialog("");
                    dialog.pack();
                    dialog.setVisible(true);
                    if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                        dialog.setVisible(false);
                        accuseButton.setEnabled(false);
                    }
                }
                else {
                    buildAccuseWindow();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dice.setEnabled(true);
                playerUpdate();
                infoPanel.remove(cardPanel);
                setupCardPanel();
                infoPanel.add(cardPanel, BorderLayout.CENTER);
                characterNameLabel.setText(currentPlayer.getCharacter().toString());
                userNameLabel.setText(currentPlayer.getUsername());
                accuseButton.setEnabled(false);
                suggestButton.setEnabled(false);
                nextButton.setEnabled(false);
                appendToLog(currentPlayer.getCharacter().toString() + " begins their turn.", log);
                currentPlayerMoveEnded = false;
                redraw();
            }
        });

        componentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //add username
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(8, 10, 0, 10);
        componentPanel.add(userNameLabel, constraints);

        //add character
        constraints.gridy++;
        componentPanel.add(characterNameLabel, constraints);

        //add suggest
        constraints.gridy++;
        componentPanel.add(suggestButton, constraints);

        //add accuse
        constraints.gridy++;
        componentPanel.add(accuseButton, constraints);

        //add next
        constraints.gridy++;
        constraints.insets = new Insets(8, 10, 20, 10);
        componentPanel.add(nextButton, constraints);

        setupCardPanel();

        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(componentPanel, BorderLayout.LINE_START);
        infoPanel.add(cardPanel, BorderLayout.CENTER);

        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPanel, infoPanel);
        mainPane.setEnabled(false);

        boardPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!dice.isEnabled() && !currentPlayerMoveEnded) {
                    if (processPlayerTurn(getPositionAtClick(e.getX(), e.getY()))) {
                        if (currentPlayer.isInRoom() && currentPlayer.canHypothesise()) {
                            accuseButton.setEnabled(true);
                            suggestButton.setEnabled(true);
                        }
                        nextButton.setEnabled(true);
                        redraw();
                        currentPlayerMoveEnded = true;
                    }
                    else {

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
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


    private void appendToLog(String s, JEditorPane log) {
        log.setText(log.getText() + "\n" + s);
    }


    private void buildAccuseWindow() {

        DefaultListModel<String> accuseModel = new DefaultListModel<>();
        JList<? extends String>  accusationsList = new JList<>(accuseModel);
        ArrayList<String> stringUnrefutedAccusations = new ArrayList<>();
        for (Hypothesis h : unrefutedSuggestions) {
            stringUnrefutedAccusations.add(h.toString());
        }
        accuseModel.addAll(stringUnrefutedAccusations);
        accusationsList.setPreferredSize(new Dimension(200, 400));


        JButton okButton = new JButton("OK!");
        okButton.setPreferredSize(new Dimension(80, 40));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accusationsList.getSelectedIndex() >= 0) {
                    if (compareToSolution(unrefutedSuggestions.get(accusationsList.getSelectedIndex()))) {
                        JOptionPane option = new JOptionPane();
                        option.setOptionType(JOptionPane.DEFAULT_OPTION);
                        option.setMessage(currentPlayer.getCharacter().toString() + " figured out the murder circumstances and won the game! \nWindow will now close.");
                        JDialog dialog = option.createDialog("Game Over!");
                        dialog.pack();
                        dialog.setVisible(true);
                        if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                            dialog.setVisible(false);
                            System.exit(0);
                        }
                    }
                    else {
                        currentPlayer.madeFalseAccusation();
                        boolean playerRemaining = false;
                        for (Player p : getPlayers()) {
                            if (p.hasNotMadeFalseAccusation()) {
                                playerRemaining = true;
                                break;
                            }
                        }
                        JOptionPane option = new JOptionPane();
                        option.setOptionType(JOptionPane.DEFAULT_OPTION);
                        if (!playerRemaining) {
                            option.setMessage("Everyone made a false accusation! The murderer got away with it! \nGame Over!\nThe window will now close.");
                            JDialog dialog = option.createDialog("Game Over");
                            dialog.pack();
                            dialog.setVisible(true);
                            if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                                dialog.setVisible(false);
                                System.exit(0);
                            }
                        }
                        else {
                            option.setMessage(currentPlayer.getCharacter().toString() + " made a false accusation, they can no longer accuse or suggest!");
                            JDialog dialog = option.createDialog("False Accusation!");
                            dialog.pack();
                            dialog.setVisible(true);
                            if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                                dialog.setVisible(false);
                                accuseWindow.setVisible(false);
                                accuseButton.setEnabled(false);
                                suggestButton.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });

        accuseWindow = new JFrame();
        accuseWindow.setTitle("Pick a suggestion to accuse based upon");
        accuseWindow.setLayout(new BorderLayout());
        accuseWindow.add(accusationsList, BorderLayout.CENTER);
        accuseWindow.add(okButton, BorderLayout.PAGE_END);

        accuseWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                accuseButton.setEnabled(false);
                super.windowClosed(e);
            }
        });

        accuseWindow.pack();
        accuseWindow.setLocationRelativeTo(null);
        accuseWindow.setVisible(true);
    }

    protected abstract boolean compareToSolution(Hypothesis s);

    private void buildSuggestWindow() {
        EntranceTile entranceTile = (EntranceTile) currentPlayer.getTile();
        RoomCard room = new RoomCard(entranceTile.getRoom());
        String [] roomArray = new String[1];
        roomArray[0] = room.toString();
        JComboBox<String> rooms = new JComboBox<>(roomArray);


        ArrayList<Player> players = getPlayers();
        String[] characterArray = new String[players.size()];
        for (int i = 0; i < players.size(); i++) { characterArray[i] = players.get(i).getCharacter().toString(); }
        JComboBox<String> characters = new JComboBox<>(characterArray);

        String[] weaponArray = getWeapons();
        JComboBox<String> weapons = new JComboBox<>(weaponArray);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane option = new JOptionPane();
                option.setOptionType(JOptionPane.YES_NO_OPTION);
                option.setMessage("You won't be able to suggest again if you close this.");
                JDialog dialog = option.createDialog("Are you sure?");
                dialog.pack();
                dialog.setVisible(true);
                if ((Integer) option.getValue() == JOptionPane.YES_OPTION) {
                    dialog.setVisible(false);
                    suggestWindow.setVisible(false);
                }
            }
        });
        JButton okayButton = new JButton("OK");
        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hypothesis suggestion = playerSuggest(room, (String)characters.getSelectedItem(), (String)weapons.getSelectedItem());
                int index = players.indexOf(currentPlayer);
                for (int i = 0; i < players.size() - 1; i++) {
                    //Roll over to index 0
                    if (index == players.size() - 1) {
                        index = 0;
                    } else {
                        index++;
                    }
                    buildRefuteWindow(players.get(index), suggestion);
                    if (checkBool) {
                        appendToLog(players.get(index).getCharacter().toString() + " refuted with " + selectedCard.getText(), log);
                        break;
                    }
                }
                if (!checkBool) {
                    unrefutedSuggestions.add(suggestion);
                }
                else {
                    checkBool = false;
                }
                suggestWindow.setVisible(false);
                suggestButton.setEnabled(false);
            }
        });

        suggestWindow = new JFrame();
        suggestWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        suggestWindow.setTitle("Make a suggestion.");
        suggestWindow.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //add room combobox
        constraints.gridy = 0;
        constraints.gridx = 0;
        suggestWindow.add(rooms, constraints);

        //add characters combobox
        constraints.gridy++;
        suggestWindow.add(characters, constraints);

        //add weapons combobox
        constraints.gridy++;
        suggestWindow.add(weapons, constraints);

        //add cancel button
        constraints.gridy++;
        constraints.gridx++;
        suggestWindow.add(cancelButton, constraints);

        //add ok button
        constraints.gridx++;
        suggestWindow.add(okayButton, constraints);


        suggestWindow.pack();
        suggestWindow.setLocationRelativeTo(null);
        suggestWindow.setVisible(true);

    }

    private boolean buildRefuteWindow(Player p, Hypothesis suggestion) {
        JPanel refuteCardPanel = new JPanel();
        Dimension cardPanelSize = new Dimension(600,150);
        refuteCardPanel.setPreferredSize(cardPanelSize);
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        refuteCardPanel.setLayout(layout);
        for (Card card : p.getHand()) {
            //all of this is awful math to make sure the cards size nicely on the panel
            ImageIcon icon = new ImageIcon("./assets/cards/" + card.getFileName());
            int width = (int)(((double)(cardPanelSize.width/p.getHand().size()))*0.7);
            double ratio = ((double)width/icon.getIconWidth());
            ratio = cardPanelSize.height > ratio*icon.getIconHeight() ? ratio : ((double) cardPanelSize.height/icon.getIconHeight())*0.9;
            icon = new ImageIcon(icon.getImage().getScaledInstance((int)(icon.getIconWidth()*ratio), (int)(icon.getIconHeight()*ratio), Image.SCALE_DEFAULT));

            refuteCardPanel.add(new JLabel(icon));
        }

        JPanel optionPanel = new JPanel();
        ButtonGroup choices = new ButtonGroup();
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        for (Card c : p.getRefutableCards(suggestion)) {
            JRadioButton temp = new JRadioButton(c.toString());
            choices.add(temp);
            radioButtons.add(temp);
        }

        JLabel info = new JLabel();

        if (choices.getButtonCount() == 0) {
            info.setText("No Refute Possible, Press Go");
        }
        else if (choices.getButtonCount() == 1) {
            info.setText("Refute Possible, Press Go");
        }
        else {
            info.setText("Please Pick a Refute Option, Press Go");
        }

        JButton goButton = new JButton("GO");
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refuteWindow.setVisible(false);
                if (choices.getButtonCount() > 0) {
                    checkBool = true;
                    for (JRadioButton j : radioButtons) {
                        if (j.isSelected()) {
                            selectedCard = j;
                            break;
                        }
                    }
                }
            }
        });

        optionPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.gridx = 1;
        JRadioButton last = null;
        switch (radioButtons.size()) {
            case 3:
                optionPanel.add(radioButtons.get(0), constraints);
                constraints.gridx = 2;
                optionPanel.add(radioButtons.get(1), constraints);
                constraints.gridx = 3;
                optionPanel.add(radioButtons.get(2), constraints);
                last = radioButtons.get(2);
                constraints.gridx = 2;
                break;
            case 2:
                optionPanel.add(radioButtons.get(0), constraints);
                constraints.gridx = 3;
                optionPanel.add(radioButtons.get(1), constraints);
                last = radioButtons.get(1);
                constraints.gridx = 2;
                break;
            case 1:
                optionPanel.add(radioButtons.get(0), constraints);
                last = radioButtons.get(0);
                break;
        }
        if (last != null) { last.setSelected(true); }


        constraints.gridy++;
        optionPanel.add(info, constraints);

        constraints.gridy++;
        optionPanel.add(goButton,constraints);

        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, refuteCardPanel, optionPanel);

        refuteWindow = new Frame();
        refuteWindow.setTitle(p.getCharacter().toString());
        JPanel window = new JPanel();
        window.add(mainPane);
        refuteWindow.add(window);

        refuteWindow.pack();
        refuteWindow.setLocationRelativeTo(null);
        refuteWindow.setVisible(true);
        return false;
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

    public void playerUpdate() {
        updateCurrentPlayer();
        currentPlayer = getCurrentPlayer();
    }


    protected abstract Player getCurrentPlayer();

    protected abstract void createPlayer(String text, String username);

    protected abstract String rollDice();

    protected abstract ArrayList<Sprite> getPlayerIcons();

    protected abstract void updateCurrentPlayer();

    protected abstract boolean processPlayerTurn(Position cellToMoveTO);

    protected abstract Hypothesis playerSuggest(RoomCard room, String selectedItem, String selectedItem1);

    protected abstract String[] getWeapons();

    protected abstract ArrayList<Player> getPlayers();


    private class Frame extends JDialog {
        public Frame() {
            setModal(true);
        }
    }


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
