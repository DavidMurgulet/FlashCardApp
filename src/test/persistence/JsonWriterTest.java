package persistence;

import model.Deck;
import model.DeckCollection;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests adapted from JSONWriterTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            DeckCollection dc = new DeckCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (FileNotFoundException e) {
            // nothing here
        }
    }

    @Test
    void testWriterEmptyDeckCollection() {
        try {
            DeckCollection dc = new DeckCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDeckCollection.json");
            writer.open();
            writer.writeCollection(dc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDeckCollection.json");
            dc = reader.read();
            assertEquals(0, dc.getSize());
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    void testWriterGeneralDeckCollection() {
        try {
            DeckCollection dc = new DeckCollection();
            Deck deck1 = new Deck("Deck 1");
            Deck deck2 = new Deck("Deck 2");
            deck1.addCard("question", "answer");
            deck2.addCard("hello", "hi");
            dc.addDeck(deck1);
            dc.addDeck(deck2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDeckCollection.json");
            writer.open();
            writer.writeCollection(dc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDeckCollection.json");
            dc = reader.read();
            assertEquals(2, dc.getSize());
            checkDeck("Deck 1", 1, dc.getDeck(0));
            checkDeck("Deck 2", 1, dc.getDeck(1));
            checkCard("question", "answer", false, deck1.getCard(0));
            checkCard("hello", "hi", false, deck2.getCard(0));
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }
}
