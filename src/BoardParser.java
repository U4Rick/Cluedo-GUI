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
                    Tile temp = parseInitials(token);
                    if (temp instanceof AccessibleTile) { board[row][col] = temp; }
                    else { throw new InputMismatchException("No valid letter sequence was found"); }
                }
                else if (token.matches("\\|")) {
                    //room tile
                    board[row][col] = new RoomTile();
                    continue; //don't need to skip the | character if we already hit it.
                }
                scan.next();
            }
        }
        return board;
    }


    private Tile parseInitials(String token) {
        return null;
    }

    private void parseRoom(String token) {
        //TODO: some sort of recursive room tile search function?
    }

    private void parseCharacter(String token) {

    }
}
