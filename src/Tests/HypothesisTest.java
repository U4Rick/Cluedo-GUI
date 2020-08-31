package Tests;

import Cards.CharacterCard;
import Cards.RoomCard;
import Cards.WeaponCard;
import GameMechanics.Action.Hypothesis;
import org.junit.jupiter.api.Test;

import static Cards.CharacterCard.CharacterEnum.*;
import static Cards.RoomCard.RoomEnum.*;
import static Cards.WeaponCard.WeaponEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class HypothesisTest {

    private final Hypothesis hypothesis = new Hypothesis(new CharacterCard(SCARLETT), new WeaponCard(CANDLESTICK), new RoomCard(BALLROOM));
    private final Hypothesis nullHypothesis = new Hypothesis(null, null, null);

    @Test
    void getCharacter() {
        assertEquals(hypothesis.getCharacter(), new CharacterCard(SCARLETT));
    }

    @Test
    void getWeapon() {
        assertEquals(hypothesis.getWeapon(), new WeaponCard(CANDLESTICK));
    }

    @Test
    void getRoom() {
        assertEquals(hypothesis.getRoom(), new RoomCard(BALLROOM));
    }

    @Test
    void setCharacter() {
        CharacterCard characterCard = new CharacterCard(SCARLETT);
        nullHypothesis.setCharacter(characterCard);
        assertEquals(nullHypothesis.getCharacter(), characterCard);
    }

    @Test
    void setWeapon() {
        WeaponCard weaponCard = new WeaponCard(CANDLESTICK);
        nullHypothesis.setWeapon(weaponCard);
        assertEquals(nullHypothesis.getWeapon(), weaponCard);
    }

    @Test
    void setRoom() {
        RoomCard roomCard = new RoomCard(BALLROOM);
        nullHypothesis.setRoom(roomCard);
        assertEquals(nullHypothesis.getRoom(), roomCard);
    }

    @Test
    void testToString() {
        assertEquals(hypothesis.toString(), "Miss Scarlett, Candlestick, Ballroom");
    }

    @Test
    void testEquals() {
        Hypothesis same = new Hypothesis(new CharacterCard(SCARLETT), new WeaponCard(CANDLESTICK), new RoomCard(BALLROOM));

        assertEquals(hypothesis, same);
        assertNotEquals(hypothesis, nullHypothesis);
    }
}