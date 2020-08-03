import java.util.*;

public class Game {

    public enum TurnState {Playing, Finished}

    //Game Attributes
    private Hypothesis solution;
    private Player currentPlayer;

    //Game Associations
    private Board board;
    private List<Player> players;

    public Game(Hypothesis solution, Player currentPlayer, Board board, Player... allPlayers) {
        this.solution = solution;
        this.currentPlayer = currentPlayer;
        if (!setBoard(board)) {
            throw new RuntimeException("Unable to create Game due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        players = new ArrayList<Player>();
        boolean didAddPlayers = setPlayers(allPlayers);
        if (!didAddPlayers) {
            throw new RuntimeException("Unable to create Game, must have 3 to 6 players. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
}