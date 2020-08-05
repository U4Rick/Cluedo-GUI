package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import Tiles.HallwayTile;
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

    //GameMechanics.Game Associations
    private Board board;
    private List<Player> players = new ArrayList<>();

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
        if (true) { //TODO if in room

            playerHypothesis();
        }
    }

    private void playerMovement() {
        //roll dice
        int movementRange = rollDice();

        //ask for tile to move to

        //check if in room
            /*if (currentPlayer.getPosition()) {
            }*/

        //opt. make suggestion/accusation

        //update current player
    }

    private void playerHypothesis() {

    }

    /**
     * Print current board and name of current player.
     */
    private void printTurnInfo() {
        System.out.println(board.toString());
        System.out.println(currentPlayer.getCharacter().convertToFullName());
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
        board = new Board();
        setNumPlayers();
        setupPlayers();
        setupCards();
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
            Player p = new Player(new CharacterCard(values[i]), board.getTileAt(board.startingTiles.get(values[i])));
            Tile startingTile = board.getTileAt(board.startingTiles.get(values[i]));
            if (startingTile instanceof HallwayTile) { ((HallwayTile) startingTile).setPlayerOnThisTile(p); }
            players.add(p);

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

            //Roll back to first player
            if (player != numPlayers - 1) {
                player++; }
            else {
                player = 0;
            }
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
        Game game = new Game();
    }
}