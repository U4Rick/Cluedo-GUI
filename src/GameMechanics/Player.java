package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Cards.RoomCard;
import GameMechanics.Action.Hypothesis;
import Tiles.Position;
import Tiles.Tile;

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

    public RoomCard.RoomEnum roomPlayerIsIn() {
        for (RoomCard.RoomEnum room : board.getEntrances().keySet()) {
            if (board.getEntrances().get(room).contains(tile.getPosition())) {
                return room;
            }
        }
        return null;
    }

    public Sprite getPlayerIcon() { return playerIcon; }


    /**
     * Prints the players hand as text to the screen.
     */
    public void displayHand() {
        StringBuilder handAsText = new StringBuilder();

        handAsText.append("Your hand: ");
        for (Card card : hand) {
            handAsText.append(card.toString()).append(", ");
        }
        handAsText.delete(handAsText.length() - 2, handAsText.length() - 1);
        handAsText.append("\n");

        System.out.println(handAsText);
    }


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

    public String getUsername() {
        return username;
    }

    public List<Card> getHand() {
        return hand;
    }
}