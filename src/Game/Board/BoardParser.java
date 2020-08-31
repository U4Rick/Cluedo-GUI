package Game.Board;

import Game.Cards.CharacterCard;
import Game.Cards.RoomCard;
import Game.Tiles.*;

import java.util.*;

import static Game.Cards.CharacterCard.CharacterEnum.*;
import static Game.Cards.RoomCard.RoomEnum.*;

/**
 * Parses an ascii board to create a functional game board for Cluedo.
 */
public class BoardParser {

    public Tile[][] board;
    public Map<CharacterCard.CharacterEnum, Position> startingTiles;
    public Map<RoomCard.RoomEnum, ArrayList<Position>> entrances;
    public Map<RoomCard.RoomEnum, ArrayList<Position>> rooms;

    /**
     * Initialise the BoardParser object.
     * Create an empty 2D array, and initialise the maps.
     */
    public BoardParser() {
        board = new Tile[25][24];
        startingTiles = new HashMap<>();
        entrances = new HashMap<>();
        rooms = new HashMap<>();
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
                else if (token.matches("[a-z]{2}")) {
                    // Room tile
                    board[row][col] = parseRoom(token, col, row);
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
     * Create a new room tile based off of the passed token and board position.
     *
     * @param token Identifying characters to parse.
     * @param x X position on board.
     * @param y Y position on board.
     * @return Tile
     */
    private Tile parseRoom(String token, int x, int y) {
        Position position = new Position(x, y);
        switch (token) {
            case "kt":
                if (!rooms.containsKey(KITCHEN)) {
                    rooms.put(KITCHEN, new ArrayList<>());
                }
                rooms.get(KITCHEN).add(position);
                return new RoomTile(KITCHEN, position);
            case "br":
                if (!rooms.containsKey(BALLROOM)) {
                    rooms.put(BALLROOM, new ArrayList<>());
                }
                rooms.get(BALLROOM).add(position);
                return new RoomTile(BALLROOM, position);
            case "ct":
                if (!rooms.containsKey(CONSERVATORY)) {
                    rooms.put(CONSERVATORY, new ArrayList<>());
                }
                rooms.get(CONSERVATORY).add(position);
                return new RoomTile(CONSERVATORY, position);
            case "bl":
                if (!rooms.containsKey(BILLIARDROOM)) {
                    rooms.put(BILLIARDROOM, new ArrayList<>());
                }
                rooms.get(BILLIARDROOM).add(position);
                return new RoomTile(BILLIARDROOM, position);
            case "lb":
                if (!rooms.containsKey(LIBRARY)) {
                    rooms.put(LIBRARY, new ArrayList<>());
                }
                rooms.get(LIBRARY).add(position);
                return new RoomTile(LIBRARY, position);
            case "st":
                if (!rooms.containsKey(STUDY)) {
                    rooms.put(STUDY, new ArrayList<>());
                }
                rooms.get(STUDY).add(position);
                return new RoomTile(STUDY, position);
            case "hl":
                if (!rooms.containsKey(HALL)) {
                    rooms.put(HALL, new ArrayList<>());
                }
                rooms.get(HALL).add(position);
                return new RoomTile(HALL, position);
            case "lg":
                if (!rooms.containsKey(LOUNGE)) {
                    rooms.put(LOUNGE, new ArrayList<>());
                }
                rooms.get(LOUNGE).add(position);
                return new RoomTile(LOUNGE, position);
            case "dr":
                if (!rooms.containsKey(DININGROOM)) {
                    rooms.put(DININGROOM, new ArrayList<>());
                }
                rooms.get(DININGROOM).add(position);
                return new RoomTile(DININGROOM, position);
        }
        return null;
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
            case "KT":
                if (!entrances.containsKey(KITCHEN)) {
                    entrances.put(KITCHEN, new ArrayList<>());
                }
                entrances.get(KITCHEN).add(position);
                return new EntranceTile(KITCHEN, position);
            case "BR":
                if (!entrances.containsKey(BALLROOM)) {
                    entrances.put(BALLROOM, new ArrayList<>());
                }
                entrances.get(BALLROOM).add(position);
                return new EntranceTile(BALLROOM, position);
            case "CT":
                if (!entrances.containsKey(CONSERVATORY)) {
                    entrances.put(CONSERVATORY, new ArrayList<>());
                }
                entrances.get(CONSERVATORY).add(position);
                return new EntranceTile(CONSERVATORY, position);
            case "BL":
                if (!entrances.containsKey(BILLIARDROOM)) {
                    entrances.put(BILLIARDROOM, new ArrayList<>());
                }
                entrances.get(BILLIARDROOM).add(position);
                return new EntranceTile(BILLIARDROOM, position);
            case "LB":
                if (!entrances.containsKey(LIBRARY)) {
                    entrances.put(LIBRARY, new ArrayList<>());
                }
                entrances.get(LIBRARY).add(position);
                return new EntranceTile(LIBRARY, position);
            case "ST":
                if (!entrances.containsKey(STUDY)) {
                    entrances.put(STUDY, new ArrayList<>());
                }
                entrances.get(STUDY).add(position);
                return new EntranceTile(STUDY, position);
            case "HL":
                if (!entrances.containsKey(HALL)) {
                    entrances.put(HALL, new ArrayList<>());
                }
                entrances.get(HALL).add(position);
                return new EntranceTile(HALL, position);
            case "LG":
                if (!entrances.containsKey(LOUNGE)) {
                    entrances.put(LOUNGE, new ArrayList<>());
                }
                entrances.get(LOUNGE).add(position);
                return new EntranceTile(LOUNGE, position);
            case "DR":
                if (!entrances.containsKey(DININGROOM)) {
                    entrances.put(DININGROOM, new ArrayList<>());
                }
                entrances.get(DININGROOM).add(position);
                return new EntranceTile(DININGROOM, position);
            case "MU":
                startingTiles.put(MUSTARD, position);
                return new HallwayTile(position);
            case "WH":
                startingTiles.put(WHITE, position);
                return new HallwayTile(position);
            case "GR":
                startingTiles.put(GREEN, position);
                return new HallwayTile(position);
            case "PC":
                startingTiles.put(PEACOCK, position);
                return new HallwayTile(position);
            case "PL":
                startingTiles.put(PLUM, position);
                return new HallwayTile(position);
            case "SC":
                startingTiles.put(SCARLETT, position);
                return new HallwayTile(position);
            default:
                throw new InputMismatchException("No viable character value found.");
        }
    }
}
