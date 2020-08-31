package Tests.EntityTests;

import Game.Actions.Hypothesis;
import Game.Board.Board;
import Game.Cards.CharacterCard;
import Game.Cards.RoomCard;
import Game.Cards.WeaponCard;
import Game.Entities.Player;
import Game.Tiles.Position;
import Game.Tiles.Tile;
import org.junit.Before;
import org.junit.Test;

import static Game.Cards.CharacterCard.CharacterEnum.SCARLETT;
import static Game.Cards.RoomCard.RoomEnum.*;
import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.Assert.*;

public class PlayerTest {

    Player player;

    @Before
    public void setUp() {
        player = new Player(new CharacterCard(SCARLETT), new Tile(new Position(4, 20)), new Board(), "test");
    }

    @Test
    public void addCardToHand() {
        int startSize = player.getHand().size();
        player.addCardToHand(new CharacterCard(SCARLETT));
        assertEquals(player.getHand().size(), startSize + 1);
    }

    @Test
    public void madeFalseAccusation() {
        assertTrue(player.hasNotMadeFalseAccusation());
        player.madeFalseAccusation();
        assertFalse(player.hasNotMadeFalseAccusation());
    }

    @Test
    public void getRefutableCards() {
        //setup cards
        CharacterCard characterCard = new CharacterCard(SCARLETT);
        WeaponCard weaponCard = new WeaponCard(CANDLESTICK);
        RoomCard roomCard = new RoomCard(BALLROOM);

        //create hypothesis
        Hypothesis hypothesis = new Hypothesis(characterCard, weaponCard, roomCard);

        //Check refutable card sizes
        assertEquals(player.getRefutableCards(hypothesis).size(), 0);
        player.addCardToHand(characterCard);
        assertEquals(player.getRefutableCards(hypothesis).size(), 1);
        player.addCardToHand(weaponCard);
        assertEquals(player.getRefutableCards(hypothesis).size(), 2);
        player.addCardToHand(roomCard);
        assertEquals(player.getRefutableCards(hypothesis).size(), 3);
    }

    @Test
    public void isInRoom() {
        assertFalse(player.isInRoom());
        player.setTile(new Tile(new Position(6, 19)));
        assertTrue(player.isInRoom());
    }

    @Test
    public void roomPlayerIsIn() {
        assertNull(player.roomPlayerIsIn());
        player.setTile(new Tile(new Position(6, 19)));
        assertEquals(player.roomPlayerIsIn(), LOUNGE);
    }

    @Test
    public void testToString() {
        assertEquals(player.toString(), "SC");
    }
}