package persistence;

import model.Deck;
import model.DeckCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests adapted from JSONReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/NonExistentFile.json");
        try {
            DeckCollection dc = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // nothing here
        }
    }

    @Test
    void testReaderEmptyDeckCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDeckCollection.json");
        try {
            DeckCollection dc = reader.read();
            assertEquals(0, dc.getSize());
        } catch (IOException e) {
            fail("Couldn't read the file");
        }
    }

    @Test
    void testReaderGeneralDeckCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDeckCollection.json");
        try {
            DeckCollection dc = reader.read();
            Deck deck1 = dc.getDeck(0);
            Deck deck2 = dc.getDeck(1);
            assertEquals(2, dc.getSize());
            checkDeck("Deck 1", 1, dc.getDeck(0));
            checkDeck("Deck 2", 1, dc.getDeck(1));
            checkCard("question", "answer", false, deck1.getCard(0));
            checkCard("hello", "hi", false, deck2.getCard(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
