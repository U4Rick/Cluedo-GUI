/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5099.60569f335 modeling language!*/


import Cards.Card;
import Cards.CharacterCard;
import Tiles.Tile;

import java.util.*;

// line 23 "model.ump"
// line 112 "model.ump"
public class Player {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Attributes
    private CharacterCard character;
    private Tile position;
    private boolean madeFalseAccusation;

    //Player Associations
    private List<Card> hand;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(CharacterCard aCharacter, Tile aPosition, boolean aMadeFalseAccusation) {
        character = aCharacter;
        position = aPosition;
        madeFalseAccusation = aMadeFalseAccusation;
        hand = new ArrayList<Card>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCharacter(CharacterCard aCharacter) {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    public boolean setPosition(Tile aPosition) {
        boolean wasSet = false;
        position = aPosition;
        wasSet = true;
        return wasSet;
    }

    public boolean setMadeFalseAccusation(boolean aMadeFalseAccusation) {
        boolean wasSet = false;
        madeFalseAccusation = aMadeFalseAccusation;
        wasSet = true;
        return wasSet;
    }

    public CharacterCard getCharacter() {
        return character;
    }

    public Tile getPosition() {
        return position;
    }

    public boolean getMadeFalseAccusation() {
        return madeFalseAccusation;
    }

    /* Code from template association_GetMany */
    public Card getHand(int index) {
        Card aHand = hand.get(index);
        return aHand;
    }

    /**
     * ArrayList<Cards.Card> hand;
     * Just creates list instead of arraylist.
     */
    public List<Card> getHand() {
        List<Card> newHand = Collections.unmodifiableList(hand);
        return newHand;
    }

    public int numberOfHand() {
        int number = hand.size();
        return number;
    }

    public boolean hasHand() {
        boolean has = hand.size() > 0;
        return has;
    }

    public int indexOfHand(Card aHand) {
        int index = hand.indexOf(aHand);
        return index;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfHand() {
        return 0;
    }

    /* Code from template association_AddUnidirectionalMany */
    public boolean addHand(Card aHand) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            return false;
        }
        hand.add(aHand);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeHand(Card aHand) {
        boolean wasRemoved = false;
        if (hand.contains(aHand)) {
            hand.remove(aHand);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
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

    // line 31 "model.ump"
    public void move() {

    }

    // line 32 "model.ump"
    public boolean canRefute() {
        return false;
    }

    // line 33 "model.ump"
    public void suggest() {

    }

    // line 34 "model.ump"
    public void accuse() {

    }

    // line 35 "model.ump"
    public void refute() {

    }


    public String toString() {
        return super.toString() + "[" +
                "madeFalseAccusation" + ":" + getMadeFalseAccusation() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this) ? getCharacter().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this) ? getPosition().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}