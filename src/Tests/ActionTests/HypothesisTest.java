package Tests.ActionTests;

import Game.Cards.CharacterCard;
import Game.Cards.RoomCard;
import Game.Cards.WeaponCard;
import Game.Actions.Hypothesis;
import org.junit.Test;


import static Game.Cards.CharacterCard.CharacterEnum.*;
import static Game.Cards.RoomCard.RoomEnum.*;
import static Game.Cards.WeaponCard.WeaponEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class HypothesisTest {

    private final Hypothesis hypothesis = new Hypothesis(new CharacterCard(SCARLETT), new WeaponCard(CANDLESTICK), new RoomCard(BALLROOM));
    private final Hypothesis nullHypothesis = new Hypothesis(null, null, null);

    @Test
    public void getCharacter() {
        assertEquals(hypothesis.getCharacter(), new CharacterCard(SCARLETT));
    }

    @Test
    public void getWeapon() {
        assertEquals(hypothesis.getWeapon(), new WeaponCard(CANDLESTICK));
    }

    @Test
    public void getRoom() {
        assertEquals(hypothesis.getRoom(), new RoomCard(BALLROOM));
    }

    @Test
    public void setCharacter() {
        CharacterCard characterCard = new CharacterCard(SCARLETT);
        nullHypothesis.setCharacter(characterCard);
        assertEquals(nullHypothesis.getCharacter(), characterCard);
    }

    @Test
    public void setWeapon() {
        WeaponCard weaponCard = new WeaponCard(CANDLESTICK);
        nullHypothesis.setWeapon(weaponCard);
        assertEquals(nullHypothesis.getWeapon(), weaponCard);
    }

    @Test
    public void setRoom() {
        RoomCard roomCard = new RoomCard(BALLROOM);
        nullHypothesis.setRoom(roomCard);
        assertEquals(nullHypothesis.getRoom(), roomCard);
    }

    @Test
    public void testToString() {
        assertEquals(hypothesis.toString(), "Miss Scarlett, Candlestick, Ballroom");
    }

    @Test
    public void testEquals() {
        Hypothesis same = new Hypothesis(new CharacterCard(SCARLETT), new WeaponCard(CANDLESTICK), new RoomCard(BALLROOM));

        assertEquals(hypothesis, same);
        assertNotEquals(hypothesis, nullHypothesis);
    }
}