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
    public Map<CharacterCard.characters, Position> startingTiles;
    public Map<RoomCard.rooms, ArrayList<Position>> entrances;

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
        Position h = new Position(x, y);
        switch (token) {
            case "KT":
                // Kitchen
                if (!entrances.containsKey(RoomCard.rooms.KITCHEN)) {
                    entrances.put(RoomCard.rooms.KITCHEN, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.KITCHEN).add(h);
                return new EntranceTile(RoomCard.rooms.KITCHEN, h);

            case "BR":
                // Ballroom
                if (!entrances.containsKey(RoomCard.rooms.BALLROOM)) {
                    entrances.put(RoomCard.rooms.BALLROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.BALLROOM).add(h);
                return new EntranceTile(RoomCard.rooms.BALLROOM, h);

            case "CT":
                // Conservatory
                if (!entrances.containsKey(RoomCard.rooms.CONSERVATORY)) {
                    entrances.put(RoomCard.rooms.CONSERVATORY, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.CONSERVATORY).add(h);
                return new EntranceTile(RoomCard.rooms.CONSERVATORY, h);

           case "BL":
                // Billiard room
                if (!entrances.containsKey(RoomCard.rooms.BILLIARDROOM)) {
                    entrances.put(RoomCard.rooms.BILLIARDROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.BILLIARDROOM).add(h);
                return new EntranceTile(RoomCard.rooms.BILLIARDROOM, h);

            case "LB":
                // Library
                if (!entrances.containsKey(RoomCard.rooms.LIBRARY)) {
                    entrances.put(RoomCard.rooms.LIBRARY, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.LIBRARY).add(h);
                return new EntranceTile(RoomCard.rooms.LIBRARY, h);

            case "ST":
                // Study
                if (!entrances.containsKey(RoomCard.rooms.STUDY)) {
                    entrances.put(RoomCard.rooms.STUDY, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.STUDY).add(h);
                return new EntranceTile(RoomCard.rooms.STUDY, h);

            case "HL":
                // Hall
                if (!entrances.containsKey(RoomCard.rooms.HALL)) {
                    entrances.put(RoomCard.rooms.HALL, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.HALL).add(h);
                return new EntranceTile(RoomCard.rooms.HALL, h);

            case"LG":
                // Lounge
                if (!entrances.containsKey(RoomCard.rooms.LOUNGE)) {
                    entrances.put(RoomCard.rooms.LOUNGE, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.LOUNGE).add(h);
                return new EntranceTile(RoomCard.rooms.LOUNGE, h);

            case "DR":
                // Dining room
                if (!entrances.containsKey(RoomCard.rooms.DININGROOM)) {
                    entrances.put(RoomCard.rooms.DININGROOM, new ArrayList<>());
                }
                entrances.get(RoomCard.rooms.DININGROOM).add(h);
                return new EntranceTile(RoomCard.rooms.DININGROOM, h);

            case "MU":
                // Mustard
                startingTiles.put(CharacterCard.characters.MUSTARD, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.MUSTARD), h);

            case "WH":
                // White
                startingTiles.put(CharacterCard.characters.WHITE, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.WHITE), h);

            case "GR":
                // Green
                startingTiles.put(CharacterCard.characters.GREEN, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.GREEN), h);

            case "PC":
                // Peacock
                startingTiles.put(CharacterCard.characters.PEACOCK, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.PEACOCK), h);

            case "PL":
                // Plum
                startingTiles.put(CharacterCard.characters.PLUM, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.PLUM), h);

            case "SC":
                // Scarlett
                startingTiles.put(CharacterCard.characters.SCARLETT, h);
                return new HallwayTile(new CharacterCard(CharacterCard.characters.SCARLETT), h);

            default:
                throw new InputMismatchException("No viable character value found.");
        }




    }
}
