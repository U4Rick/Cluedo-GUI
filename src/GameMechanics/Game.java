package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import GameMechanics.Action.Accusation;
import GameMechanics.Action.Hypothesis;
import GameMechanics.Action.Move;
import GameMechanics.Action.Suggestion;
import Tiles.*;

import java.util.*;

import static Cards.CharacterCard.*;
import static Cards.RoomCard.*;
import static Cards.WeaponCard.*;

/**
 * Main class for Cluedo.
 * Handles game initialisation, game over, and refers to all other
 * classes in running the game via game logic
 */
public class Game extends GUI {

    //GameMechanics.Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;
    private int numPlayers;
    private boolean playerHasWon = false;
    private int currentRoll;

    //GameMechanics.Game Associations
    private Board board;
    private List<Player> players = new ArrayList<>();
    private final List<WeaponCard> allWeapons = new ArrayList<>();
    private final List<Hypothesis> unrefutedSuggestions = new ArrayList<>();
    private final List<Sprite> playerIcons = new ArrayList<>();

    /**
     * Creates new instance of game.
     * Run initialise methods and then run the game.
     */
    public Game() {
        /*initialise();
        run();*/
    }

    @Override
    protected void create() {
        board = new Board();
        numPlayers = 0;
        players = new ArrayList<>();
    }

    @Override
    protected void updateCurrentPlayer() {
        int i = players.indexOf(currentPlayer);
        if (i == players.size() - 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(i + 1);
        }
    }

    @Override
    protected boolean processPlayerTurn(Position cellToMoveTo) {
        Move move  = new Move(currentPlayer, board, currentRoll);
        return move.playerMovement(cellToMoveTo);
    }

    @Override
    protected String[] getWeapons() {
        String[] weapons = new String[allWeapons.size()];
        for (int i = 0; i < allWeapons.size(); i++) { weapons[i] = allWeapons.get(i).toString(); }
        return weapons;
     }

    @Override
    protected ArrayList getPlayers() {
        return (ArrayList)players;
    }

    @Override
    protected String rollDice() {
        int dice1 = (int) (Math.random() * 6 + 1);
        int dice2 = (int) (Math.random() * 6 + 1);
        currentRoll = dice1 + dice2;
        return "You have rolled a " + currentRoll + ".";
    }

    @Override
    protected ArrayList<Sprite> getPlayerIcons() {
        return (ArrayList<Sprite>) playerIcons;
    }

    @Override
    protected Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    protected void createPlayer(String character, String username) {
        CharacterCard card;
        switch (character) {
            case "Plum":
                card = new CharacterCard(CharacterEnum.PLUM);
                break;

            case "Peacock":
                card = new CharacterCard(CharacterEnum.PEACOCK);
                break;

            case "Mustard":
                card = new CharacterCard(CharacterEnum.MUSTARD);
                break;

            case "Scarlett":
                card = new CharacterCard(CharacterEnum.SCARLETT);
                break;

            case "White":
                card = new CharacterCard(CharacterEnum.WHITE);
                break;

            case "Green":
                card = new CharacterCard(CharacterEnum.GREEN);
                break;

            default:
                throw new NoSuchElementException();
        }
        Player p = new Player(card, board.getTileAt(board.getStartingTiles().get(card.getCharacter())), board, username);
        Tile startingTile = board.getTileAt(board.getStartingTiles().get(card.getCharacter()));
        if (startingTile instanceof HallwayTile) { startingTile.setPlayerOnThisTile(p); }
        players.add(p);
        playerIcons.add(p.getPlayerIcon());
        numPlayers++;
    }

    @Override
    protected void setupCardsAndGame() {
        currentPlayer = players.get(0);
        solution = new Hypothesis(null, null, null);

        ArrayList<Card> cards = new ArrayList<>(setupCharacterCards());
        cards.addAll(setupWeaponCards());
        cards.addAll(setupRoomCards());

        dealCards(cards);
    }

    /**
     * Setup the game.
     */
    //replaced by abstract methods
   /* public void initialise() {
        board = new Board();
        //setNumPlayers();
        //setupPlayers();
        setupCards();
        currentPlayer = players.get(0);
    }*/

    /**
     * Main game loop, loops until a player has won or all players have made false accusations.
     */
    /*private void run() {
        while (!isGameOver()) {
            printTurnInfo();
            processPlayerTurn();
            updateCurrentPlayer();
        }
    }*/

    //////////////////////////
    // SETUP METHODS
    //////////////////////////

    /**
     * Ask the player for amount of players, must be between 3-6.
     */
    //replaced in gui
    /*public void setNumPlayers() {
        System.out.println("How many players?");


        //Loop until number of player is set
        while (true) {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int num = scan.nextInt();
                //check within range
                if (num >= 3 && num <= 6) {
                    numPlayers = num;
                    break;
                }
            }
            System.out.println("Enter a number between 3 and 6");
        }
    }*/

    /**
     * Create the players and add them to the list of players.
     */
    //replaced with create player abstract void
    /*private void setupPlayers() {
        CharacterEnum[] values = CharacterEnum.values();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player(new CharacterCard(values[i]), board.getTileAt(board.getStartingTiles().get(values[i])), board);
            Tile startingTile = board.getTileAt(board.getStartingTiles().get(values[i]));
            if (startingTile instanceof HallwayTile) { startingTile.setPlayerOnThisTile(p); }
            players.add(p);
        }
    }*/



    /**
     * For every active player, creates a character card. Removes one at random and sets as solution. Returns the rest.
     *
     * @return List of all active characterCards, with solution removed.
     */
    private ArrayList<? extends Card> setupCharacterCards() {
        ArrayList<CharacterCard> characterCards = new ArrayList<>();

        //Create all the cards representing current players
        for (Player player : players) {
            characterCards.add(player.getCharacter());
        }

        //Remove one at random and set aside as the solution
        Collections.shuffle(characterCards);
        solution.setCharacter(characterCards.get(0));
        characterCards.remove(0);

        return characterCards;
    }

    /**
     * Create a weaponCard for every weapon. Removes one at random and sets as solution. Returns the rest.
     *
     * @return List of all weaponCards, with solution removed.
     */
    private Collection<? extends Card> setupWeaponCards() {
        ArrayList<WeaponCard> weaponCards = new ArrayList<>();

        //Create weapon cards for all WeaponEnum and add to collection
        for (WeaponEnum weapon : WeaponEnum.values()) {
            WeaponCard weaponCard = new WeaponCard(weapon);
            weaponCards.add(weaponCard);
            allWeapons.add(weaponCard);
        }

        //Remove one at random and set aside as the solution
        Collections.shuffle(weaponCards);
        solution.setWeapon(weaponCards.get(0));
        weaponCards.remove(0);

        return weaponCards;
    }

    /**
     * Create a roomCard for every room. Removes one at random and sets as solution. Returns the rest.
     *
     * @return List of all roomCards, with solution removed.
     */
    private Collection<? extends Card> setupRoomCards() {
        ArrayList<RoomCard> roomCards = new ArrayList<>();

        //Create weapon cards for all WeaponEnum and add to collection
        for (RoomEnum room : RoomEnum.values()) {
            RoomCard roomCard = new RoomCard(room);
            roomCards.add(roomCard);
        }

        //Remove one at random and set aside as the solution
        Collections.shuffle(roomCards);
        solution.setRoom(roomCards.get(0));
        roomCards.remove(0);

        return roomCards;
    }

    /**
     * Deal the cards to the players.
     *
     * @param cards List of cards to deal to players.
     */
    private void dealCards(ArrayList<Card> cards) {
        int player = 0;
        for (Card card : cards) {
            players.get(player).addCardToHand(card);

            //Roll back to first player
            if (player != numPlayers - 1) {
                player++; }
            else {
                player = 0;
            }
        }
    }

    //////////////////////////
    // PLAYER TURN
    //////////////////////////

    /**
     *  Runs the player movement and suggestion/accusation process for
     *  current player.
     */
    /*private void processPlayerTurn() {
        Move move  = new Move(currentPlayer, board, currentRoll);
        move.playerMovement();
        if (currentPlayer.canHypothesise()) {
            playerHypothesis();
        }
    }*/

    /**
     * Set currentPlayer to next player in turn order.
     */
    //replaced by abstract
   /* private void updateCurrentPlayer() {
        int i = players.indexOf(currentPlayer);
        if (i == players.size() - 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(i + 1);
        }
    }*/

    /**
     *  Checks if player wants to make a suggestion and/or accusation,
     *  and runs relevant methods to those choices.
     */
    private void playerHypothesis() {
        Scanner scan = new Scanner(System.in);

        //Check if player wants to make a suggestion.
        System.out.println("Make a Suggestion? (Y or N)");
        String userInput = scan.next();
        System.out.println("\n");
        if (userInput.equalsIgnoreCase("Y")) {
            Suggestion suggestion = new Suggestion(currentPlayer, players);
            if (suggestion.playerSuggestion(unrefutedSuggestions, allWeapons, board)) {
                return;
            }
        }

        //Check if player wants to make an accusation.
        System.out.println("Make an Accusation?");
        userInput = scan.next();
        if (userInput.equalsIgnoreCase("Y")) {
            Accusation accusation = new Accusation(unrefutedSuggestions, solution);
            if (accusation.playerAccusation(currentPlayer)) {
                playerHasWon = true;
            }
            return;
        }

        System.out.println("No action selected.");
    }

    //////////////////////////
    // GAME OVER
    //////////////////////////

    /**
     * Check whether a player has won, or all players have made false accusations.
     *
     * @return True if game over, otherwise false.
     */
    private boolean isGameOver() {
        return playerHasWon || isGameInvalid();
    }

    /**
     * Checks if all players have made a false accusation.
     *
     * @return True if all players have lost, otherwise false.
     */
    private boolean isGameInvalid() {
        for (Player player : players) {
            if (player.hasNotMadeFalseAccusation()) {
                return false;                     //At least one player is still playing
            }
        }
        System.out.println("The murderer got away with it! Everybody loses!");
        return true;
    }



    //////////////////////////
    // PRINT METHODS
    //////////////////////////

    /**
     * Print current board and name of current player.
     */
    private void printTurnInfo() {
        System.out.println("\n-------------------------------------------\n");
        System.out.print("\n");
        System.out.println(board.toString());
        printPlayersInRooms();
        System.out.println(currentPlayer.getCharacter());
        currentPlayer.displayHand();
    }

    /**
     * Prints an informative line for each player who is in a room.
     */
    private void printPlayersInRooms() {
        for (Player player : players) {
            for (RoomEnum room : board.getEntrances().keySet()) {
                if (board.getEntrances().get(room).contains(player.getTile().position)) {
                    System.out.println(player.getCharacter().toString() + " is in the " + new RoomCard(room));
                    break;
                }
            }
        }
        System.out.println();
    }

    /**
     * Run the game.
     * @param args  ignored
     */
    public static void main(String[] args) {
        Game game = new Game();
    }
}