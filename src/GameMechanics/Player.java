package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Tiles.Position;
import Tiles.Tile;

import java.util.*;

public class Player {

    //GameMechanics.Player Attributes
    private final CharacterCard character;
    private Tile tile;
    private boolean madeFalseAccusation = false;

    //GameMechanics.Player Associations
    private final List<Card> hand;
    private final Board board;
    public int movementRange;

    public Player(CharacterCard character, Tile tile, Board board) {
        this.character = character;
        this.tile = tile;
        this.board = board;
        hand = new ArrayList<>();
    }

    /**
     * Setter for tile.
     *
     * @param tile New tile to set current as.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
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
     * Getter for madeFalseAccusation.
     *
     * @return madeFalseAccusation.
     */
    public boolean getMadeFalseAccusation() {
        return madeFalseAccusation;
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

    @Override
    public String toString() {
        return switch (character.getCharacter()) {
            case PEACOCK -> "PC";
            case MUSTARD -> "MU";
            case PLUM -> "PL";
            case GREEN -> "GR";
            case WHITE -> "WH";
            case SCARLETT -> "SC";
        };
    }

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

    /**
     * Check if player is in a room.
     *
     * @return True if player is in a room, otherwise false.
     */
    public boolean isInRoom() {
        for (ArrayList<Position> entrance : board.entrances.values()) {
            for (Position position : entrance) {    //TODO cleaner way to do this?
                if (tile.getPosition().equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A player can only hypothesise if they landed on a room entrance, and they have not made a false accusation.
     *
     * @return True if they can make hypothesis, otherwise false.
     */
    public boolean canHypothesise() {
        return isInRoom() && !getMadeFalseAccusation();
    }
}