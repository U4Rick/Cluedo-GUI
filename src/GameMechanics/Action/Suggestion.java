package GameMechanics.Action;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import GameMechanics.Board;
import GameMechanics.Player;
import Tiles.EntranceTile;

import java.util.List;
import java.util.Scanner;

//TODO confirm class is redundant and dispose

/**
 *  A suggested murder circumstance.
 */
public class Suggestion extends Action {

    private final Player currentPlayer;
    private final List<Player> players;

    /**
     * Initialise a new Suggestion.
     * @param currentPlayer Player making the suggestion
     * @param players   The players in the game
     */
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
     * @param unrefutedSuggestions  Collection to add suggestion to if successfully unrefuted
     * @param weapons   Weapons that are available to make a suggestion with
     * @param board The board we suggest in reference to
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
                case "MU":
                    character = new CharacterCard(CharacterCard.CharacterEnum.MUSTARD);
                    break;
                case "WH":
                    character = new CharacterCard(CharacterCard.CharacterEnum.WHITE);
                    break;
                case "GR":
                    character = new CharacterCard(CharacterCard.CharacterEnum.GREEN);
                    break;
                case "PC":
                    character = new CharacterCard(CharacterCard.CharacterEnum.PEACOCK);
                    break;
                case "PL":
                    character = new CharacterCard(CharacterCard.CharacterEnum.PLUM);
                    break;
                case "SC":
                    character = new CharacterCard(CharacterCard.CharacterEnum.SCARLETT);
                    break;
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
                case "1":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.CANDLESTICK);
                    break;
                case "2":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.LEADPIPE);
                    break;
                case "3":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.DAGGER);
                    break;
                case "4":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.REVOLVER);
                    break;
                case "5":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.ROPE);
                    break;
                case "6":
                    weapon = new WeaponCard(WeaponCard.WeaponEnum.SPANNER);
                    break;
            }
        } while (weapon == null);

        return weapon;
    }

    /**
     *  Prints all the weapons options for ease of suggestion making.
     * @param allWeapons The weapons available
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
     *  Prints all the potential characters for ease of suggestion making.
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
