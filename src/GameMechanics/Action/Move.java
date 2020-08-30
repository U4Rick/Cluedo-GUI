package GameMechanics.Action;

import Cards.RoomCard;
import GameMechanics.Board;
import GameMechanics.Node;
import GameMechanics.Player;
import Tiles.*;

import java.util.*;

/**
 * Move players around the board and check the validity of those moves.
 */
public class Move {

    private final Player currentPlayer;
    private final Board board;
    private final int movementRange;
    private String movementOutcome = "";

    /**
     * Initialise a new move instance.
     *
     * @param currentPlayer Player that's moving
     * @param board         Board to move around on
     * @param currentRoll   Max distance able to move.
     */
    public Move(Player currentPlayer, Board board, int currentRoll) {
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.movementRange = currentRoll;
    }

    public String playerMovement(Position cellToMoveTo) {
        if (cellToMoveTo.getY() < 25 && cellToMoveTo.getY() >= 0 && cellToMoveTo.getX() < 24 && cellToMoveTo.getX() >= 0) {
            move(cellToMoveTo.getX(), cellToMoveTo.getY()); //check if requested tile is within board bounds
        }
        return movementOutcome;
    }

    /**
     * Checks if the requested move of the player is a valid movement.
     * Checks for out of board, out of move range, tile types.
     *
     * @param startX Starting x position
     * @param startY Starting y position
     * @param endX   Ending x position
     * @param endY   Ending y position
     * @return true if valid move, false if invalid move.
     */
    public boolean isValidMovement(int startX, int startY, int endX, int endY) {

        Position startPos = new Position(startX, startY);
        Position endPos = new Position(endX, endY);

        //if endTile == inaccessible
        if (board.getTileAt(endPos) instanceof InaccessibleTile) {
            movementOutcome  = ("Inaccessible Tile");
            return false;
        }
        Tile endTile = board.getTileAt(endPos);
        if ((endTile.getPlayerOnThisTile() != null) && !(board.getTileAt(endPos) instanceof EntranceTile)) {
            movementOutcome = ("Tile already has player on it");
            return false;//else if endPos already has player && endPos is not entranceTile
        }


        if (Math.abs((startX - endX) + (startY - endY)) > this.movementRange) {
            movementOutcome = ("You can not move that far!");
            return false;
        }

        if (!pathfinding(board, startPos, endPos)) {
            movementOutcome = ("No valid path to that tile, try again.");
            return false;
        }

        return true;
    }

    /**
     * Dijkstras pathfinding algorithm to prevent players clipping through rooms on high rolls
     *
     * @param board the board
     * @param start the position the movement starts from
     * @param goal  the position movement ends at
     * @return true if a valid path can be made
     */
    public boolean pathfinding(Board board, Position start, Position goal) {
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> fringe = new PriorityQueue<>();
        HashMap<Position, Node> pathing = new HashMap<>();
        Tile[][] tiles = board.board;

        for (Tile[] tile : tiles) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (!(tile[x] instanceof InaccessibleTile) || (tile[x] instanceof RoomTile)) {       //collects all accessible tiles of board
                    Node node;
                    if (tile[x].getPosition().equals(start)) {
                        node = new Node(tile[x], 0);
                    } else {
                        node = new Node(tile[x], 999);  //initialise distances from root
                    }
                    fringe.offer(node);
                    pathing.put(tile[x].getPosition(), node);   //populate fringe and node map
                }
            }
        }

        while (!fringe.isEmpty()) { //until no valid paths

            if (fringe.peek().getDistance() >= 12) {
                fringe.removeIf(node -> node.getDistance() > 12);   //bad logic work around
            }

            if (fringe.peek() != null) {
                Node node = fringe.poll();
                visited.add(node);  //visit nearest fringe tile

                if (node.getTile().getPosition().equals(goal)) {
                    return node.getDistance() <= movementRange; //check if reached end correctly
                }

                Node[] neigh = getNeighbours(node, pathing);

                for (int i = 0; i < 4; i++) {
                    Node child = neigh[i];
                    if (child == null || visited.contains(child)) continue;

                    child.setDistance(node.getDistance() + 1);
                    fringe.offer(child);    //adds neighbouring tiles to fringe if possible path
                }
            }
        }
        return false;
    }

    /**
     * Gets the neighbouring nodes of the current node
     *
     * @param node    the  current node
     * @param pathing map of nodes and their positions on the board
     * @return the neighbouring nodes
     */
    private static Node[] getNeighbours(Node node, Map<Position, Node> pathing) {

        Position position = node.getTile().getPosition();

        Node[] neighbours = new Node[4];

        //RIGHT
        if (pathing.containsKey(position.add(1, 0))) neighbours[1] = pathing.get(position.add(1, 0));

        //DOWN
        if (pathing.containsKey(position.add(0, 1))) neighbours[2] = pathing.get(position.add(0, 1));

        //LEFT
        if (pathing.containsKey(position.add(-1, 0))) neighbours[3] = pathing.get(position.add(-1, 0));

        //UP
        if (pathing.containsKey(position.add(0, -1))) neighbours[0] = pathing.get(position.add(0, -1));

        return neighbours;
    }

    /**
     * Get the move, and move the player if the move is deemed valid.
     *
     * @param x x coordinate to move to
     * @param y y coordinate to move to
     */
    public void move(int x, int y) {

        Tile startTile = currentPlayer.getTile();   //tile before moving
        Position endPos = new Position(x, y); // position to move to

        Tile endTile = board.getTileAt(endPos);    //tile to move to
        Map<RoomCard.RoomEnum, ArrayList<Position>> roomEntrances = board.getEntrances();

        if (startTile instanceof RoomTile && endTile instanceof EntranceTile) {
            RoomCard.RoomEnum playerRoom = ((RoomTile) startTile).getRoom();
            ArrayList<Position> currentRoomEntrances = roomEntrances.get(playerRoom);
            if (currentRoomEntrances.contains(endPos)) {
                currentPlayer.setTile(endTile);
                endTile.setPlayerOnThisTile(currentPlayer);
                startTile.setPlayerOnThisTile(null);
            }
        } else {
            int playerX = currentPlayer.getTile().position.getX();    //current X
            int playerY = currentPlayer.getTile().position.getY();    //current Y
            if (isValidMovement(playerX, playerY, x, y)) {
                currentPlayer.setTile(endTile);
                endTile.setPlayerOnThisTile(currentPlayer);
                startTile.setPlayerOnThisTile(null);
            }
        }
    }
}
