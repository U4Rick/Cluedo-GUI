package GameMechanics;

import Cards.Card;
import Cards.RoomCard;
import GameMechanics.Action.Hypothesis;
import Tiles.EntranceTile;
import Tiles.Position;
import Tiles.RoomTile;
import Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

//TODO finish javadocs
//TODO comment inner code so it's readable

/**
 * The GUI for the Cluedo game.
 */
public abstract class GUI {

    private JFrame selectWindow;
    private JFrame gameWindow;
    private JFrame suggestWindow;
    private JFrame accuseWindow;
    private JDialog refuteWindow;

    private final double cellSize = 18.2;
    private final int left = 33;
    private final int top = 18;

    private Player currentPlayer;
    private JPanel cardPanel;
    private boolean checkBool;
    private JRadioButton selectedCard;
    private JButton suggestButton;
    private JButton accuseButton;
    private JEditorPane log;
    private boolean currentPlayerMoveEnded = false;
    private final ArrayList<Hypothesis> unrefutedSuggestions = new ArrayList<>();


    /**
     * Run the first window creation method on initialisation.
     */
    public GUI() {
        buildCharacterSelectWindow();
    }

    /**
     * Build the initial game setup window.
     * Includes a set of JRadioButtons to select characters playing in the game,
     * a JLabel informing the user to select characters, and a submit JButton.
     * On pressing the submit button, the program pops up a number of InputDialogs
     * (equal to the number of characters selected), prompting usernames of the
     * players for each character.
     * These InputDialogs will not proceed if the input box is empty, avoiding blank
     * usernames.
     * If the user tries to start the game with less than 3 characters selected,
     * a JDialog will appear informing the user that they need 3 or more characters
     * to play the game, and take them back to the character selection window.
     * The calling of abstract methods to do initial game setup (e.g setUpCardsAndGame())
     * is done within this method.
     * When the user has provided sufficient information, the buildGameBoard() method is
     * called to setup the main game area.
     */
    private void buildCharacterSelectWindow() {

        //TODO change these to checkboxes?
        ArrayList<JRadioButton> characters = new ArrayList<>();
        characters.add(new JRadioButton("Peacock"));
        characters.add(new JRadioButton("Mustard"));
        characters.add(new JRadioButton("Scarlett"));
        characters.add(new JRadioButton("Plum"));
        characters.add(new JRadioButton("White"));
        characters.add(new JRadioButton("Green"));

        characters.get(0).setSelected(true);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            createGame();
            int count = 0;
            for (JRadioButton character : characters) {
                if (character.isSelected()) {
                    count++;
                    JOptionPane option = new JOptionPane();
                    option.setOptionType(JOptionPane.DEFAULT_OPTION);
                    String username;

                    do {
                        username = JOptionPane.showInputDialog(option, "Please enter the user name for player playing as " + character.getText() + ".", "Username", JOptionPane.PLAIN_MESSAGE);
                    } while (username == null || username.length() == 0);

                    option.setMessage(username + " is playing as " + character.getText() + ".");
                    JDialog dialog = option.createDialog("Name");
                    dialog.pack();
                    dialog.setVisible(true);
                    if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                        dialog.setVisible(false);
                        createPlayer(character.getText(), username);
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
     * Builds the main game area.
     * This method handles all of the outer window calls, and calls most
     * of the abstract methods regarding game logic within listeners of
     * various kinds.
     * The game area is split into 4 sections, using a JSplitPane, split vertically,
     * with a JPanel containing two other JPanels on either side.
     * - On the top left section of the game area is the game board, a overridden
     * JPanel with one JButton. The majority of the work here happens with graphics
     * painted directly onto the panel, and a MouseListener that uses a helper method
     * to determine the click position to board cell location. The user interacts with
     * the board via mouse clicks, in order to move their character.
     * - On the top right, there is a panel containing the game log. The log features
     * three main components: a JEditorPane (in a JScrollPane), a JTextField, and a
     * JButton. The text inputted in the text field is added to the end of the existing
     * text in the editorPane when the button is pressed, noted as being sent by the
     * user currently playing at that time. The log also features game relevant information,
     * such as dice rolls, whose turn it is, and if a move is invalid, which are all
     * pulled from Game using abstract methods.
     * - On the bottom left is the info/main button control panel. This features two
     * JLabels showing the current player's Character name and username. There are
     * also three JButtons: One for Suggestions, one for Accusations, and one to
     * proceed to the next player's turn. These buttons (along with the dice button
     * on the game board) get disabled when it is not appropriate to use them based on
     * game conditions. The suggestion button will run the buildSuggestionWindow() method
     * on press. The accusations button will run the buildAccusationsWindow() method if
     * there are active unrefuted suggestions, otherwise it will display a JDialog
     * notifying the player than they cannot make an accusation at this time (and disabling
     * the button). The next button runs a chunk of code which setups the game for the next
     * player, and proceeds to the next round.
     * - On the bottom right, there is the cardPanel, which is a JPanel containing a number
     * of ImageIcon JLabels, in order to display the current player's card hand. This panel
     * is purely decorative and serves no functional purpose outside of information for the
     * player.
     */
    private void buildGameBoard() {
        currentPlayer = getCurrentPlayer();

        //Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu rulesMenu = new JMenu("Rules");
        menuBar.add(rulesMenu);

        //Movement submenu
        JMenu movementSubMenu = new JMenu("Movement");
        movementSubMenu.add(new JMenuItem("Players may move their pieces UP TO their rolled value, to any hallway tile or room entrance tile."));
        movementSubMenu.add(new JMenuItem("Diagonal moves are disallowed."));
        movementSubMenu.add(new JMenuItem("Players may enter rooms via entrances only."));
        movementSubMenu.add(new JMenuItem("Entering a room ends the move."));
        movementSubMenu.add(new JMenuItem("To leave a room, click the exit you wish to leave from first."));
        movementSubMenu.add(new JMenuItem("No two pieces may occupy the same square."));

        //Suggestion submenu
        JMenu suggestionSubMenu = new JMenu("Suggestions");
        suggestionSubMenu.add(new JMenuItem("A player who ends their turn moving into a room MAY make a suggestion."));
        suggestionSubMenu.add(new JMenuItem("A suggestion comprises of the current room, a weapon, and a character."));
        suggestionSubMenu.add(new JMenuItem("The suggested weapon and character are moved to the suggested room"));

        //Refuting submenu
        JMenu refutingSubMenu = new JMenu("Refuting");
        refutingSubMenu.add(new JMenuItem("If a player can refute, they MUST refute."));
        refutingSubMenu.add(new JMenuItem("If a player has multiple options to refute, they may chose any ONE."));
        refutingSubMenu.add(new JMenuItem("If no player can refute, the suggestion is available as an accusation."));

        //Accusation submenu
        JMenu accusationSubMenu = new JMenu("Accusations");
        accusationSubMenu.add(new JMenuItem("An accusation may be made INSTEAD of a suggestion, OR immediately after an unrefuted suggestion."));
        accusationSubMenu.add(new JMenuItem("An accusation succeeds if it exactly matches the solution, then that player wins the game."));
        accusationSubMenu.add(new JMenuItem("If the accusation fails, that player may no longer make suggestions OR accusations."));
        accusationSubMenu.add(new JMenuItem("A player who has made a false accusation refutes as normal."));

        //Add submenus to rule menu
        rulesMenu.add(movementSubMenu);
        rulesMenu.addSeparator();
        rulesMenu.add(suggestionSubMenu);
        rulesMenu.addSeparator();
        rulesMenu.add(refutingSubMenu);
        rulesMenu.addSeparator();
        rulesMenu.add(accusationSubMenu);

        JPanel logPanel = new JPanel();
        logPanel.setPreferredSize(new Dimension(300, 500));
        logPanel.setLayout(new BorderLayout());
        log = new JEditorPane();
        log.setPreferredSize(new Dimension(290, 450));
        log.setEditable(false);
        log.setText("Text log begins here.");
        JScrollPane logPane = new JScrollPane(log);
        logPane.setPreferredSize(new Dimension(290, 450));
        logPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPanel.add(logPane, BorderLayout.PAGE_START);
        appendToLog(currentPlayer.getCharacter().toString() + " begins their turn.", log);


        JTextField chatBox = new JTextField();
        chatBox.setText("");
        logPanel.add(chatBox, BorderLayout.CENTER);

        JButton okButton = new JButton("OK!");
        okButton.setPreferredSize(new Dimension(60, 40));
        okButton.addActionListener(e -> {
            if (chatBox.getText().length() != 0) {
                appendToLog(currentPlayer.getUsername() + ": " + chatBox.getText(), log);
                chatBox.setText("");
            }
        });
        logPanel.add(okButton, BorderLayout.LINE_END);

        JPanel upperPanel = new JPanel();

        Panel boardPanel = new Panel(new ImageIcon("./assets/cluedo_board.jpg").getImage());

        JButton nextButton = new JButton("Next!");
        nextButton.setEnabled(false);


        JButton dice = new JButton("Roll");
        dice.addActionListener(e -> {
            appendToLog(rollDice(), log);
            dice.setEnabled(false);
        });
        dice.setPreferredSize(new Dimension(80, 30));

        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints diceInsets = new GridBagConstraints();

        diceInsets.insets = new Insets(255, 235, 215, 215);
        boardPanel.add(dice, diceInsets);

        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(boardPanel, BorderLayout.CENTER);
        upperPanel.add(logPanel, BorderLayout.LINE_END);


        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(800, 150));

        JPanel componentPanel = new JPanel();
        componentPanel.setPreferredSize(new Dimension(100, 150));

        JLabel characterNameLabel = new JLabel(currentPlayer.getCharacter().toString());

        JLabel userNameLabel = new JLabel(currentPlayer.getUsername());

        suggestButton = new JButton("Suggest!");
        suggestButton.setEnabled(false);
        suggestButton.addActionListener(e -> buildSuggestWindow());

        accuseButton = new JButton("Accuse!");
        accuseButton.setEnabled(false);
        accuseButton.addActionListener(e -> {
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
            } else {
                buildAccuseWindow();
            }
        });

        nextButton.addActionListener(e -> {
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
            appendToLog(printPlayersInRooms(), log);
            appendToLog(currentPlayer.getCharacter().toString() + " begins their turn.", log);
            currentPlayerMoveEnded = false;
            currentPlayer.setMadeClick(false);
            redraw();
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
                if (!dice.isEnabled() && !currentPlayerMoveEnded) {     //allowing mouse click movement
                    Tile currTile = currentPlayer.getTile();
                    String outcome = processPlayerTurn(getPositionAtClick(e.getX(), e.getY()));
                    if (outcome.equals("")) {
                        if (currentPlayer.isInRoom() && ((!(currTile instanceof RoomTile)))) {  //logic for a normal movement
                            placePlayerInRoom(currentPlayer, currentPlayer.roomPlayerIsIn());
                            if (currentPlayer.hasNotMadeFalseAccusation()) {
                                accuseButton.setEnabled(true);
                                suggestButton.setEnabled(true); //update actions for when player enters room
                            }
                        }
                        redraw();

                        if (currTile instanceof RoomTile && currentPlayer.getTile() instanceof EntranceTile) {
                            boardPanel.addMouseListener(new MouseListener() {   //allow for entrance selection room exit

                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    Tile chosenEntrance = currentPlayer.getTile();
                                    if (chosenEntrance instanceof EntranceTile) {   //if they have chosen an entrance allow next movement
                                        if (!currentPlayer.isMadeClick()) {
                                            String outcome = processPlayerTurn(getPositionAtClick(e.getX(), e.getY()));
                                            if (outcome.equals("")) {
                                                redraw();
                                                nextButton.setEnabled(true);    //ending the turn setup
                                                redraw();
                                                currentPlayerMoveEnded = false;
                                                currentPlayer.setMadeClick(true);
                                            }
                                        } else {
                                            appendToLog(outcome, log);  //print out the problem
                                        }
                                    }
                                }

                                @Override
                                public void mousePressed(MouseEvent e) {        //unused but needed for mouselistener
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
                        }
                        nextButton.setEnabled(true);        //if no second move option setup end of turn
                        redraw();
                        currentPlayerMoveEnded = true;
                    } else {
                        appendToLog(outcome, log);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {    //more unused but necessary implementation
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


    /**
     * Builds the window where a player can make an accusation.
     * The window is simple with just two components: A JList of possible accusations,
     * and a JButton to confirm the selection. Based on Game circumstances and player's
     * choices, a JDialog is produced with the game outcome (Game won, game over for player,
     * game over for everyone).
     */
    private void buildAccuseWindow() {

        DefaultListModel<String> accuseModel = new DefaultListModel<>();
        JList<? extends String> accusationsList = new JList<>(accuseModel);
        for (Hypothesis h : unrefutedSuggestions) {
            accuseModel.addElement(h.toString());
        }
        accusationsList.setPreferredSize(new Dimension(200, 400));


        JButton okButton = new JButton("OK!");
        okButton.setPreferredSize(new Dimension(80, 40));
        okButton.addActionListener(e -> {
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
                } else {
                    currentPlayer.madeFalseAccusation();
                    JOptionPane option = new JOptionPane();
                    option.setOptionType(JOptionPane.DEFAULT_OPTION);
                    if (isGameInvalid()) {
                        option.setMessage("Everyone made a false accusation! The murderer got away with it! \nGame Over!\nThe window will now close.");
                        JDialog dialog = option.createDialog("Game Over");
                        dialog.pack();
                        dialog.setVisible(true);
                        if ((Integer) option.getValue() == JOptionPane.OK_OPTION) {
                            dialog.setVisible(false);
                            System.exit(0);
                        }
                    } else {
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


    /**
     * Builds the window to allow the player to make a suggestion.
     * The suggestion window is simple, with a JComboBox for each element of
     * a Hypothesis (room, weapon, character), and two JButtons - one for
     * cancelling the suggestion, and one for confirming the suggestion.
     * The cancel button will produce a JDialog on click, asking the user
     * if they are sure they want to, which will disable the suggest button if
     * the user selects yes.
     * The confirm button takes the info from the combo boxes, processes suggestion
     * related actions such as moving suggested player to the suggested room, and
     * launches the refute process for each other player, until the suggestion is
     * refuted or all players have had an opportunity to refute unsuccessfully.
     */
    private void buildSuggestWindow() {
        Tile playerTile = currentPlayer.getTile();
        RoomCard room;
        if (playerTile instanceof EntranceTile) {
            room = new RoomCard(((EntranceTile) playerTile).getRoom());
        } else if (playerTile instanceof RoomTile) {
            room = new RoomCard(((RoomTile) playerTile).room);
        } else {
            throw new NoSuchElementException();
        }
        String[] roomArray = new String[1];
        roomArray[0] = room.toString();
        JComboBox<String> rooms = new JComboBox<>(roomArray);


        ArrayList<Player> players = getPlayers();
        String[] characterArray = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            characterArray[i] = players.get(i).getCharacter().toString();
        }
        JComboBox<String> characters = new JComboBox<>(characterArray);

        String[] weaponArray = getWeapons();
        JComboBox<String> weapons = new JComboBox<>(weaponArray);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            JOptionPane option = new JOptionPane();
            option.setOptionType(JOptionPane.YES_NO_OPTION);
            option.setMessage("You won't be able to suggest again if you close this.");
            JDialog dialog = option.createDialog("Are you sure?");
            dialog.pack();
            dialog.setVisible(true);
            if ((Integer) option.getValue() == JOptionPane.YES_OPTION) {
                suggestButton.setEnabled(false);
                dialog.setVisible(false);
                suggestWindow.setVisible(false);
            }
        });
        JButton okayButton = new JButton("OK");
        okayButton.addActionListener(e -> {
            Hypothesis suggestion = playerSuggest(room, (String) characters.getSelectedItem(), (String) weapons.getSelectedItem());
            for (Player p : players) {
                if (p.getCharacter().toString().equals(characters.getSelectedItem())) {
                    appendToLog(playerTeleport(p, currentPlayer.getTile().position), log);
                    redraw();
                    break;
                }
            }

            //Teleport the suggested weapon to the suggested room.
            for (Weapon weapon : getWeaponObjects()) {
                if (weapon.getWeapon().toString().equals(weapons.getSelectedItem())) {
                    appendToLog(weaponTeleport(weapon, currentPlayer.getTile().position), log);
                    redraw();
                    break;
                }
            }

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
            } else {
                checkBool = false;
            }
            suggestWindow.setVisible(false);
            suggestButton.setEnabled(false);
        });

        suggestWindow = new JFrame();
        suggestWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        suggestWindow.setTitle("Make a suggestion.");
        suggestWindow.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //add room ComboBox
        constraints.gridy = 0;
        constraints.gridx = 0;
        suggestWindow.add(rooms, constraints);

        //add characters ComboBox
        constraints.gridy++;
        suggestWindow.add(characters, constraints);

        //add weapons ComboBox
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


    /**
     * Builds the window allowing a specific player to refute against a specific
     * suggestion.
     * The 'window' is actually a JDialog, which prevents any other code from
     * running until the dialog has been closed, which is needed as multiple players
     * get the option to refute.
     * This window is made up of two panels split vertically by a JSplitPane.
     * On the top, the is a JPanel with a number of ImageIcon JLabels, showing
     * the user their cards.
     * On the bottom is the info JPanel, which at minimum contains a JLabel and a JButton.
     * The JLabel informs the player of what they are able to do (can refute with one card,
     * needs to pick a card from 2 or 3 to refute with, or can't refute).
     * The JButton is a go button, which processes the refute (or lack of). This button
     * automatically closes the window on click, as is the nature of a dialog.
     * This panel also potentially includes an amount of JRadioButtons, if the player can
     * refute the suggestion. Theses are used to allow the player to pick the card they
     * wish to refute with.
     *
     * @param p          Player refuting
     * @param suggestion Suggestion to refute against.
     */
    private void buildRefuteWindow(Player p, Hypothesis suggestion) {
        JPanel refuteCardPanel = new JPanel();
        Dimension cardPanelSize = new Dimension(600, 150);
        refuteCardPanel.setPreferredSize(cardPanelSize);
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        refuteCardPanel.setLayout(layout);
        for (Card card : p.getHand()) {
            //all of this is awful math to make sure the cards size nicely on the panel
            ImageIcon icon = new ImageIcon("./assets/cards/" + card.getFileName());
            int width = (int) (((double) (cardPanelSize.width / p.getHand().size())) * 0.7);
            double ratio = ((double) width / icon.getIconWidth());
            ratio = cardPanelSize.height > ratio * icon.getIconHeight() ? ratio : ((double) cardPanelSize.height / icon.getIconHeight()) * 0.9;
            icon = new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * ratio), (int) (icon.getIconHeight() * ratio), Image.SCALE_DEFAULT));

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
        } else if (choices.getButtonCount() == 1) {
            info.setText("Refute Possible, Press Go");
        } else {
            info.setText("Please Pick a Refute Option, Press Go");
        }

        JButton goButton = new JButton("GO");
        goButton.addActionListener(e -> {
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
        if (last != null) {
            last.setSelected(true);
        }

        constraints.gridy++;
        optionPanel.add(info, constraints);

        constraints.gridy++;
        optionPanel.add(goButton, constraints);

        JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, refuteCardPanel, optionPanel);

        refuteWindow = new Frame();
        refuteWindow.setTitle(p.getCharacter().toString());
        JPanel window = new JPanel();
        window.add(mainPane);
        refuteWindow.add(window);

        refuteWindow.pack();
        refuteWindow.setLocationRelativeTo(null);
        refuteWindow.setVisible(true);
    }

    /**
     * Converts the pixel coordinates to a Position relative to the game board.
     *
     * @param x X position
     * @param y Y position
     * @return Returns the position from coordinates
     */
    private Position getPositionAtClick(int x, int y) {
        int col = (int) ((x - left) / cellSize);
        int row = (int) ((y - top) / cellSize);
        return new Position(col, row);

    }

    /**
     * Reloads and repaints the main window.
     */
    public void redraw() {
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    /**
     * Updates to the next players turn.
     */
    public void playerUpdate() {
        updateCurrentPlayer();
        currentPlayer = getCurrentPlayer();
    }

    /**
     * Reloads the card panel on the main board to have the current player's cards.
     */
    private void setupCardPanel() {
        cardPanel = new JPanel();
        Dimension cardPanelSize = new Dimension(700, 150);
        cardPanel.setPreferredSize(cardPanelSize);
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.RIGHT);
        cardPanel.setLayout(layout);
        for (Card card : currentPlayer.getHand()) {
            //all of this is awful math to make sure the cards size nicely on the panel
            ImageIcon icon = new ImageIcon("./assets/cards/" + card.getFileName());
            int width = (int) (((double) (cardPanelSize.width / currentPlayer.getHand().size())) * 0.7);
            double ratio = ((double) width / icon.getIconWidth());
            ratio = cardPanelSize.height > ratio * icon.getIconHeight() ? ratio : ((double) cardPanelSize.height / icon.getIconHeight()) * 0.9;
            icon = new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * ratio), (int) (icon.getIconHeight() * ratio), Image.SCALE_DEFAULT));

            cardPanel.add(new JLabel(icon));
        }
    }


    /**
     * Helper method to add text to the end of the log JEditorPane.
     *
     * @param s   Text to append
     * @param log JEditorPane to append to
     */
    private void appendToLog(String s, JEditorPane log) {
        log.setText(log.getText() + "\n" + s);
    }


    /**
     * Gets the current player.
     *
     * @return returns current player
     */
    protected abstract Player getCurrentPlayer();

    /**
     * Creates a new player object.
     *
     * @param characterName Name of the character player is playing as
     * @param username      Username of player
     */
    protected abstract void createPlayer(String characterName, String username);

    /**
     * Roll dice for player movement.
     *
     * @return Returns a string regarding the dice roll
     */
    protected abstract String rollDice();

    /**
     * Get the icons for all players in play.
     *
     * @return Returns Arraylist of Sprites
     */
    protected abstract ArrayList<Sprite> getPlayerIcons();

    /**
     * Switch the current player to the next player in the order.
     */
    protected abstract void updateCurrentPlayer();

    /**
     * Checks the move attempted by currentPlayer and executes if valid.
     *
     * @param cellToMoveTo Cell on the board player is attempted to move to.
     * @return Returns a string, blank if valid move, reason why move is invalid otherwise
     */
    protected abstract String processPlayerTurn(Position cellToMoveTo);

    /**
     * Creates a Hypothesis from the information gathered.
     *
     * @param room      Room of the hypothesis
     * @param character Character of the hypothesis
     * @param weapon    Weapon of the hypothesis
     * @return Returns the hypothesis
     */
    protected abstract Hypothesis playerSuggest(RoomCard room, String character, String weapon);

    /**
     * Gets a String array of the weapons.
     *
     * @return Returns a String array of weapons
     */
    protected abstract String[] getWeapons();

    /**
     * Gets the players.
     *
     * @return Returns arraylist of players
     */
    protected abstract ArrayList<Player> getPlayers();

    /**
     * Compare a hypothesis to the solution of the game.
     *
     * @param hypothesis Hypothesis to compare
     * @return Returns true if hypothesis matches solution, false if not.
     */
    protected abstract boolean compareToSolution(Hypothesis hypothesis);


    /**
     * Create all of the cards, select a solution, then deal the rest of the cards to the players.
     */
    protected abstract void setupCardsAndGame();

    /**
     * Initializes the game.
     */
    protected abstract void createGame();

    /**
     * Checks if all players have made a false accusation.
     *
     * @return True if all players have lost, otherwise false.
     */
    protected abstract boolean isGameInvalid();

    /**
     * Prints an informative line for each player who is in a room.
     *
     * @return Returns String representation of all players in rooms.
     */
    protected abstract String printPlayersInRooms();

    /**
     * Gets the object versions of the weapons.
     *
     * @return Returns an arraylist of weapon objects
     */
    protected abstract ArrayList<Weapon> getWeaponObjects();

    /**
     * Moves player to a new position outside of regular movement.
     *
     * @param p        Player to move
     * @param position Position to move to
     * @return Returns the outcome, either they moved or were already there.
     */
    protected abstract String playerTeleport(Player p, Position position);

    /**
     * Moves a weapon to a new position.
     *
     * @param weapon Weapon to move.
     * @param position Position to move to.
     * @return Returns the outcome, either they moved or were already there.
     */
    protected abstract String weaponTeleport(Weapon weapon, Position position);

    /**
     * Place player in a random tile within a given room.
     *
     * @param currentPlayer  Player to place
     * @param roomPlayerIsIn Room to place in
     */
    protected abstract void placePlayerInRoom(Player currentPlayer, RoomCard.RoomEnum roomPlayerIsIn);


    /**
     * JDialog Override to allow nice windows that stop everything while existing.
     */
    private static class Frame extends JDialog {
        /**
         * Frame constructor.
         */
        public Frame() {
            setModal(true);
        }
    }


    /**
     * JPanel override class that lets us paint directly onto it.
     */
    private class Panel extends JPanel {

        private final Image img;

        /**
         * Makes a new Panel with a image background.
         *
         * @param img Image to have as background
         */
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
                g.drawImage(s.getIcon(), (int) (s.getPosition().getX() * cellSize) + left, (int) (s.getPosition().getY() * cellSize) + top, null);

            }
            Sprite activePlayer = currentPlayer.getPlayerIcon();
            g.drawImage(activePlayer.getActiveIcon(), (int) (activePlayer.getPosition().getX() * cellSize) + left, (int) (activePlayer.getPosition().getY() * cellSize) + top, null);

            for (Weapon w : getWeaponObjects()) {
                Sprite s = w.getIcon();
                g.drawImage(s.getIcon(), (int) (s.getPosition().getX() * cellSize) + left, (int) (s.getPosition().getY() * cellSize) + top, null);

            }
        }

    }
}
