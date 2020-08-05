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
    public Map<RoomCard.rooms, Position> entrances;

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
                    //inaccessible tile
                    board[row][col] = new InaccessibleTile(new Position(col, row));
                }
                else if (token.matches("__")) {
                    //hallway tile
                    board[row][col] = new HallwayTile(new Position(col, row));
                }
                else if (token.matches("[A-Z]{2}")) {
                    //room entry tile or player start tile
                    board[row][col] = parseInitials(token, col, row);
                }
                else if (token.matches("\\s\\s")) {
                    //room tile
                    board[row][col] = new RoomTile(new Position(col, row));
                }
                else {
                    System.out.println("no matching regex " + col + " " + row + " " + token);
                }
            }
            if (row != 24) {
                scan.next(); //dispose of the \n char
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
        if (token.equalsIgnoreCase("KT")) {
            //kitchen
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.KITCHEN, h);
            return new EntranceTile(RoomCard.rooms.KITCHEN, h);
        }
        else if (token.equalsIgnoreCase("BR")) {
            //ballroom
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.BALLROOM, h);
            return new EntranceTile(RoomCard.rooms.BALLROOM, h);
        }
        else if (token.equalsIgnoreCase("CT")) {
            //conservatory
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.CONSERVATORY, h);
            return new EntranceTile(RoomCard.rooms.CONSERVATORY, h);
        }
        else if (token.equalsIgnoreCase("BL")) {
            //billiard room
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.BILLIARDROOM, h);
            return new EntranceTile(RoomCard.rooms.BILLIARDROOM, h);
        }
        else if (token.equalsIgnoreCase("LB")) {
            //library
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.LIBRARY, h);
            return new EntranceTile(RoomCard.rooms.LIBRARY, h);
        }
        else if (token.equalsIgnoreCase("ST")) {
            //study
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.STUDY, h);
            return new EntranceTile(RoomCard.rooms.STUDY, h);
        }
        else if (token.equalsIgnoreCase("HL")) {
            //hall
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.HALL, h);
            return new EntranceTile(RoomCard.rooms.HALL, h);
        }
        else if (token.equalsIgnoreCase("LG")) {
            //lounge
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.LOUNGE, h);
            return new EntranceTile(RoomCard.rooms.LOUNGE, h);
        }
        else if (token.equalsIgnoreCase("DR")) {
            //dining room
            Position h = new Position(x, y);
            entrances.put(RoomCard.rooms.DININGROOM, h);
            return new EntranceTile(RoomCard.rooms.DININGROOM, h);
        }
        else if (token.equalsIgnoreCase("MU")) {
            //Mustard
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.MUSTARD), h);
        }
        else if (token.equalsIgnoreCase("WH")) {
            //white
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.WHITE), h);
        }
        else if (token.equalsIgnoreCase("GR")) {
            //green
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.GREEN), h);
        }
        else if (token.equalsIgnoreCase("PC")) {
            //peacock
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PEACOCK), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("PL")) {
            //plum
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PLUM), h);
        }
        else if (token.equalsIgnoreCase("SC")) {
            //scarlett
            Position h = new Position(x, y);
            startingTiles.put(CharacterCard.characters.MUSTARD, h);
            return new HallwayTile(new CharacterCard(CharacterCard.characters.SCARLETT), h);
        }
        else {
            throw new InputMismatchException("No viable character value found.");
        }
    }
}
