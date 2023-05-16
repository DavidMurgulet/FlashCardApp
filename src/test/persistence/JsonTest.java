package persistence;

import model.Card;
import model.Deck;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Implementation of methods inspired by JSONTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkDeck(String title, int cardCount, Deck deck) {
        assertEquals(title, deck.getTitle());
        assertEquals(cardCount, deck.getCardCount());
    }

    protected void checkCard(String q, String a, Boolean status, Card card) {
        assertEquals(q, card.getQuestion());
        assertEquals(a, card.getAnswer());
        if (status) {
            assertTrue(card.getStatus());
        } else {
            assertFalse(card.getStatus());
        }
    }
}
