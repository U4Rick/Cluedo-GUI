package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import GameMechanics.Action.Accusation;
import GameMechanics.Action.Hypothesis;
import GameMechanics.Action.Suggestion;
import Tiles.*;

import java.util.*;

import static Cards.CharacterCard.*;
import static Cards.RoomCard.*;
import static Cards.WeaponCard.*;

public class Game {

    private int movementRange = 999;
    private boolean hasMadeValidMove = false;

    //GameMechanics.Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;
    private int numPlayers;
    private boolean playerHasWon = false;
    private final String contentBreak = "\n-------------------------------------------\n";

    //GameMechanics.Game Associations
    private Board board;
    private final List<Player> players = new ArrayList<>();
    private final List<WeaponCard> allWeapons = new ArrayList<>();
    private final List<Hypothesis> unrefutedSuggestions = new ArrayList<>();

    public Game() {
        initialise();
        run();
    }

    /**
     * Setup the game.
     */
    public void initialise() {
        board = new Board();
        setNumPlayers();
        setupPlayers();
        setupCards();
        currentPlayer = players.get(0);
    }

    /**
     * Main game loop, loops until a player has won or all players have made false accusations.
     */
    private void run() {
        while (!isGameOver()) {
            printTurnInfo();
            processPlayerTurn();
            updateCurrentPlayer();
        }
    }

    //////////////////////////
    // SETUP METHODS
    //////////////////////////

    /**
     * Ask the player for amount of players, must be between 3-6
     */
    public void setNumPlayers() {
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
    }

    /**
     * Create the players and add them to the list of players.
     */
    private void setupPlayers() {
        CharacterEnum[] values = CharacterEnum.values();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player(new CharacterCard(values[i]), board.getTileAt(board.getStartingTiles().get(values[i])), board);
            Tile startingTile = board.getTileAt(board.getStartingTiles().get(values[i]));
            if (startingTile instanceof HallwayTile) { startingTile.setPlayerOnThisTile(p); }
            players.add(p);
        }
    }

    /**
     * Create all of the cards, select a solution, then deal the rest of the cards to the players.
     */
    private void setupCards() {
        solution = new Hypothesis(null, null, null);

        ArrayList<Card> cards = new ArrayList<>(setupCharacterCards());
        cards.addAll(setupWeaponCards());
        cards.addAll(setupRoomCards());

        dealCards(cards);
    }

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
    private void processPlayerTurn() {
        playerMovement();
        if (currentPlayer.canHypothesise()) {
            playerHypothesis();
        }
    }

    /**
     * Set currentPlayer to next player in turn order.
     */
    private void updateCurrentPlayer() {
        int i = players.indexOf(currentPlayer);
        if (i == players.size() - 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(i + 1);
        }
    }

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
    // MOVEMENT
    //////////////////////////

    /**
     * Handles user input and console output for movement.
     * Calls necessary methods to control movement.
     */
    private void playerMovement() {
        //roll dice
        //movementRange = rollDice(); TODO uncomment at the end
        System.out.println("You rolled a " + movementRange);

        //ask for tile to move to
        Scanner sc = new Scanner(System.in);
        hasMadeValidMove = false;
        while(!hasMadeValidMove) {

            System.out.println("Your position \nRow: " + currentPlayer.getTile().position.getY()
                    + "\nCol: " + currentPlayer.getTile().position.getX());
            System.out.println("\nEnter column to move to:");
            int moveCol = sc.nextInt();
            System.out.println("Enter row to move to:");
            int moveRow = sc.nextInt();

            if (moveRow < 25 && moveRow >= 0 && moveCol < 24 && moveCol >= 0) {
                move(moveCol, moveRow); //check if requested tile is within board bounds
            }
            else{
                System.out.println("Invalid row/column, try again.");
            }
        }
    }

    /**
     * Checks if the requested move of the player is a valid movement.
     * Checks for out of board, out of move range, tile types.
     * @param startX    Starting x position
     * @param startY    Starting y position
     * @param endX      Ending x position
     * @param endY      Ending y position
     * @return  true if valid move, false if invalid move.
     */
    public Boolean isValidMovement(int startX, int startY, int endX, int endY){

        Position endPos = new Position(endX, endY);
        Position startPos = new Position(startX, startY);

        //if endtile == inaccessible
        if(board.getTileAt(endPos) instanceof InaccessibleTile){
            System.out.println("Inaccessible Tile");
            return false;
        }

        Tile endTile = board.getTileAt(endPos);
        if((endTile.getPlayerOnThisTile() != null) && !(board.getTileAt(endPos) instanceof EntranceTile)){
            System.out.println("Tile already has player on it");
            return false;//else if endPos already has player && endPos is not entranceTile
        }

        if(Math.abs((startX - endX) + (startY - endY)) > this.movementRange){
            System.out.println("You can not move that far!");
            return false;
        }//else if Math.abs((startPos.x + endPos .x) + (startPos.y + endPos.y)) > movementRange

        hasMadeValidMove = true;
        return true;
    }

    /**
     * @param x
     * @param y
     */
    public void move(int x, int y) {

        Tile startTile = currentPlayer.getTile();   //tile before moving
        Position startPos = startTile.position; //position before moving
        Position endPos = new Position(x,y); // position to move to
        Tile endTile = board.getTileAt(endPos);    //tile to move to
        int playerX = currentPlayer.getTile().position.getX();    //current X
        int playerY = currentPlayer.getTile().position.getY();    //current Y
        if(isValidMovement(playerX, playerY, x, y)){
            //board.setTileAt(endPos, currentPlayer);  //move current player to their end position
            //board.setTileAt(startPos,null);//set the start position to null
            currentPlayer.setTile(endTile);
            endTile.setPlayerOnThisTile(currentPlayer);
            startTile.setPlayerOnThisTile(null);
            //board.draw();
        }
    }

    /**
     * Rolls two dice and returns the sum of them.
     *
     * @return Sum of two dice.
     */
    private int rollDice(){
        int dice1 = (int) (Math.random() * 6 + 1);
        int dice2 = (int) (Math.random() * 6 + 1);
        return dice1 + dice2;
    }

    //////////////////////////
    // PRINT METHODS
    //////////////////////////

    /**
     * Print current board and name of current player.
     */
    private void printTurnInfo() {
        System.out.println(contentBreak);
        System.out.print("\n");
        System.out.println(board.toString());
        printPlayersInRooms();
        System.out.println(currentPlayer.getCharacter());
        currentPlayer.displayHand();
    }

    /**
     *
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

    //////////////////////////
    // GETTERS & SETTERS
    //////////////////////////

    public static void main(String[] args) {
        Game game = new Game();
    }
}