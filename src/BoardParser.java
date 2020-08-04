import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Parses an ascii board to create a functional game board for Cluedo.
 */
public class BoardParser {

    Tile[][] board;

    public BoardParser() {
        board = new Tile[24][25];
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

        for (int row = 0; row < board.length; row++) {
            if (!scan.next().matches("\\|")) { throw new InputMismatchException("Should be a | here"); }

            for (int col = 0; col < board[row].length; col++) {
                if (!scan.hasNext()) { throw new NoSuchElementException("Input wrong size"); }

                String token = scan.next();
                if (token.matches("~")) {
                    //inaccessible tile
                    board[row][col] = new InaccessibleTile(new Position(col, row));
                    scan.next();
                }
                else if (token.matches("_")) {
                    //hallway tile
                    board[row][col] = new HallwayTile(new Position(col, row));
                    scan.next();
                }
                else if (token.matches("[A-Z]")) {
                    //room entry tile or player start tile
                    token = token.concat(scan.next());
                    board[row][col] = parseInitials(token, col, row);
                }
                else if (token.matches("\\|")) {
                    //room tile
                    board[row][col] = new RoomTile(new Position(col, row));
                    continue; //don't need to skip the | character if we already hit it.
                }
                scan.next();
            }
        }

        //TODO: need to sort the blank room tiles into their specific rooms here?
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
            return new RoomTile(RoomCard.rooms.KITCHEN, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("BR")) {
            //ballroom
            return new RoomTile(RoomCard.rooms.BALLROOM, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("CT")) {
            //conservatory
            return new RoomTile(RoomCard.rooms.CONSERVATORY, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("BL")) {
            //billiard room
            return new RoomTile(RoomCard.rooms.BILLIARDROOM, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("LB")) {
            //library
            return new RoomTile(RoomCard.rooms.LIBRARY, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("ST")) {
            //study
            return new RoomTile(RoomCard.rooms.STUDY, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("HL")) {
            //hall
            return new RoomTile(RoomCard.rooms.HALL, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("LG")) {
            //lounge
            return new RoomTile(RoomCard.rooms.LOUNGE, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("DR")) {
            //dining room
            return new RoomTile(RoomCard.rooms.DININGROOM, new Position(x, y));
        }
        else if (token.equalsIgnoreCase("MU")) {
            //Mustard
            return new HallwayTile(new CharacterCard(CharacterCard.characters.MUSTARD), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("WH")) {
            //white
            return new HallwayTile(new CharacterCard(CharacterCard.characters.WHITE), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("GR")) {
            //green
            return new HallwayTile(new CharacterCard(CharacterCard.characters.GREEN), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("PC")) {
            //peacock
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PEACOCK), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("PL")) {
            //plum
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PLUM), new Position(x, y));
        }
        else if (token.equalsIgnoreCase("SC")) {
            //scarlett
            return new HallwayTile(new CharacterCard(CharacterCard.characters.SCARLETT), new Position(x, y));
        }
        else {
            throw new InputMismatchException("No viable character value found.");
        }
    }
}
