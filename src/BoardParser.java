import Tiles.Tile;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BoardParser {

    Tile[][] board;

    public BoardParser() {
        board = new Tile[24][25];
    }

    private void parseBoard(String input) {
        Scanner scan = new Scanner(input);
        for (int row = 0; row < board.length; row++) {
            if (!scan.next().matches("\\|")) { throw new InputMismatchException("Should be a | here"); }
            for (int col = 0; col < board[row].length; col++) {
                if (!scan.hasNext()) { throw new NoSuchElementException("Input wrong size"); }
                String token = scan.next();
                if (token.matches("~")) {
                    //inaccessible tile
                    scan.next();
                }
                else if (token.matches("_")) {
                    //hallway tile
                    scan.next();
                }
                else if (token.matches("[A-Z]")) {
                    //room entry tile
                    token = token.concat(scan.next());

                }
                else if (token.matches("\\|")) {
                    //room tile
                    continue;
                }
                scan.next();
            }
        }
    }


    private void parseInitials(String token) {

    }

    private void parseRoom(String token) {
        //TODO: some sort of recursive room tile search function?
    }

    private void parseCharacter(String token) {

    }
}
