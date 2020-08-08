package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
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
            Player p = new Player(new CharacterCard(values[i]), board.getTileAt(board.startingTiles.get(values[i])), board);
            Tile startingTile = board.getTileAt(board.startingTiles.get(values[i]));
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
            if (playerSuggestion()) {
                return;
            }
        }

        //Check if player wants to make an accusation.
        System.out.println("Make an Accusation?");
        userInput = scan.next();
        if (userInput.equalsIgnoreCase("Y")) {
            playerAccusation();
            return;
        }

        System.out.println("No action selected.");
    }


    /**
     *  Allows player to make a suggestion.
     *  Prints out relevant info needed to do so, and runs createNewSuggestion().
     *  Moves the relevant player of suggestion to the room if not already there,
     *  then runs the refute() method for each player other than current.
     * @return true if suggestion was refuted, false if unrefuted.
     */
    private boolean playerSuggestion() {

        currentPlayer.displayHand();
        printPotentialCharacters();
        printPotentialWeapons();
        Hypothesis activeSuggestion = createNewSuggestion();
        System.out.println("Hypothesis: \n" + activeSuggestion);

        //Move targeted player to current tile
        for (Player p: players) {
            if (p.getCharacter().equals(activeSuggestion.getCharacter())) {
                playerTeleport(p, currentPlayer.getTile().position);
                break;
            }
        }

        //take turns refuting
        int index = players.indexOf(currentPlayer);
        for (int i = 0; i < numPlayers - 1; i++) {

            //Roll over to index 0
            if (index == players.size() - 1) {
                index = 0;
            } else {
                index++;
            }

            Player refutingPlayer = players.get(index);
            if (refute(refutingPlayer, activeSuggestion)) {
                return true;
            }
        }

        System.out.println("Nobody was able to refute!");
        unrefutedSuggestions.add(activeSuggestion);      //Add to collection if no one refutes
        return false;
    }

    /**
     * Creates a new accusation, if it matches the solution the game is over and the player wins.
     * Otherwise they can no longer make suggestions or accusations. (They can still refute)
     */
    private void playerAccusation() {
        printSuggestions();
        if (!unrefutedSuggestions.isEmpty()) {
            Hypothesis selected = selectSuggestion();

            if (selected.equals(solution)) {
                this.playerHasWon = true;
                System.out.println(currentPlayer + " has won the game!");
            } else {
                currentPlayer.madeFalseAccusation();
                System.out.println(currentPlayer + " made a false accusation!");
            }
        }
        sleep();
    }

    /**
     * Teleports a player to a room if they're not already there.
     * @param player     Player to move
     * @param position   Position to move to
     */
    public void playerTeleport(Player player, Position position) {
        System.out.println("\n");
        if (player.getTile() != board.getTileAt(position)) {
            player.getTile().setPlayerOnThisTile(null);
            player.setTile(board.getTileAt(position));
            System.out.println(player.toString() + " moved to suggested room.");
        }
        else {
            System.out.println(player.toString() + " is already in the room.");
        }
    }

    /**
     * Checks if a player can refute, and acts on the outcome.
     * If no refute found, program prints accordingly.
     * If one refute is found, program prints accordingly
     * If multiple refutes are found, player gets to choose which card
     * they would like to refute with.
     * @param refutingPlayer    Player to refute
     * @param activeSuggestion  The suggestion to refute against
     * @return  true if refute occurred, false if not
     */
    private boolean refute(Player refutingPlayer, Hypothesis activeSuggestion) {
        StringBuilder result = new StringBuilder();
        result.append(refutingPlayer.getCharacter());
        ArrayList<Card> refutableCards = refutingPlayer.getRefutableCards(activeSuggestion);

        System.out.println("\n");
        if (!refutableCards.isEmpty()) {
            if (refutableCards.size() == 1) {
                System.out.println(result.append(" refutes with ").append(refutableCards.get(0)));

            } else {
                System.out.println(refutingPlayer + " turn to refute.");
                System.out.println(result.append(refuteWithMultiple(refutableCards)));
            }
            sleep();
            return true;
        }
        result.append(" cannot refute.");
        sleep();
        System.out.println(result);
        return false;
    }

    /**
     * Create a new Hypothesis from user input.
     *
     * @return Newly created Hypothesis.
     */
    public Hypothesis createNewSuggestion() {
        CharacterCard character = characterFromInput();
        WeaponCard weapon = weaponFromInput();

        EntranceTile entranceTile = (EntranceTile) currentPlayer.getTile();
        RoomCard room = new RoomCard(entranceTile.getRoom());

        return new Hypothesis(character, weapon, room);
    }

    //////////////////////////
    // USER INPUT
    //////////////////////////

    /**
     * Creates a character from user input after validation.
     *
     * @return CharacterCard created from user input.
     */
    private CharacterCard characterFromInput() {
        Scanner scan = new Scanner(System.in);
        CharacterCard character = null;

        do {
            System.out.println("\nSuggest a character using initials... (eg. Miss Scarlett = SC)");
            String userInput = scan.next();
            //System.out.println(userInput);

            switch (userInput) {
                case "MU" -> character = new CharacterCard(CharacterEnum.MUSTARD);
                case "WH" -> character = new CharacterCard(CharacterEnum.WHITE);
                case "GR" -> character = new CharacterCard(CharacterEnum.GREEN);
                case "PC" -> character = new CharacterCard(CharacterEnum.PEACOCK);
                case "PL" -> character = new CharacterCard(CharacterEnum.PLUM);
                case "SC" -> character = new CharacterCard(CharacterEnum.SCARLETT);
            }
        } while (character == null);

        return character;
    }

    /**
     * Create weapon from user input after validation.
     *
     * @return WeaponCard create from user input.
     */
    private WeaponCard weaponFromInput() {
        Scanner scan = new Scanner(System.in);
        WeaponCard weapon = null;

        do {
            System.out.println("Suggest a weapon using number between 1 and 6... (eg. Candlestick = 1)");
            String userInput = scan.next();

            switch (userInput) {
                case "1" -> weapon = new WeaponCard(WeaponEnum.CANDLESTICK);
                case "2" -> weapon = new WeaponCard(WeaponEnum.LEADPIPE);
                case "3" -> weapon = new WeaponCard(WeaponEnum.DAGGER);
                case "4" -> weapon = new WeaponCard(WeaponEnum.REVOLVER);
                case "5" -> weapon = new WeaponCard(WeaponEnum.ROPE);
                case "6" -> weapon = new WeaponCard(WeaponEnum.SPANNER);
            }
        } while (weapon == null);

        return weapon;
    }

    /**
     * Takes input from user regarding multiple card refutes.
     * @param refutableCards The cards the player can refute with
     * @return  the String output of chosen refute card
     */
    private String refuteWithMultiple(ArrayList<Card> refutableCards) {
        printRefutableCards(refutableCards);
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        StringBuilder result = new StringBuilder();
        result.append(" refutes with ");

        while (true) {
            switch (userInput) {
                case "1" -> {
                    return result.append(refutableCards.get(0)).toString();
                }
                case "2" -> {
                    return result.append(refutableCards.get(1)).toString();
                }
                case "3" -> {
                    return result.append(refutableCards.get(2)).toString();
                }
            }
        }
    }

    /**
     * Select from user input a suggestion from unrefutedSuggestions for use in an accusation.
     *
     * @return Selected Hypothesis.
     */
    private Hypothesis selectSuggestion() {
        Scanner scan = new Scanner(System.in);

        //Loop until valid number input
        while (true) {
            if (scan.hasNextInt()) {
                int userInput = scan.nextInt();
                if (userInput >= 1 && userInput <= unrefutedSuggestions.size()) {
                    return unrefutedSuggestions.get(userInput - 1);
                }
            }
        }
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
            if (!player.getMadeFalseAccusation()) {
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
            for (RoomEnum r : board.entrances.keySet()) {
                if (board.entrances.get(r).contains(player.getTile().position)) {
                    System.out.println(player.getCharacter().toString() + " is in the " + new RoomCard(r));
                    break;
                }
            }
        }
        System.out.print("\n");
    }

    /**
     *
     */
    private void printPotentialWeapons() {
        StringBuilder result = new StringBuilder();
        result.append("Weapons: ");
        int count = 1;
        for (WeaponCard weapon : allWeapons) {
            result.append(count).append(".").append(weapon.toString()).append(" ");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }

    private void printSuggestions() {
        StringBuilder result = new StringBuilder();
        int count = 1;
        if (unrefutedSuggestions.isEmpty()) {
            result.append("There are no existing suggestions. \nNo accusation can be made.");
            System.out.println(result);
            sleep();
            return;
        }
        result.append("Choose one:\n");
        for (Hypothesis hypothesis : unrefutedSuggestions) {
            result.append("\t").append(count).append(".").append(hypothesis.toString()).append("\n");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }

    /**
     *
     */
    private void printPotentialCharacters() {
        StringBuilder result = new StringBuilder();
        result.append("Characters: ");
        for (Player player : players) {
            result.append(player.getCharacter().toString()).append(", ");
        }
        result.delete(result.length() - 2, result.length() - 1);
        System.out.println(result);
    }

    /**
     * @param cards
     */
    private void printRefutableCards(ArrayList<Card> cards) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        result.append("Choose one: ");
        for (Card card : cards) {
            result.append(count).append(".").append(card.toString()).append(" ");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }

    //////////////////////////
    // HELPER METHODS
    //////////////////////////

    private void sleep() {
        try { Thread.sleep(2000); } catch (Exception e) { System.out.println(e.toString()); }
    }

    //////////////////////////
    // GETTERS & SETTERS
    //////////////////////////

    public static void main(String[] args) {
        Game game = new Game();
    }
}