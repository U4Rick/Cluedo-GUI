package Game.Entities;

import Game.Cards.Card;
import Game.Cards.CharacterCard;
import Game.Cards.RoomCard;
import Game.Actions.Hypothesis;
import Game.Board.Board;
import Game.Tiles.Position;
import Game.Tiles.Tile;

import java.util.*;


/**
 * Holds the information unique to each player.
 */
public class Player {

    private final CharacterCard character;
    private Tile tile;
    private boolean madeFalseAccusation = false;
    private boolean madeClick = false;

    private final String username;
    private final List<Card> hand;
    private final Board board;

    private final Sprite playerIcon;

    /**
     * Initialises the player.
     * @param character Card of the character the player is playing as
     * @param tile      The tile the player starts on
     * @param board     The board of the game
     */
    public Player(CharacterCard character, Tile tile, Board board, String username) {
        this.character = character;
        this.tile = tile;
        this.board = board;
        this.username = username;
        this.playerIcon = new Sprite(this.getCharacter().getFileName(), tile.position, "c");
        hand = new ArrayList<>();
    }

    /**
     * Setter for madeClick field.
     *
     * @param madeClick Boolean to set madeClick as.
     */
    public void setMadeClick(boolean madeClick) {
        this.madeClick = madeClick;
    }

    /**
     * Getter for boolean field madeClick.
     *
     * @return Boolean value of madeClick.
     */
    public boolean isMadeClick() {
        return madeClick;
    }

    /**
     * Add a card to the players hand.
     *
     * @param card Card to add to the hand.
     */
    public void addCardToHand(Card card) {
        if (!hand.contains(card)) {
            hand.add(card);
        }
    }

    /**
     * Setter for tile.
     *
     * @param tile New tile to set current as.
     */
    public void setTile(Tile tile) {

        this.tile = tile;
        playerIcon.updatePosition(tile.position);
    }

    /**
     * Sets madeFalseAccusation to true.
     */
    public void madeFalseAccusation() {
        this.madeFalseAccusation = true;
    }


    /**
     * Getter for character.
     *
     * @return character.
     */
    public CharacterCard getCharacter() {
        return character;
    }

    /**
     * Getter for tile.
     *
     * @return tile.
     */
    public Tile getTile() {
        return tile;
    }


    /**
     * Get a list of all cards that could be used as to refute the passed suggestion.
     *
     * @param activeSuggestion Suggestion to check based off.
     * @return ArrayList of potential cards, could be empty.
     */
    public ArrayList<Card> getRefutableCards(Hypothesis activeSuggestion) {
        ArrayList<Card> refutableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.equals(activeSuggestion.getCharacter()) || card.equals(activeSuggestion.getRoom()) || card.equals(activeSuggestion.getWeapon())) {
                refutableCards.add(card);
            }
        }
        return refutableCards;
    }

    /**
     * Getter for madeFalseAccusation.
     *
     * @return madeFalseAccusation.
     */
    public boolean hasNotMadeFalseAccusation() {
        return !madeFalseAccusation;
    }

    /**
     * Check if player is in a room.
     *
     * @return True if player is in a room, otherwise false.
     */
    public boolean isInRoom() {
        for (ArrayList<Position> entrance : board.getEntrances().values()) {
            for (Position position : entrance) {
                if (tile.getPosition().equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the room that the player is in.
     *
     * @return RoomEnum if the player is in a room, otherwise Null.
     */
    public RoomCard.RoomEnum roomPlayerIsIn() {
        for (RoomCard.RoomEnum room : board.getEntrances().keySet()) {
            if (board.getEntrances().get(room).contains(tile.getPosition())) {
                return room;
            }
        }
        return null;
    }

    /**
     * Getter for playerIcon.
     *
     * @return Sprite representing the player.
     */
    public Sprite getPlayerIcon() { return playerIcon; }

    @Override
    public String toString() {
        switch (character.getCharacter()) {
            case PEACOCK:
                return "PC";
            case MUSTARD:
                return "MU";
            case PLUM:
                return "PL";
            case GREEN:
                return "GR";
            case WHITE:
                return "WH";
            case SCARLETT:
                return "SC";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Getter for username.
     *
     * @return Username of player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for hand.
     *
     * @return Hand of player.
     */
    public List<Card> getHand() {
        return hand;
    }
}