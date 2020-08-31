package Tests.ActionTests;

import Game.Cards.CharacterCard;
import Game.Actions.Move;
import Game.Board.Board;
import Game.Entities.Player;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Game.Cards.CharacterCard.CharacterEnum.*;
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
    void validMove_Actioned() {
        move.move(7, 20);
        assertEquals((board.getTileAt(new Position(7, 20)).toString()), "SC");
    }

    @Test
    void invalidMove_NotActioned() {
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