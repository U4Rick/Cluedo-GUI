package GameMechanics.Action;

import GameMechanics.Board;
import GameMechanics.Node;
import GameMechanics.Player;
import Tiles.*;

import java.util.*;

/**
 * Move players around the board and check the validity of those moves.
 */
public class Move {

    private boolean hasMadeValidMove = false;

    private final Player currentPlayer;
    private final Board board;
    private int movementRange;

    /**
     * Initialise a new move instance.
     *  @param currentPlayer Player that's moving
     * @param board         Board to move around on
     * @param currentRoll
     */
    public Move(Player currentPlayer, Board board, int currentRoll) {
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.movementRange = currentRoll;
    }

    /**
     * Handles user input and console output for movement.
     * Calls necessary methods to control movement.
     */
    /*public void playerMovement() {
        //roll dice
        *//*movementRange = rollDice();*//*
        //System.out.println("You rolled a " + movementRange);

        //ask for tile to move to
        hasMadeValidMove = false;
        while (!hasMadeValidMove) {

            System.out.println("Your position \nRow: " + currentPlayer.getTile().position.getY()
                    + "\nCol: " + currentPlayer.getTile().position.getX());
            System.out.println("\nEnter column to move to:");

            int moveCol = -1;
            int moveRow = -1;
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                moveCol = scan.nextInt();
                System.out.println("Enter row to move to:");    //repeats until valid inputs
                if (scan.hasNextInt()) {
                    moveRow = scan.nextInt();
                }
            }

            if (moveRow < 25 && moveRow >= 0 && moveCol < 24 && moveCol >= 0) {
                move(moveCol, moveRow); //check if requested tile is within board bounds
            } else {
                System.out.println("Invalid row/column, try again.");
            }
        }
    }*/

    public boolean playerMovement(Position cellToMoveTo) {
        if (cellToMoveTo.getY() < 25 && cellToMoveTo.getY() >= 0 && cellToMoveTo.getX() < 24 && cellToMoveTo.getX() >= 0) {
            move(cellToMoveTo.getX(), cellToMoveTo.getY()); //check if requested tile is within board bounds
            return hasMadeValidMove;

        } else {
            return false;
        }
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
    public Boolean isValidMovement(int startX, int startY, int endX, int endY) {

        Position startPos = new Position(startX, startY);
        Position endPos = new Position(endX, endY);

        //if endTile == inaccessible
        if (board.getTileAt(endPos) instanceof InaccessibleTile) {
            System.out.println("Inaccessible Tile");
            return false;
        }

        Tile endTile = board.getTileAt(endPos);
        if ((endTile.getPlayerOnThisTile() != null) && !(board.getTileAt(endPos) instanceof EntranceTile)) {
            System.out.println("Tile already has player on it");
            return false;//else if endPos already has player && endPos is not entranceTile
        }

        if (Math.abs((startX - endX) + (startY - endY)) > this.movementRange) {
            System.out.println("You can not move that far!");
            return false;
        }

        if (!pathfinding(board, startPos, endPos)) {
            System.out.println("No valid path to that tile, try again.");
            return false;
        }

        hasMadeValidMove = true;
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

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (!(tiles[y][x] instanceof InaccessibleTile)) {
                    Node node;
                    if (tiles[y][x].getPosition().equals(start)) {
                        node = new Node(tiles[y][x], 0);
                    } else {
                        node = new Node(tiles[y][x], 999);
                    }
                    fringe.offer(node);
                    pathing.put(tiles[y][x].getPosition(), node);
                }
            }
        }

        while (!fringe.isEmpty()) {

            if (fringe.peek().getDistance() >= 12) {
                fringe.removeIf(node -> node.getDistance() > 12);
            }

            if (fringe.peek() !=  null) {
                Node node = fringe.poll();
                visited.add(node);

                if (node.getTile().getPosition().equals(goal)) {
                    ArrayList<Node> temp = new ArrayList<>();
                    for (Node node1 : visited) {
                        if (node1.getDistance() >= 999) {
                            temp.add(node1);
                        }
                    }
                    visited.removeAll(temp);    //todo all of this chunk is for debugging/tracking the path. Might use later?

                    return node.getDistance() <= movementRange;
                }

                Node[] neigh = getNeighbours(node, pathing);

                for (int i = 0; i < 4; i++) {
                    Node child = neigh[i];
                    if (child == null || visited.contains(child)) continue;

                    child.setDistance(node.getDistance() + 1);
                    fringe.offer(child);
                }
            }
        }
        return false;
    }

    /**
     * Gets the neighbouring nodes of the current node
     * @param node  the  current node
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
        int playerX = currentPlayer.getTile().position.getX();    //current X
        int playerY = currentPlayer.getTile().position.getY();    //current Y
        if (isValidMovement(playerX, playerY, x, y) /*&& (pathfinding(board, startPos, endPos))*/) {
            currentPlayer.setTile(endTile);
            endTile.setPlayerOnThisTile(currentPlayer);
            startTile.setPlayerOnThisTile(null);
        }
    }

    /**
     * Rolls two dice and returns the sum of them.
     *
     * @return Sum of two dice.
     */
    //replaced in game
    /*private int rollDice() {
        int dice1 = (int) (Math.random() * 6 + 1);
        int dice2 = (int) (Math.random() * 6 + 1);
        return dice1 + dice2;
    }*/

}
