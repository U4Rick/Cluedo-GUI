package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.*;

/**
 * Parses an ascii board to create a functional game board for Cluedo.
 */
public class BoardParser {

    public Tile[][] board;
    public Map<CharacterCard.CharacterEnum, Position> startingTiles;
    public Map<RoomCard.RoomEnum, ArrayList<Position>> entrances;

    /**
     * Initialise the BoardParser object.
     * Create an empty 2D array, and initialise the maps.
     */
    public BoardParser() {
        board = new Tile[25][24];
        startingTiles = new HashMap<>();
        entrances = new HashMap<>();
    }

    /**
     * Takes a board string and parses it into an actual game board.
     * @param input     The board to parse from
     * @return  Returns a 2D array of Tile objects, the board
     * @throws InputMismatchException   Input isn't as should be
     * @throws NoSuchElementException   No element where should be some.
     */
    public Tile[][] parseBoard(String input) throws InputMismatchException, NoSuchElementException{
        Scanner scan = new Scanner(input);
        scan.useDelimiter("\\|");
        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[row].length; col++) {
                if (!scan.hasNext()) { throw new NoSuchElementException("Input wrong size"); }

                String token = scan.next();
                if (token.matches("~~")) {
                    // Inaccessible tile
                    board[row][col] = new InaccessibleTile(new Position(col, row));
                }
                else if (token.matches("__")) {
                    // Hallway tile
                    board[row][col] = new HallwayTile(new Position(col, row));
                }
                else if (token.matches("[A-Z]{2}")) {
                    // Room entry/player start tile
                    board[row][col] = parseInitials(token, col, row);
                }
                else if (token.matches("\\s\\s")) {
                    // Room tile
                    board[row][col] = new RoomTile(new Position(col, row));
                }
                else {
                    System.out.println("no matching regex " + col + " " + row + " " + token);
                }
            }
            if (row != 24) {
                scan.next(); // Dispose of the \n char
            }
        }
        return board;
    }


    /**
     * Takes alphabetic content from the board and parses it into either a starting hallway tile or
     * room entrance.
     * @param token     Content to parse from
     * @param x     X position of the tile
     * @param y     Y position of the tile
     * @return      Returns a tile of specific type
     * @throws InputMismatchException   If token is not of pre-known sequence, input is incorrect.
     */
    private Tile parseInitials(String token, int x, int y) throws InputMismatchException {
        Position position = new Position(x, y);
        switch (token) {
            case "KT" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.KITCHEN)) {
                    entrances.put(RoomCard.RoomEnum.KITCHEN, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.KITCHEN).add(position);
                return new EntranceTile(RoomCard.RoomEnum.KITCHEN, position);
            }
            case "BR" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.BALLROOM)) {
                    entrances.put(RoomCard.RoomEnum.BALLROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.BALLROOM).add(position);
                return new EntranceTile(RoomCard.RoomEnum.BALLROOM, position);
            }
            case "CT" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.CONSERVATORY)) {
                    entrances.put(RoomCard.RoomEnum.CONSERVATORY, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.CONSERVATORY).add(position);
                return new EntranceTile(RoomCard.RoomEnum.CONSERVATORY, position);
            }
            case "BL" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.BILLIARDROOM)) {
                    entrances.put(RoomCard.RoomEnum.BILLIARDROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.BILLIARDROOM).add(position);
                return new EntranceTile(RoomCard.RoomEnum.BILLIARDROOM, position);
            }
            case "LB" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.LIBRARY)) {
                    entrances.put(RoomCard.RoomEnum.LIBRARY, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.LIBRARY).add(position);
                return new EntranceTile(RoomCard.RoomEnum.LIBRARY, position);
            }
            case "ST" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.STUDY)) {
                    entrances.put(RoomCard.RoomEnum.STUDY, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.STUDY).add(position);
                return new EntranceTile(RoomCard.RoomEnum.STUDY, position);
            }
            case "HL" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.HALL)) {
                    entrances.put(RoomCard.RoomEnum.HALL, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.HALL).add(position);
                return new EntranceTile(RoomCard.RoomEnum.HALL, position);
            }
            case "LG" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.LOUNGE)) {
                    entrances.put(RoomCard.RoomEnum.LOUNGE, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.LOUNGE).add(position);
                return new EntranceTile(RoomCard.RoomEnum.LOUNGE, position);
            }
            case "DR" -> {
                if (!entrances.containsKey(RoomCard.RoomEnum.DININGROOM)) {
                    entrances.put(RoomCard.RoomEnum.DININGROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.RoomEnum.DININGROOM).add(position);
                return new EntranceTile(RoomCard.RoomEnum.DININGROOM, position);
            }
            case "MU" -> {
                startingTiles.put(CharacterCard.CharacterEnum.MUSTARD, position);
                return new HallwayTile(position);
            }
            case "WH" -> {
                startingTiles.put(CharacterCard.CharacterEnum.WHITE, position);
                return new HallwayTile(position);
            }
            case "GR" -> {
                startingTiles.put(CharacterCard.CharacterEnum.GREEN, position);
                return new HallwayTile(position);
            }
            case "PC" -> {
                startingTiles.put(CharacterCard.CharacterEnum.PEACOCK, position);
                return new HallwayTile(position);
            }
            case "PL" -> {
                startingTiles.put(CharacterCard.CharacterEnum.PLUM, position);
                return new HallwayTile(position);
            }
            case "SC" -> {
                startingTiles.put(CharacterCard.CharacterEnum.SCARLETT, position);
                return new HallwayTile(position);
            }
            default -> throw new InputMismatchException("No viable character value found.");
        }
    }
}
