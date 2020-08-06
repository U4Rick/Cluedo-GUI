package Tiles;


import GameMechanics.Board;
import GameMechanics.BoardParser;
import GameMechanics.Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tile {

    public Position position;
    private Board board;
    private Set<String> characters = new HashSet<>(Arrays.asList("MU", "WH", "GR", "PC", "PL", "SC"));


    public Tile(Position position) {
        this.position = position;
    }


    public void draw() {
    }

    //todo implement
    public boolean hasPlayer() {
        return false;
    }

    public Position getPosition() {
        return this.position;
    }
}