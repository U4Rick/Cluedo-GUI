package GameMechanics;

import Cards.Card;
import Cards.CharacterCard;
import Tiles.Position;
import Tiles.Tile;

import java.util.*;

public class Player {

    //GameMechanics.Player Attributes
    private CharacterCard character;
    private Tile tile;
    private boolean madeFalseAccusation = false;

    //GameMechanics.Player Associations
    private List<Card> hand;
    private Board board;
    public int movementRange;

    public Player(CharacterCard character, Tile tile, Board board) {
        this.character = character;
        this.tile = tile;
        this.board = board;
        hand = new ArrayList<>();
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void madeFalseAccusation() {
        this.madeFalseAccusation = true;
    }

    public CharacterCard getCharacter() {
        return character;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean getMadeFalseAccusation() {
        return madeFalseAccusation;
    }

    /**
     * TODO: This isn't right, needs to return some kind of collection. should be get card?
     *
     * @param index
     * @return
     */
    public Card getCard(int index) {
        Card hand = this.hand.get(index);
        return hand;
    }

    /**
     * ArrayList<Cards.Card> hand;
     * Just creates list instead of arraylist.
     */
    public List<Card> getHand() {
        List<Card> hand = Collections.unmodifiableList(this.hand);
        return hand;
    }

    //TODO: size of hand?
    public int sizeOfHand() {
        int number = hand.size();
        return number;
    }

    //TODO ?????
    public int indexOfCard(Card card) {
        int index = hand.indexOf(card);
        return index;
    }

    //TODO naming, what are these?
    public boolean addCard(Card card) {
        boolean wasAdded = false;
        if (hand.contains(card)) {
            return false;
        }
        hand.add(card);
        wasAdded = true;
        return wasAdded;
    }

    public ArrayList<Card> getRefutableCards(Hypothesis activeSuggestion) {
        ArrayList<Card> refutableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.equals(activeSuggestion.getCharacter()) || card.equals(activeSuggestion.getRoom()) || card.equals(activeSuggestion.getWeapon())) {
                refutableCards.add(card);
            }
        }
        return refutableCards;
    }

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

    public void displayHand() {
        StringBuilder handAsText = new StringBuilder();

        handAsText.append("Your hand: ");
        for (Card card : hand) {
            handAsText.append(card.toString()).append(", ");
        }
        handAsText.delete(handAsText.length() - 2, handAsText.length() - 1);

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