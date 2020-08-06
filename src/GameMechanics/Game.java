package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import Tiles.InaccessibleTile;
import Tiles.Position;
import Tiles.RoomTile;
import Tiles.Tile;

import java.util.*;

import static Cards.CharacterCard.*;
import static Cards.RoomCard.*;
import static Cards.WeaponCard.*;

public class Game {



    public enum TurnState {Playing, Finished;}

    //GameMechanics.Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;
    private int numPlayers;
    public int movementRange;

    //GameMechanics.Game Associations
    private Board board;
    private List<Player> players = new ArrayList<>();

    public Game() {
        initialise();
        gameLoop();
        //GameMechanics.Hypothesis solution, GameMechanics.Player currentPlayer, GameMechanics.Board board, GameMechanics.Player... allPlayers

        /*this.solution = solution;
        this.currentPlayer = currentPlayer;
        if (!setBoard(board)) {
            throw new RuntimeException("Unable to create GameMechanics.Game due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        players = new ArrayList<GameMechanics.Player>();
        boolean didAddPlayers = setPlayers(allPlayers);
        if (!didAddPlayers) {
            throw new RuntimeException("Unable to create GameMechanics.Game, must have 3 to 6 players. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }*/
    }

    private void gameLoop() {
        while (!isGameOver()) {
            //show board
            System.out.println(board.toString());

            //print current player name
            System.out.println(currentPlayer.getCharacter().convertToFullName());

            //roll dice
            movementRange = rollDice();
            System.out.println("You rolled a " + movementRange);

            //ask for tile to move to
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a row to move to:");
            int userRow = sc.nextInt();
            System.out.println("Enter a column to move to:");
            int userCol = sc.nextInt();
            move(userRow, userCol);

            //check if in room
            /*if (currentPlayer.getPosition()) {
            }*/

            //opt. make suggestion/accusation

            //update current player
            updateCurrentPlayer();


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

    //TODO get movement working
    public Boolean isValidMovement(int startX, int startY, int endX, int endY){

        //if endtile == inaccessible
        if(board.getTileAt(endX, endY) instanceof InaccessibleTile){
            System.out.println("Inaccessible Tile");
            return false;
        }

        else if(endX > 25 || endY > 24){//these might not be the right values?
            System.out.println("Invalid Coordinate");
            return false;
        }

        else if((board.getTileAt(endX, endY).hasPlayer()) && !(board.getTileAt(endX, endY) instanceof RoomTile)){
            System.out.println("Tile already has player on it");
            return false;
        }//else if endPos already has player && endPos is not entranceTile


        else if(Math.abs((startX + endX) + (startY + endY)) > movementRange){
            System.out.println("You can not move that far!");
            return false;
        }//else if Math.abs((startPos.x + endPos .x) + (startPos.y + endPos.y)) > movementRange

        return true;
    }
    //TODO get movement working
    public void move(int x, int y) {
        Tile startTile = currentPlayer.getPosition();
        if(currentPlayer.getPosition() == null){
            currentPlayer.setPosition(new Tile(new Position(0,0)));
        }
        Tile endTile = new Tile(new Position(x,y)); // tile to move to
        int playerX = currentPlayer.getPosition().getP().getX();    //current X
        int playerY = currentPlayer.getPosition().getP().getY();    //current Y
        if(isValidMovement(playerX, playerY, endTile.getP().getX(), endTile.getP().getY())){
            board.setTileAt(x,y,currentPlayer);  //move current player to that tile
            board.setTileAt(startTile.getP().getX(), startTile.getP().getY(),null);//set the start position to null
            board.draw();
        }
    }

    /**
     * Check whether a player has won, or all players have made false accusations.
     *
     * @return True if game over, otherwise false.
     */
    private boolean isGameOver() {
        return false;
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
        setNumPlayers();
        setupPlayers();
        //TODO: setup players being placed into starting tiles.
        setupCards();
        board = new Board();
        currentPlayer = players.get(0);
        //System.out.println(board.toString());
    }

    //TODO loop if incorrect input

    /**
     * Ask the player for amount of players, must be between 3-6
     */
    public void setNumPlayers() {
        System.out.println("How many players?");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int num = scan.nextInt();
            //check within range
            if (num >= 3 && num <= 6) {
                numPlayers = num;
            }
        }
    }

    /**
     * Create the players and add them to the list of players.
     */
    private void setupPlayers() {
        characters[] values = characters.values();
        for (int i = 0; i < numPlayers; i++) {
            //TODO tile
            players.add(new Player(new CharacterCard(values[i]), null));

        }
    }

    /**
     * Create all of the cards, select a solution, then deal the rest of the cards to the players.
     */
    private void setupCards() {
        //Char cards
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Card> solutionCards = new ArrayList<>();
        solution = new Hypothesis(null, null, null);

        for (Player player : players) {
            cards.add(player.getCharacter());
            solutionCards.add(player.getCharacter());
        }

        Collections.shuffle(solutionCards);
        solution.setCharacter((CharacterCard) solutionCards.get(0));
        cards.remove(solutionCards.get(0));
        solutionCards.clear();

        //Weapon cards
        for (weapons weapon : weapons.values()) {
            WeaponCard weaponCard = new WeaponCard(weapon);
            cards.add(weaponCard);
            solutionCards.add(weaponCard);
        }

        Collections.shuffle(solutionCards);
        solution.setWeapon((WeaponCard) solutionCards.get(0));
        cards.remove(solutionCards.get(0));
        solutionCards.clear();

        //Room cards
        for (rooms room : rooms.values()) {
            RoomCard roomCard = new RoomCard(room);
            cards.add(roomCard);
            solutionCards.add(roomCard);
        }

        Collections.shuffle(solutionCards);
        solution.setRoom((RoomCard) solutionCards.get(0));
        cards.remove(solutionCards.get(0));
        solutionCards.clear();

        dealCards(cards);
    }

    /**
     * Deal the cards to the players.
     *
     * @param cards List of cards to deal to players.
     */
    private void dealCards(ArrayList<Card> cards) {
        int player = 0;
        for (Card card : cards) {
            players.get(player).addHand(card);
            if (player != numPlayers - 1) { player++; }
            else { player = 0; }


        }
    }


    public boolean setSolution(Hypothesis solution) {
        boolean wasSet = false;
        this.solution = solution;
        wasSet = true;
        return wasSet;
    }

    public boolean setCurrentPlayer(Player currentPlayer) {
        boolean wasSet = false;
        this.currentPlayer = currentPlayer;
        wasSet = true;
        return wasSet;
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

    public Player getPlayer(int index) {
        Player player = players.get(index);
        return player;
    }

    public List<Player> getPlayers() {
        List<Player> players = Collections.unmodifiableList(this.players);
        return players;
    }

    public int numberOfPlayers() {
        int number = players.size();
        return number;
    }

    public boolean hasPlayers() {
        boolean has = players.size() > 0;
        return has;
    }

    public int indexOfPlayer(Player player) {
        int index = players.indexOf(player);
        return index;
    }

    public boolean setBoard(Board newBoard) {
        boolean wasSet = false;
        if (newBoard != null) {
            this.board = newBoard;
            wasSet = true;
        }
        return wasSet;
    }

    public static int minimumNumberOfPlayers() {
        return 3;
    }

    public static int maximumNumberOfPlayers() {
        return 6;
    }

    public boolean addPlayer(Player player) {
        boolean wasAdded = false;
        if (players.contains(player)) {
            return false;
        }
        if (numberOfPlayers() < maximumNumberOfPlayers()) {
            players.add(player);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean removePlayer(Player player) {
        boolean wasRemoved = false;
        if (!players.contains(player)) {
            return wasRemoved;
        }

        if (numberOfPlayers() <= minimumNumberOfPlayers()) {
            return wasRemoved;
        }

        players.remove(player);
        wasRemoved = true;
        return wasRemoved;
    }

    public boolean setPlayers(Player... newPlayers) {
        boolean wasSet = false;
        ArrayList<Player> verifiedPlayers = new ArrayList<Player>();
        for (Player player : newPlayers) {
            if (verifiedPlayers.contains(player)) {
                continue;
            }
            verifiedPlayers.add(player);
        }

        if (verifiedPlayers.size() != newPlayers.length || verifiedPlayers.size() < minimumNumberOfPlayers() || verifiedPlayers.size() > maximumNumberOfPlayers()) {
            return wasSet;
        }

        players.clear();
        players.addAll(verifiedPlayers);
        wasSet = true;
        return wasSet;
    }

    public boolean addPlayerAt(Player player, int index) {
        boolean wasAdded = false;
        if (addPlayer(player)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPlayers()) {
                index = numberOfPlayers() - 1;
            }
            players.remove(player);
            players.add(index, player);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMovePlayerAt(Player player, int index) {
        boolean wasAdded = false;
        if (players.contains(player)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPlayers()) {
                index = numberOfPlayers() - 1;
            }
            players.remove(player);
            players.add(index, player);
            wasAdded = true;
        } else {
            wasAdded = addPlayerAt(player, index);
        }
        return wasAdded;
    }

    public void delete() {
        board = null;
        players.clear();
    }

    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "solution" + "=" + (getSolution() != null ? !getSolution().equals(this) ? getSolution().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "currentPlayer" + "=" + (getCurrentPlayer() != null ? !getCurrentPlayer().equals(this) ? getCurrentPlayer().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "board = " + (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null");
    }

    public static void main(String[] args) {
        Game coolbeans = new Game();
    }
}