package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;

import java.util.*;

import static Cards.CharacterCard.*;
import static Cards.RoomCard.*;
import static Cards.WeaponCard.*;

public class Game {

    public enum TurnState {Playing, Finished}

    //GameMechanics.Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;

    //GameMechanics.Game Associations
    private Board board;
    private List<Player> players = new ArrayList<>();
    private int numPlayers;

    public Game() {
        initialise();

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

    public void initialise() {
        setNumPlayers();
        setupPlayers();
        //TODO: setup players being placed into starting tiles.
        setupCards();
        board = new Board();
        currentPlayer = players.get(0);
        System.out.println(board.toString());
    }

    //TODO loop if incorrect input

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
    private void setupPlayers() {
        characters[] values = characters.values();
        for (int i = 0; i < numPlayers; i++) {
            //TODO tile
            players.add(new Player(new CharacterCard(values[i]), null));

        }
    }

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

    public boolean isOver() {
      return false;
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