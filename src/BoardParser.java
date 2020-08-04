import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BoardParser {

    Tile[][] board;

    public BoardParser() {
        board = new Tile[24][25];
    }

    public Tile[][] parseBoard(String input) {
        Scanner scan = new Scanner(input);

        for (int row = 0; row < board.length; row++) {
            if (!scan.next().matches("\\|")) { throw new InputMismatchException("Should be a | here"); }

            for (int col = 0; col < board[row].length; col++) {
                if (!scan.hasNext()) { throw new NoSuchElementException("Input wrong size"); }

                String token = scan.next();
                if (token.matches("~")) {
                    //inaccessible tile
                    board[row][col] = new InaccessibleTile();
                    scan.next();
                }
                else if (token.matches("_")) {
                    //hallway tile
                    board[row][col] = new HallwayTile();
                    scan.next();
                }
                else if (token.matches("[A-Z]")) {
                    //room entry tile or player start tile
                    token = token.concat(scan.next());
                    board[row][col] = parseInitials(token);
                }
                else if (token.matches("\\|")) {
                    //room tile
                    board[row][col] = new RoomTile();
                    continue; //don't need to skip the | character if we already hit it.
                }
                scan.next();
            }
        }

        //TODO: need to sort the blank room tiles into their specific rooms here?
        return board;
    }

//TODO: pregenerate the character cards??? or leave as is? idk maybe in game before board creation
    private Tile parseInitials(String token) {
        if (token.equalsIgnoreCase("KT")) {
            //kitchen
            return new RoomTile(RoomCard.rooms.KITCHEN);
        }
        else if (token.equalsIgnoreCase("BR")) {
            //ballroom
            return new RoomTile(RoomCard.rooms.BALLROOM);
        }
        else if (token.equalsIgnoreCase("CT")) {
            //conservatory
            return new RoomTile(RoomCard.rooms.CONSERVATORY);
        }
        else if (token.equalsIgnoreCase("BL")) {
            //billard room
            return new RoomTile(RoomCard.rooms.BILLARDROOM);
        }
        else if (token.equalsIgnoreCase("LB")) {
            //library
            return new RoomTile(RoomCard.rooms.LIBRARY);
        }
        else if (token.equalsIgnoreCase("ST")) {
            //study
            return new RoomTile(RoomCard.rooms.STUDY);
        }
        else if (token.equalsIgnoreCase("HL")) {
            //hall
            return new RoomTile(RoomCard.rooms.HALL);
        }
        else if (token.equalsIgnoreCase("LG")) {
            //lounge
            return new RoomTile(RoomCard.rooms.LOUNGE);
        }
        else if (token.equalsIgnoreCase("DR")) {
            //dining room
            return new RoomTile(RoomCard.rooms.DININGROOM);
        }
        else if (token.equalsIgnoreCase("MU")) {
            //Mustard
            return new HallwayTile(new CharacterCard(CharacterCard.characters.MUSTARD));
        }
        else if (token.equalsIgnoreCase("WH")) {
            //white
            return new HallwayTile(new CharacterCard(CharacterCard.characters.WHITE));
        }
        else if (token.equalsIgnoreCase("GR")) {
            //green
            return new HallwayTile(new CharacterCard(CharacterCard.characters.GREEN));
        }
        else if (token.equalsIgnoreCase("PC")) {
            //peacock
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PEACOCK));
        }
        else if (token.equalsIgnoreCase("PL")) {
            //plum
            return new HallwayTile(new CharacterCard(CharacterCard.characters.PLUM));
        }
        else if (token.equalsIgnoreCase("SC")) {
            //scarlett
            return new HallwayTile(new CharacterCard(CharacterCard.characters.SCARLETT));
        }
        else {
            throw new InputMismatchException("No viable character value found.");
        }
    }

    private void parseRoom(String token) {
        //TODO: some sort of recursive room tile search function?
    }

    private void parseCharacter(String token) {

    }
}
