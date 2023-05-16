package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    private Deck testDeck;


    @BeforeEach
    void testSetup() {
        testDeck = new Deck("Set 1");
    }

    @Test
    void testConstructor() {
        assertEquals("Set 1", testDeck.getTitle());
        assertEquals(0, testDeck.getCardCount());
        int setSize = testDeck.getCards().size();
        boolean tSet = testDeck.getCards().isEmpty();
        assertEquals(0, setSize);
        assertTrue(tSet);
    }


    @Test
    void testResetStatus() {
        testDeck.addCard("q", "a");
        testDeck.addCard("q2", "a2");
        assertFalse(testDeck.getCard(0).getStatus());
        assertFalse(testDeck.getCard(1).getStatus());
        testDeck.getCard(0).setStatusTrue();
        testDeck.getCard(1).setStatusTrue();
        assertTrue(testDeck.getCard(0).getStatus());
        assertTrue(testDeck.getCard(1).getStatus());
        testDeck.resetStatus();
        assertFalse(testDeck.getCard(0).getStatus());
        assertFalse(testDeck.getCard(1).getStatus());
    }

    @Test
    void testAddCard() {
        testDeck.addCard("q", "a");
        assertEquals(1, testDeck.getCardCount());
        assertEquals("q", testDeck.getCards().get(0).getQuestion());
        testDeck.addCard("q2", "a2");
        assertEquals(2, testDeck.getCardCount());
        assertEquals("q2", testDeck.getCards().get(1).getQuestion());
    }

    @Test
    void testRemoveCard() {
        testDeck.addCard("q", "a");
        testDeck.addCard("q2", "a2");
        assertEquals(2, testDeck.getCardCount());
        assertEquals("q2", testDeck.getCards().get(1).getQuestion());
        testDeck.removeCard(1);
        assertEquals(1, testDeck.getCardCount());
        testDeck.removeCard(0);
        assertEquals(0, testDeck.getCardCount());
    }

    @Test
    void testGetCardInt() {
        testDeck.addCard("q", "a");
        testDeck.addCard("q2", "a2");
        Card card1 = testDeck.getCard(0);
        Card card2 = testDeck.getCard(1);
        assertEquals(0, testDeck.getCardInt(card1));
        assertEquals(1, testDeck.getCardInt(card2));
    }

}
