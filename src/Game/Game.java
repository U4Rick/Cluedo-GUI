package Game;

import Game.Cards.Card;
import Game.Cards.CharacterCard;
import Game.Cards.RoomCard;
import Game.Cards.WeaponCard;
import Game.Actions.Hypothesis;
import Game.Actions.Move;
import Game.Board.Board;
import Game.Entities.Player;
import Game.Entities.Sprite;
import Game.Entities.Weapon;
import Game.Tiles.*;

import java.util.*;

import static Game.Cards.CharacterCard.*;
import static Game.Cards.CharacterCard.CharacterEnum.*;
import static Game.Cards.RoomCard.*;
import static Game.Cards.WeaponCard.*;

/**
 * Main class for Cluedo.
 * Handles game initialisation, game over, and refers to all other
 * classes in running the game via game logic
 */
public class Game extends GUI {

    private Hypothesis solution;
    private Player currentPlayer;
    private int numPlayers;
    private int currentRoll;

    private Board board;
    private List<Player> players = new ArrayList<>();
    private final List<WeaponCard> allWeapons = new ArrayList<>();
    private final List<Sprite> playerIcons = new ArrayList<>();
    private final List<Weapon> weapons = new ArrayList<>();

    /**
     * Creates new instance of game.
     */
    public Game() {

    }

    @Override
    protected void createGame() {
        board = new Board();
        numPlayers = 0;
        players = new ArrayList<>();
    }

    @Override
    protected boolean compareToSolution(Hypothesis hypothesis) {
        return hypothesis.equals(solution);
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
    protected String processPlayerTurn(Position cellToMoveTo) {
        Move move  = new Move(currentPlayer, board, currentRoll);
        return move.playerMovement(cellToMoveTo);
    }

    @Override
    protected Hypothesis playerSuggest(RoomCard room, String character, String weapon) {
        CharacterCard charac = null;
        for (Player p : players) {
            if (p.getCharacter().toString().equals(character)) {
                charac = p.getCharacter();
                break;
            }
        }
        WeaponCard weaponCard = null;
        for (WeaponCard w : allWeapons) {
            if (w.toString().equals(weapon)) {
                weaponCard = w;
                break;
            }
        }
        if (charac == null || weaponCard == null) {
            throw new InputMismatchException();
        }
        return new Hypothesis(charac, weaponCard, room);
    }

    @Override
    protected String[] getWeapons() {
        String[] weapons = new String[allWeapons.size()];
        for (int i = 0; i < allWeapons.size(); i++) { weapons[i] = allWeapons.get(i).toString(); }
        return weapons;
     }

    @Override
    protected ArrayList<Player> getPlayers() {
        if (players instanceof ArrayList) {
            return (ArrayList<Player>) players;
        }
        return null;
    }

    @Override
    protected String rollDice() {
        int dice1 = (int) (Math.random() * 6 + 1);
        int dice2 = (int) (Math.random() * 6 + 1);
        currentRoll = dice1 + dice2;
        return currentPlayer.getCharacter().toString() + " rolled a " + currentRoll + ".";
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
    protected void createPlayer(String characterName, String username) {
        CharacterCard card;
        switch (characterName) {
            case "Plum":
                card = new CharacterCard(PLUM);
                break;

            case "Peacock":
                card = new CharacterCard(PEACOCK);
                break;

            case "Mustard":
                card = new CharacterCard(MUSTARD);
                break;

            case "Scarlett":
                card = new CharacterCard(SCARLETT);
                break;

            case "White":
                card = new CharacterCard(WHITE);
                break;

            case "Green":
                card = new CharacterCard(GREEN);
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
        setUpWeapons();
        dealCards(cards);
    }

    @Override
    protected boolean isGameInvalid() {
        for (Player player : players) {
            if (player.hasNotMadeFalseAccusation()) {
                return false;                     //At least one player is still playing
            }
        }
        return true;
    }

    @Override
    protected String printPlayersInRooms() {
        StringBuilder playersInRooms = new StringBuilder();
        for (Player player : players) {
            for (RoomEnum room : board.getEntrances().keySet()) {
                if (board.getEntrances().get(room).contains(player.getTile().position)) {
                    playersInRooms.append(player.getCharacter().toString()).append(" is in the ").append(new RoomCard(room)).append('\n');
                    break;
                }
            }
            for (RoomEnum room : board.getRoomTiles().keySet()) {
                if (board.getRoomTiles().get(room).contains(player.getTile().position)) {
                    playersInRooms.append(player.getCharacter().toString()).append(" is in the ").append(new RoomCard(room)).append('\n');
                    break;
                }

            }
        }
        return playersInRooms.toString();
    }

    @Override
    protected ArrayList<Weapon> getWeaponObjects() {
        return (ArrayList<Weapon>) weapons;
    }

    @Override
    protected String playerTeleport(Player player, Position position) {
        Tile pos = board.getTileAt(position);
        RoomEnum room;
        if (pos instanceof RoomTile) {
            room = ((RoomTile) pos).room;
        } else if (pos instanceof EntranceTile) {
            room = ((EntranceTile) pos).getRoom();
        }
        else { throw new InputMismatchException(); }
        if (board.getRoomTiles().get(room).contains(player.getTile().getPosition())) {
            return (player.getCharacter().toString() + " is already in the room.");
        }
        else {
            player.getTile().setPlayerOnThisTile(null);
            placePlayerInRoom(player, room);
            return (player.getCharacter().toString() + " moved to suggested room.");
        }
    }

    @Override
    protected String weaponTeleport(Weapon weapon, Position position) {
        Tile pos = board.getTileAt(position);
        RoomEnum room;
        if (pos instanceof RoomTile) {
            room = ((RoomTile) pos).room;
        } else if (pos instanceof EntranceTile) {
            room = ((EntranceTile) pos).getRoom();
        }
        else { throw new InputMismatchException(); }
        if (board.getRoomTiles().get(room).contains(weapon.getTile().getPosition())) {
            return (weapon.getWeapon().toString() + " is already in the room.");
        }
        else {
            weapon.getTile().setPlayerOnThisTile(null);
            placeWeaponInRoom(weapon, room);
            return (weapon.getWeapon().toString() + " moved to suggested room.");
        }
    }

    /**
     * Create all weapons and place them in random rooms.
     */
    private void setUpWeapons() {
        ArrayList<RoomCard> rooms = new ArrayList<>();
        for (RoomEnum room : RoomEnum.values()) {
            RoomCard roomCard = new RoomCard(room);
            rooms.add(roomCard);
        }

        for (WeaponCard w: allWeapons) {
            RoomCard room = rooms.get((int) (Math.random() * rooms.size()));
            rooms.remove(room);
            Weapon weapon = new Weapon(w);
            placeWeaponInRoom(weapon, room.getRoom());
            weapons.add(weapon);
        }
    }


    /**
     * Move the passed Weapon into a random tile within the passed Room.
     *
     * @param weapon Weapon to place.
     * @param room Room to place in.
     */
    private void placeWeaponInRoom(Weapon weapon, RoomEnum room) {

        Tile location;
        while (true) {
            ArrayList<Position> tiles = board.getRoomTiles().get(room);
            location = board.getTileAt(tiles.get((int) (Math.random() * tiles.size())));
            if(location instanceof RoomTile) {
                if (location.getPlayerOnThisTile() == null && ((RoomTile) location).getWeaponOnThisTile() == null) {
                    break;
                }
            }
        }
        ((RoomTile) location).setWeaponOnThisTile(weapon);
        weapon.setTile(location);
    }

    /**
     * Move the passed Player into a random tile within the passed Room.
     *
     * @param player Player to place.
     * @param room Room to place in.
     */
    protected void placePlayerInRoom(Player player, RoomEnum room) {

        Tile location;
        while (true) {
            ArrayList<Position> tiles = board.getRoomTiles().get(room);
            location = board.getTileAt(tiles.get((int) (Math.random() * tiles.size())));
            if(location instanceof RoomTile) {
                if (location.getPlayerOnThisTile() == null && ((RoomTile) location).getWeaponOnThisTile() == null) {
                    break;
                }
            }
        }
        location.setPlayerOnThisTile(player);
        player.setTile(location);
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

    /**
     * Run the game.
     * @param args  ignored
     */
    public static void main(String[] args) {
        new Game();
    }
}