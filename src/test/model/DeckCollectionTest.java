package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckCollectionTest {
    private DeckCollection tdc;


    @BeforeEach
    void setup() {
        tdc = new DeckCollection();
        Deck d1 = new Deck("d1");
        tdc.addDeck(d1);
    }

    @Test
    void testConstructor() {
        assertEquals(1, tdc.getSize());
    }

    @Test
    void testAddDeck() {
        assertEquals(1, tdc.getSize());
        Deck d2 = new Deck("d2");
        tdc.addDeck(d2);
        assertEquals(2, tdc.getSize());
        Deck d3 = new Deck("d3");
        tdc.addDeck(d3);
        assertEquals(3, tdc.getSize());
    }

    @Test
    void testRemoveOnce() {
        assertEquals(1, tdc.getSize());
        tdc.removeDeck(0);
        assertEquals(0, tdc.getSize());
    }

    @Test
    void testRemoveMultiple() {
        Deck d2 = new Deck ("d2");
        tdc.addDeck(d2);
        assertEquals(2, tdc.getSize());
        tdc.removeDeck(0);
        assertEquals(1, tdc.getSize());
        tdc.removeDeck(0);
        assertEquals(0, tdc.getSize());
    }

    @Test
    void testIterator() {
        assertTrue(tdc.iterator().hasNext());
        tdc.removeDeck(0);
        assertFalse(tdc.iterator().hasNext());
    }
}
