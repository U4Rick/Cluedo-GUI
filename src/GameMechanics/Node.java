package GameMechanics;

import Tiles.Position;
import Tiles.Tile;

import java.util.HashMap;
import java.util.Objects;

//TODO javadoc

public class Node implements Comparable<Node> {

    private Tile tile;
    private int distance;

    public Node(Tile tile, int distance) {
        this.tile = tile;
        this.distance = distance;
    }


    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }


    public int getDistance() {
        return distance;
    }

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

    public Node[] getNeighbours(Node node, HashMap<Position, Node> path) {
        Position pos = node.getTile().position;
        Node[] neigh = new Node[4];

        //UP
        if (path.containsKey(pos.add(0, -1))) neigh[0] = path.get(pos.add(0, -1));

        //RIGHT
        if (path.containsKey(pos.add(1, 0))) neigh[1] = path.get(pos.add(1, 0));

        //DOWN
        if (path.containsKey(pos.add(0, 1))) neigh[2] = path.get(pos.add(0, 1));

        //LEFT
        if (path.containsKey(pos.add(-1, 0))) neigh[3] = path.get(pos.add(-1, 0));

        return neigh;
    }
}

