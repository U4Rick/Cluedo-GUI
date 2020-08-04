package GameMechanics;

import Tiles.AccessibleTile;
import Tiles.HallwayTile;
import Tiles.RoomTile;
import Tiles.Tile;

public class Board {

    Tile[][] board;
    public Board() {
        //board
        String boardText =
                "|~~|~~|~~|~~|~~|~~|~~|~~|~~|WH|~~|~~|~~|~~|GR|~~|~~|~~|~~|~~|~~|~~|~~|~~|\n" +
                "|  |  |  |  |  |  |~~|__|__|__|  |  |  |  |__|__|__|~~|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |~~|\n" +
                "|~~|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|PC|\n" +
                "|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|~~|\n" +
                "|~~|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |  |__|__|~~|~~|~~|~~|~~|__|__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |  |__|__|~~|~~|~~|~~|~~|__|__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |DR|__|__|~~|~~|~~|~~|~~|__|__|__|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |  |__|__|~~|~~|~~|~~|~~|__|__|__|__|__|__|__|__|~~|\n" +
                "|  |  |  |  |  |  |  |  |__|__|~~|~~|~~|~~|~~|__|__|__|  |  |LB|  |  |~~|\n" +
                "|  |  |  |  |  |  |DR|  |__|__|~~|~~|~~|~~|~~|__|__|  |  |  |  |  |  |  |\n" +
                "|~~|__|__|__|__|__|__|__|__|__|~~|~~|~~|~~|~~|__|__|LB|  |  |  |  |  |  |\n" +
                "|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                "|~~|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |~~|\n" +
                "|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|PL|\n" +
                "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|~~|\n" +
                "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                "|  |  |  |  |  |  |~~|SC|~~|  |  |  |  |  |  |~~|__|~~|  |  |  |  |  |  |";


        board = new BoardParser().parseBoard(boardText);
    }

    public void delete() {
    }

    /**
     * Tiles.Tile[][] tiles;
     * Creates list.
     */


    public void draw() {

    }

    public Tile getTileAt(int col, int row) { return board[row][col]; }

    public boolean setTileAt(int col, int row, Player p) {
        Tile temp = board[row][col];
        if (temp instanceof AccessibleTile) {
            if (temp instanceof HallwayTile) {
                ((HallwayTile) temp).setPlayerOnThisTile(p);
                board[row][col] = temp;
                return true;
            }
            else if (temp instanceof RoomTile && ((RoomTile) temp).isEntrance()) {
                //TODO: set up room entry stuff here,
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; row++)  {
                builder.append("|");
                builder.append(board[row][col].toString());
            }
            builder.append("|").append("\n");
        }
        return builder.toString();
    }
}