package GameMechanics;

import Cards.CharacterCard;
import Cards.RoomCard;
import Tiles.*;

import java.util.ArrayList;
import java.util.Map;

/**
 *  The board which the game is played on.
 *  Contains the 2D array of tiles that make up the board,
 *  and a couple collections of positions relating to starting tiles
 *  for players and the entrances to rooms.
 */
public class Board {

    public Tile[][] board;
    private final Map<CharacterCard.CharacterEnum, Position> startingTiles;
    private final Map<RoomCard.RoomEnum, ArrayList<Position>> entrances;
    private final Map<RoomCard.RoomEnum, ArrayList<Position>> roomTiles;

    /**
     *  Initialise the board.
     *  Runs the boardParser to process the ascii board into the 2D array.
     */
    public Board() {
        String boardText =
                "|~~|~~|~~|~~|~~|~~|~~|~~|~~|WH|~~|~~|~~|~~|GR|~~|~~|~~|~~|~~|~~|~~|~~|~~|\n" +
                "|kt|kt|kt|kt|kt|kt|~~|__|__|__|br|br|br|br|__|__|__|~~|ct|ct|ct|ct|ct|ct|\n" +
                "|kt|kt|kt|kt|kt|kt|__|__|br|br|br|br|br|br|br|br|__|__|ct|ct|ct|ct|ct|ct|\n" +
                "|kt|kt|kt|kt|kt|kt|__|__|br|br|br|br|br|br|br|br|__|__|ct|ct|ct|ct|ct|ct|\n" +
                "|kt|kt|kt|kt|kt|kt|__|__|br|br|br|br|br|br|br|br|__|__|CT|ct|ct|ct|ct|ct|\n" +
                "|kt|kt|kt|kt|kt|kt|__|__|BR|br|br|br|br|br|br|BR|__|__|__|ct|ct|ct|ct|~~|\n" +
                "|~~|kt|kt|kt|KT|kt|__|__|br|br|br|br|br|br|br|br|__|__|__|__|__|__|__|PC|\n" +
                "|__|__|__|__|__|__|__|__|br|BR|br|br|br|br|BR|br|__|__|__|__|__|__|__|~~|\n" +
                "|~~|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                "|dr|dr|dr|dr|dr|__|__|__|__|__|__|__|__|__|__|__|__|__|BL|bl|bl|bl|bl|bl|\n" +
                "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|~~|~~|~~|~~|~~|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|~~|~~|~~|~~|~~|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                "|dr|dr|dr|dr|dr|dr|dr|DR|__|__|~~|~~|~~|~~|~~|__|__|__|bl|bl|bl|bl|BL|bl|\n" +
                "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|~~|~~|~~|~~|~~|__|__|__|__|__|__|__|__|~~|\n" +
                "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|~~|~~|~~|~~|~~|__|__|__|lb|lb|LB|lb|lb|~~|\n" +
                "|dr|dr|dr|dr|dr|dr|DR|dr|__|__|~~|~~|~~|~~|~~|__|__|lb|lb|lb|lb|lb|lb|lb|\n" +
                "|~~|__|__|__|__|__|__|__|__|__|~~|~~|~~|~~|~~|__|__|LB|lb|lb|lb|lb|lb|lb|\n" +
                "|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|lb|lb|lb|lb|lb|lb|lb|\n" +
                "|~~|__|__|__|__|__|__|__|__|hl|hl|HL|HL|hl|hl|__|__|__|lb|lb|lb|lb|lb|~~|\n" +
                "|lg|lg|lg|lg|lg|lg|LG|__|__|hl|hl|hl|hl|hl|hl|__|__|__|__|__|__|__|__|PL|\n" +
                "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|HL|__|__|__|__|__|__|__|__|~~|\n" +
                "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|ST|st|st|st|st|st|st|\n" +
                "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|st|st|st|st|st|st|st|\n" +
                "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|st|st|st|st|st|st|st|\n" +
                "|lg|lg|lg|lg|lg|lg|~~|SC|~~|hl|hl|hl|hl|hl|hl|~~|__|~~|st|st|st|st|st|st|";

        BoardParser b = new BoardParser();
        board = b.parseBoard(boardText);
        startingTiles = b.startingTiles;
        entrances = b.entrances;
        roomTiles = b.rooms;
    }

    /**
     * Get the tile from the board given a Position (X, Y coordinates).
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

    /**
     * Getter for roomTiles.
     *
     * @return roomTiles.
     */
    public Map<RoomCard.RoomEnum, ArrayList<Position>> getRoomTiles() {
        return roomTiles;
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