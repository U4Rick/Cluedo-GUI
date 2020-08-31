package Tests;

import Cards.CharacterCard;
import GameMechanics.Action.Move;
import GameMechanics.Board;
import GameMechanics.Player;
import Tiles.Position;
import Tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Cards.CharacterCard.CharacterEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    Move move;
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        CharacterCard characterCard = new CharacterCard(SCARLETT);
        Tile tile = board.getTileAt(board.getStartingTiles().get(characterCard.getCharacter()));
        Player player = new Player(characterCard, tile, board, "test");

        move = new Move(player, board, 10);
    }

    @Test
    void validMove() {
        move.move(7, 20);
        assertEquals((board.getTileAt(new Position(7, 20)).toString()), "SC");
    }

    @Test
    void inValidMove() {
        move.move(7, 10);
        assertNotEquals((board.getTileAt(new Position(7, 10)).toString()), "SC");
    }

    @Test
    void isValidMovement_ValidMovement() {
        assertTrue(move.isValidMovement(7, 24, 7, 20));
    }

    @Test
    void isValidMovement_OutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> move.isValidMovement(7, 24, 7, 28));
    }

    @Test
    void isValidMovement_OutOfRange() {
        assertFalse(move.isValidMovement(7, 24, 7, 13));
    }

    @Test
    void isValidMovement_InaccessibleTile() {
        assertFalse(move.isValidMovement(7, 24, 6, 24));
    }
}