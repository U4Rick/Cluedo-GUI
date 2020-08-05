package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.ArrayList;
import java.util.Map;

public class Board {

    Tile[][] board;
    public Map<CharacterCard.characters, Position> startingTiles;
    public Map<RoomCard.rooms, ArrayList<Position>> entrances;

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

        BoardParser b = new BoardParser();
        board = b.parseBoard(boardText);
        startingTiles = b.startingTiles;
        entrances = b.entrances;
    }

    public void delete() {
    }

    /**
     * Tiles.Tile[][] tiles;
     * Creates list.
     */


    public void draw() {

    }

    public Tile getTileAt(Position p) { return board[p.getY()][p.getX()]; }

    public boolean setTileAt(Position pos, Player p) {
        Tile temp = board[pos.getY()][pos.getX()];
        if (temp instanceof AccessibleTile) {
            if (temp instanceof HallwayTile) {
                ((HallwayTile) temp).setPlayerOnThisTile(p);
                return true;
            }
            else if (temp instanceof EntranceTile) {
                //TODO: set up room entry stuff here,
            }
        }
        return false;
    }

    public Map<CharacterCard.characters, Position> getStartingTiles() { return startingTiles; }

    public Map<RoomCard.rooms, ArrayList<Position>> getEntrances() { return entrances; }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++)  {
                builder.append("|");
                builder.append(board[row][col].toString());
            }
            builder.append("|").append("\n");
        }
        return builder.toString();
    }
}