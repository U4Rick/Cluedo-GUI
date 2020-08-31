package Game.Board;

import Game.Tiles.Tile;

import java.util.Objects;

/**
 * Node representation of a tile, used for Djikstra's Algorithm.
 */
public class Node implements Comparable<Node> {

    private Tile tile;
    private int distance;

    /**
     * Constructor for a Node.
     *
     * @param tile Tile of Node.
     * @param distance Distance to this Node.
     */
    public Node(Tile tile, int distance) {
        this.tile = tile;
        this.distance = distance;
    }

    /**
     * Getter for tile.
     *
     * @return tile.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Setter for tile.
     *
     * @param tile Tile to set to.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Getter for distance.
     *
     * @return distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Setter for distance.
     *
     * @param distance Int value to set distance to.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node node) {
        if (node.getDistance() == this.distance) return 0;
        return node.getDistance() < this.distance ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return distance == node.distance &&
                Objects.equals(tile, node.tile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile, distance);
    }

    @Override
    public String toString() {
        return "Node{" +
                "tile=" + tile +
                ", distanceToRoot=" + distance +
                '}';
    }
}

