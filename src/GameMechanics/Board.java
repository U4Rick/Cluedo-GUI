package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.ArrayList;
import java.util.Map;

public class Board {

    Tile[][] board;
    private final Map<CharacterCard.CharacterEnum, Position> startingTiles;
    private final Map<RoomCard.RoomEnum, ArrayList<Position>> entrances;

    public Board() {
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

    /**
     * Get the tile from the board given a Position (X, Y coordinates)
     *
     * @param position Position to get the tile of.
     * @return Tile at passed position.
     */
    public Tile getTileAt(Position position) {
        return board[position.getY()][position.getX()];
    }

    /**
     * Getter for startingTiles.
     *
     * @return startingTiles.
     */
    public Map<CharacterCard.CharacterEnum, Position> getStartingTiles() {
        return startingTiles;
    }

    /**
     * Getter for entrances.
     *
     * @return entrances.
     */
    public Map<RoomCard.RoomEnum, ArrayList<Position>> getEntrances() {
        return entrances;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < board.length + 1; row++) {

            if (row < board.length) { builder.append(row); }
            else { builder.append("   "); }
            if (row < 10) { builder.append(" "); }

            for (int col = 0; col < board[0].length; col++)  {
                if (row == board.length) {
                    builder.append(" ").append(col);
                    if (col < 10) { builder.append(" "); }
                }
                else {
                    builder.append("|");
                    builder.append(board[row][col].toString());
                }

            }
            if (row != board.length) { builder.append("|").append("\n"); }
        }
        return builder.toString();
    }
}