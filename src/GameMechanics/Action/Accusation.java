package GameMechanics.Action;

import GameMechanics.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Accusation {

    private List<Hypothesis> unrefutedSuggestions = new ArrayList<>();
    private final Hypothesis solution;

    public Accusation(List<Hypothesis> suggestions, Hypothesis solution) {
        this.unrefutedSuggestions =  suggestions;
        this.solution = solution;
    }


    public void printSuggestions() {
        StringBuilder result = new StringBuilder();
        int count = 1;
        if (unrefutedSuggestions.isEmpty()) {
            result.append("There are no existing suggestions. \nNo accusation can be made.");
            System.out.println(result);
            sleep();
            return;
        }
        result.append("Choose one:\n");
        for (Hypothesis hypothesis : unrefutedSuggestions) {
            result.append("\t").append(count).append(".").append(hypothesis.toString()).append("\n");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }

    /**
     * Creates a new accusation, if it matches the solution the game is over and the player wins.
     * Otherwise they can no longer make suggestions or accusations. (They can still refute)
     *
     * @return True if game is won, otherwise false.
     */
    public boolean playerAccusation(Player currentPlayer) {
        printSuggestions();
        if (!unrefutedSuggestions.isEmpty()) {
            Hypothesis selected = selectSuggestion();

            if (selected.equals(solution)) {
                System.out.println(currentPlayer.getCharacter() + " has won the game!");
                return true;
            } else {
                currentPlayer.madeFalseAccusation();
                System.out.println(currentPlayer + " made a false accusation!");
            }
        }
        sleep();
        return false;
    }

    /**
     * Select from user input a suggestion from unrefutedSuggestions for use in an accusation.
     *
     * @return Selected Hypothesis.
     */
    public Hypothesis selectSuggestion() {
        Scanner scan = new Scanner(System.in);

        //Loop until valid number input
        while (true) {
            if (scan.hasNextInt()) {
                int userInput = scan.nextInt();
                if (userInput >= 1 && userInput <= unrefutedSuggestions.size()) {
                    return unrefutedSuggestions.get(userInput - 1);
                }
            }
        }
    }
    private void sleep() {
        try { Thread.sleep(2000); } catch (Exception e) { System.out.println(e.toString()); }
    }
}
