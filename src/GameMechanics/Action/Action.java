package GameMechanics.Action;

import GameMechanics.Board;
import GameMechanics.Player;
import Tiles.Position;

/**
 *  Super class to some player actions.
 *  Contains helper methods that multiple actions utilise.
 */
public class Action {


    /**
     *  Pause the console output for 2 seconds.
     */
    protected void sleep() {
        try { Thread.sleep(2000); } catch (Exception e) { System.out.println(e.toString()); }
    }

    /**
     * Teleports a player to a room if they're not already there.
     * @param player     Player to move
     * @param position   Position to move to
     * @param board      Board to move upon
     */
    protected void playerTeleport(Player player, Position position, Board board) {
        System.out.print("\n");
        if (player.getTile() != board.getTileAt(position)) {
            player.getTile().setPlayerOnThisTile(null);
            player.setTile(board.getTileAt(position));
            System.out.println(player.toString() + " moved to suggested room.");
        }
        else {
            System.out.println(player.toString() + " is already in the room.");
        }
    }
}
