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

    public boolean setCharacter(CharacterCard character) {
        boolean wasSet = false;
        this.character = character;
        wasSet = true;
        return wasSet;
    }

    public boolean setPosition(Tile position) {
        boolean wasSet = false;
        this.tile = position;
        wasSet = true;
        return wasSet;
    }

    public boolean setMadeFalseAccusation(boolean madeFalseAccusation) {
        boolean wasSet = false;
        this.madeFalseAccusation = madeFalseAccusation;
        wasSet = true;
        return wasSet;
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
    public Card getHand(int index) {
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
    public int numberOfHand() {
        int number = hand.size();
        return number;
    }

    public boolean hasHand() {
        boolean hasHand = hand.size() > 0;
        return hasHand;
    }

    //TODO ?????
    public int indexOfHand(Card aHand) {
        int index = hand.indexOf(aHand);
        return index;
    }

    public static int minimumNumberOfHand() {
        return 0;
    }

    //TODO naming, what are these?
    public boolean addHand(Card aHand) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            return false;
        }
        hand.add(aHand);
        wasAdded = true;
        return wasAdded;
    }

    //TODO remove card from hand?
    public boolean removeHand(Card aHand) {
        boolean wasRemoved = false;
        if (hand.contains(aHand)) {
            hand.remove(aHand);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    //TODO lul
    public boolean addHandAt(Card aHand, int index) {
        boolean wasAdded = false;
        if (addHand(aHand)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfHand()) {
                index = numberOfHand() - 1;
            }
            hand.remove(aHand);
            hand.add(index, aHand);
            wasAdded = true;
        }
        return wasAdded;
    }

    //TODO stop
    public boolean addOrMoveHandAt(Card aHand, int index) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfHand()) {
                index = numberOfHand() - 1;
            }
            hand.remove(aHand);
            hand.add(index, aHand);
            wasAdded = true;
        } else {
            wasAdded = addHandAt(aHand, index);
        }
        return wasAdded;
    }

    public void delete() {
        hand.clear();
    }



    public boolean canRefute() {
        return false;
    }

    public void suggest() {

    }

    public void accuse() {

    }

    public void refute() {

    }


    public String toString() {
        switch(character.getCharacter()) {
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
                return "";
        }
    }

    public void displayHand() {
        StringBuilder handAsText = new StringBuilder();

        handAsText.append("Your hand: ");
        for (Card card : hand) {
            handAsText.append(card.toString()).append(", ");
        }
        handAsText.delete(handAsText.length() - 3, handAsText.length() - 1);    //TODO check range

        System.out.println(handAsText);
    }

    /**
     * Check if player is in a room.
     *
     * @return True if player is in a room, otherwise false.
     */
    public boolean isInRoom() {
        for (Position entrance : board.startingTiles.values()) {
            if (tile.getPosition().equals(entrance)) {
                return true;
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