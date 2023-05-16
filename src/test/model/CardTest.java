package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card testCard;

    @BeforeEach
    void testSetup() {
        testCard = new Card("What colour is the sky?", "blue");
    }

    @Test
    void testConstructor() {
        assertEquals("What colour is the sky?", testCard.getQuestion());
        assertEquals("blue", testCard.getAnswer());
        assertFalse(testCard.getStatus());
    }

    @Test
    void testGetCorrectness() {
        assertEquals("Incorrect", testCard.getCorrectness());
        testCard.setStatusTrue();
        assertEquals("Correct", testCard.getCorrectness());
        testCard.setStatusFalse();
        assertEquals("Incorrect", testCard.getCorrectness());
    }
}
