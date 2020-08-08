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

    public enum TurnState {Playing, Finished;}

    //GameMechanics.Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;
    private int numPlayers;

    private int turn = 0; //FIXME remove when implementing working gameover
    private boolean playerHasWon = false;

    //GameMechanics.Game Associations
    private Board board;
    private List<Player> players = new ArrayList<>();
    private List<WeaponCard> allWeapons = new ArrayList<>();
    private List<Hypothesis> unrefutedSuggestions = new ArrayList<>();


    public Game() {
        initialise();
        run();
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

    /**
     *
     */
    private void processPlayerTurn() {
        playerMovement();
        if (currentPlayer.canHypothesise()) {
            //currentPlayer.displayHand();
            playerHypothesis();
        }
    }

    private void playerMovement() {
        System.out.println("Player movement called");
        //roll dice
       // movementRange = rollDice();
        System.out.println("You rolled a " + movementRange);
        //ask for tile to move to
        Scanner sc = new Scanner(System.in);
        hasMadeValidMove = false;
        while(!hasMadeValidMove) {
            System.out.println("current col coord = " + currentPlayer.getTile().position.getX());
            System.out.println("current row coord = " + currentPlayer.getTile().position.getY());//todo just temp

            System.out.println("Enter column to move to:");
            int moveCol = sc.nextInt();
            System.out.println("Enter row to move to:");
            int moveRow = sc.nextInt();

            if (moveRow < 25 && moveRow >= 0 && moveCol < 24 && moveCol >= 0) {
                move(moveCol, moveRow);//check if requested tile is even within 24x25
            }//these might not be the right values?
            else{
                System.out.println("Invalid row/column, try again.");
            }

        }


        //opt. make suggestion/accusation

        //update current player
    }


    /**
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
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
     *
     */
    private void playerHypothesis() {
        Scanner scan = new Scanner(System.in);
        System.out.println("player hypothesis called");

        //Get input from user
        System.out.println("Make a Suggestion? (Y or N)");
        String userInput = scan.next();

        if (userInput.equalsIgnoreCase("Y")) {
            playerSuggestion();
            return;
        }

        System.out.println("Make an Accusation?");
        userInput = scan.next();
        if (userInput.equalsIgnoreCase("Y")) {
            playerAccusation();
            return;
        }

        System.out.println("No action selected.");

    }

    /**
     *
     */
    private void playerSuggestion() {

        currentPlayer.displayHand();
        printPotentialCharacters();
        printPotentialWeapons();
        Hypothesis activeSuggestion = createNewSuggestion();
        System.out.println(activeSuggestion);
        for (Player p: players) {
            if (p.getCharacter().equals(activeSuggestion.getCharacter())) {
                playerTeleport(p, currentPlayer.getTile().position);
                break;
            }
        }

        //take turns refuting
        int index = players.indexOf(currentPlayer);
        do {
            //Roll over to index 0
            if (index == players.size() - 1) {
                index = 0;
            } else {
                index++;
            }

            Player refutingPlayer = players.get(index);
            if (refute(refutingPlayer, activeSuggestion)) {
                return;
            }

        } while (index != players.indexOf(currentPlayer));

        System.out.println("Nobody was able to refute!");
        unrefutedSuggestions.add(activeSuggestion);                             //Add to collection if no one refutes
        //TODO add optional make accusation
    }

    /**
     * @param p
     * @param pos
     */
    public void playerTeleport(Player p, Position pos) {
        if (p.getTile() != board.getTileAt(pos)) {
            p.getTile().setPlayerOnThisTile(null);
            p.setTile(board.getTileAt(pos));
            System.out.println(p.toString() + " moved to suggested room.");
        }
        else {
            System.out.println(p.toString() + " is already in the room.");
        }


    }

    /**
     * @param refutingPlayer
     * @param activeSuggestion
     * @return
     */
    private boolean refute(Player refutingPlayer, Hypothesis activeSuggestion) {
        StringBuilder result = new StringBuilder();
        result.append(refutingPlayer.getCharacter());
        ArrayList<Card> refutableCards = refutingPlayer.getRefutableCards(activeSuggestion);

        refutingPlayer.displayHand(); //debug
        System.out.println(refutableCards.size()); //debug

        if (!refutableCards.isEmpty()) {
            if (refutableCards.size() == 1) {
                System.out.println(result.append(" refutes with ").append(refutableCards.get(0)));
            } else {
                System.out.println(result.append(refuteWithMultiple(refutableCards)));
            }
            return true;
        } else {
            result.append(" cannot refute.");
        }
        System.out.println(result);
        return false;
    }

    /**
     * @param refutableCards
     * @return
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
        result.append("Choose one: ");
        for (Hypothesis hypothesis : unrefutedSuggestions) {
            result.append(count).append(".").append(hypothesis.toString()).append(" ");
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

    /**
     * Creates a new accusation, if it matches the solution the game is over and the player wins.
     * Otherwise they can no longer make suggestions or accusations. (They can still refute)
     */
    private void playerAccusation() {
        printSuggestions();
        Hypothesis selected = selectSuggestion();

        if (selected.equals(solution)) {
            System.out.println(currentPlayer + " has won the game!");
            this.playerHasWon = true;
        } else {
            currentPlayer.madeFalseAccusation();
        }
    }


    private Hypothesis selectSuggestion() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            if (scan.hasNextInt()) {
                int userInput = scan.nextInt();
                if (userInput >= 0 && userInput < unrefutedSuggestions.size()) {
                    return unrefutedSuggestions.get(userInput);
                }
            }
        }
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

    /**
     * Creates a character from user input after validation.
     *
     * @return CharacterCard created from user input.
     */
    private CharacterCard characterFromInput() {
        Scanner scan = new Scanner(System.in);
        CharacterCard character = null;

        do {
            System.out.println("Suggest a character using initials... (eg. Miss Scarlett = SC)");
            String userInput = scan.next();
            System.out.println(userInput);

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
     * Print current board and name of current player.
     */
    private void printTurnInfo() {
        System.out.println(board.toString());
        System.out.println(currentPlayer.getCharacter());
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
     * Check whether a player has won, or all players have made false accusations.
     *
     * @return True if game over, otherwise false.
     */
    private boolean isGameOver() {
        //return playerHasWon || isGameInvalid(); //FIXME uncomment when game actually working
        return tempGameOver();
    }

    //temp method
    private boolean tempGameOver() {
        if (turn >= 10) {
            return true;
        }
        turn++;
        return false;
    }

    /**
     * Checks if all players have made a false accusation.
     *
     * @return True if all players have lost, otherwise false.
     */
    private boolean isGameInvalid() {
        for (Player player : players) {
            if (!player.getMadeFalseAccusation()) {
                return false;                                                   //At least one player is still playing
            }
        }
        System.out.println("The murderer got away with it! Everybody loses!");
        return true;
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

    /**
     * Setup the game.
     */
    public void initialise() {
        board = new Board();
        setNumPlayers();
        setupPlayers();
        setupCards();
        currentPlayer = players.get(0);
        //System.out.println(board.toString());
    }

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
            players.get(player).addCard(card);

            //Roll back to first player
            if (player != numPlayers - 1) {
                player++; }
            else {
                player = 0;
            }
        }
    }

    public Hypothesis getSolution() {
        return solution;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "solution" + "=" + (getSolution() != null ? !getSolution().equals(this) ? getSolution().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "currentPlayer" + "=" + (getCurrentPlayer() != null ? !getCurrentPlayer().equals(this) ? getCurrentPlayer().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "board = " + (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null");
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}