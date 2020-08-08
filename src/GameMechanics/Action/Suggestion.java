package GameMechanics.Action;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import GameMechanics.Board;
import GameMechanics.Player;
import Tiles.EntranceTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Suggestion extends Action {

    private final Player currentPlayer;
    private List<Player> players = new ArrayList<>();

    public Suggestion(Player currentPlayer, List<Player> players) {
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    /**
     * Create a new Hypothesis from user input.
     *
     * @return Newly created Hypothesis.
     */
    public Hypothesis createNewSuggestion() {
        CharacterCard character = characterFromInput();
        WeaponCard weapon = weaponFromInput();

        EntranceTile entranceTile = (EntranceTile) currentPlayer.getTile();
        RoomCard room = new RoomCard(entranceTile.getRoom());

        return new Hypothesis(character, weapon, room);
    }

    /**
     *  Allows player to make a suggestion.
     *  Prints out relevant info needed to do so, and runs createNewSuggestion().
     *  Moves the relevant player of suggestion to the room if not already there,
     *  then runs the refute() method for each player other than current.
     * @return true if suggestion was refuted, false if unrefuted.
     */
    public boolean playerSuggestion(List<Hypothesis> unrefutedSuggestions, List<WeaponCard> weapons, Board board) {

        currentPlayer.displayHand();
        printPotentialCharacters();
        printPotentialWeapons(weapons);
        Hypothesis activeSuggestion = createNewSuggestion();
        System.out.println("Hypothesis: \n" + activeSuggestion);

        //Move targeted player to current tile
        for (Player p: players) {
            if (p.getCharacter().equals(activeSuggestion.getCharacter())) {
                playerTeleport(p, currentPlayer.getTile().position, board);
                break;
            }
        }

        Refute refute = new Refute();
        int numPlayers = players.size();
        //take turns refuting
        int index = players.indexOf(currentPlayer);
        for (int i = 0; i < numPlayers - 1; i++) {

            //Roll over to index 0
            if (index == players.size() - 1) {
                index = 0;
            } else {
                index++;
            }

            Player refutingPlayer = players.get(index);
            if (refute.refute(refutingPlayer, activeSuggestion)) {
                return true;
            }
        }

        System.out.println("Nobody was able to refute!");
        unrefutedSuggestions.add(activeSuggestion);      //Add to collection if no one refutes
        return false;
    }

    /**
     * Creates a character from user input after validation.
     *
     * @return CharacterCard created from user input.
     */
    private CharacterCard characterFromInput() {
        Scanner scan = new Scanner(System.in);
        CharacterCard character = null;

        do {
            System.out.println("\nSuggest a character using initials... (eg. Miss Scarlett = SC)");
            String userInput = scan.next();
            //System.out.println(userInput);

            switch (userInput) {
                case "MU" -> character = new CharacterCard(CharacterCard.CharacterEnum.MUSTARD);
                case "WH" -> character = new CharacterCard(CharacterCard.CharacterEnum.WHITE);
                case "GR" -> character = new CharacterCard(CharacterCard.CharacterEnum.GREEN);
                case "PC" -> character = new CharacterCard(CharacterCard.CharacterEnum.PEACOCK);
                case "PL" -> character = new CharacterCard(CharacterCard.CharacterEnum.PLUM);
                case "SC" -> character = new CharacterCard(CharacterCard.CharacterEnum.SCARLETT);
            }
        } while (character == null);

        return character;
    }

    /**
     * Create weapon from user input after validation.
     *
     * @return WeaponCard create from user input.
     */
    private WeaponCard weaponFromInput() {
        Scanner scan = new Scanner(System.in);
        WeaponCard weapon = null;

        do {
            System.out.println("Suggest a weapon using number between 1 and 6... (eg. Candlestick = 1)");
            String userInput = scan.next();

            switch (userInput) {
                case "1" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.CANDLESTICK);
                case "2" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.LEADPIPE);
                case "3" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.DAGGER);
                case "4" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.REVOLVER);
                case "5" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.ROPE);
                case "6" -> weapon = new WeaponCard(WeaponCard.WeaponEnum.SPANNER);
            }
        } while (weapon == null);

        return weapon;
    }

    /**
     *
     */
    private void printPotentialWeapons(List<WeaponCard> allWeapons) {
        StringBuilder result = new StringBuilder();
        result.append("Weapons: ");
        int count = 1;
        for (WeaponCard weapon : allWeapons) {
            result.append(count).append(".").append(weapon.toString()).append(" ");
            count++;
        }
        result.delete(result.length() - 1, result.length());
        System.out.println(result);
    }



    /**
     *
     */
    private void printPotentialCharacters() {
        StringBuilder result = new StringBuilder();
        result.append("Characters: ");
        for (Player player : players) {
            result.append(player.getCharacter().toString()).append(", ");
        }
        result.delete(result.length() - 2, result.length() - 1);
        System.out.println(result);
    }
}
