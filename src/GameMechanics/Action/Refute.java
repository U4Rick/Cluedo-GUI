package GameMechanics.Action;

import Cards.Card;
import GameMechanics.Player;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class Refute extends Action {

    /**
     *
     */
    public Refute() {}

    /**
     * Checks if a player can refute, and acts on the outcome.
     * If no refute found, program prints accordingly.
     * If one refute is found, program prints accordingly
     * If multiple refutes are found, player gets to choose which card
     * they would like to refute with.
     * @param refutingPlayer    Player to refute
     * @param activeSuggestion  The suggestion to refute against
     * @return  true if refute occurred, false if not
     */
    public boolean refute(Player refutingPlayer, Hypothesis activeSuggestion) {
        StringBuilder result = new StringBuilder();
        result.append(refutingPlayer.getCharacter());
        ArrayList<Card> refutableCards = refutingPlayer.getRefutableCards(activeSuggestion);

        System.out.println("\n");
        if (!refutableCards.isEmpty()) {
            if (refutableCards.size() == 1) {
                System.out.println(result.append(" refutes with ").append(refutableCards.get(0)));

            } else {
                System.out.println(refutingPlayer + " turn to refute.");
                System.out.println(result.append(refuteWithMultiple(refutableCards)));
            }
            sleep();
            return true;
        }
        result.append(" cannot refute.");
        sleep();
        System.out.println(result);
        return false;
    }

    /**
     * Takes input from user regarding multiple card refutes.
     * @param refutableCards The cards the player can refute with
     * @return  the String output of chosen refute card
     */
    private String refuteWithMultiple(ArrayList<Card> refutableCards) {
        printRefutableCards(refutableCards);
        Scanner scan = new Scanner(System.in);
        String userInput = scan.next();
        StringBuilder result = new StringBuilder();
        result.append(" refutes with ");

        while (true) {
            switch (userInput) {
                case "1" -> {
                    return result.append(refutableCards.get(0)).toString();
                }
                case "2" -> {
                    return result.append(refutableCards.get(1)).toString();
                }
                case "3" -> {
                    return result.append(refutableCards.get(2)).toString();
                }
            }
        }
    }

    /**
     * @param cards
     */
    private void printRefutableCards(ArrayList<Card> cards) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        result.append("Choose one: ");
        for (Card card : cards) {
            result.append(count).append(".").append(card.toString()).append(" ");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }


}
