package persistence;

import model.Card;
import model.Deck;
import model.DeckCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // Implementation of method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DeckCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDeckCollection(jsonObject);
    }

    // Implementation of method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // Implementation of method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses DeckCollection from JSON object and returns it
    private DeckCollection parseDeckCollection(JSONObject jsonObject) {
        DeckCollection deckCol = new DeckCollection();
        addDecks(deckCol, jsonObject);
        return deckCol;
    }

    // Implementation of method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: dc
    // EFFECTS: parses decks from JSON object and adds them to DeckCollection
    private void addDecks(DeckCollection dc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("decks");
        for (Object json : jsonArray) {
            JSONObject nextDeck = (JSONObject) json;
            addDeck(dc, nextDeck);
        }
    }

    // Implementation of method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: dc
    // EFFECTS: parses deck from JSON object and adds it to workroom
    private void addDeck(DeckCollection dc, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        Deck deck = new Deck(title);
        dc.addDeck(deck);
        addCards(deck, jsonObject);
    }

    // MODIFIES: deck
    // EFFECTS: parses cards from JSON object and its to deck
    private void addCards(Deck deck, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(deck, nextCard);
        }
    }

    // MODIFIES: deck
    // EFFECTS: parses card from JSON object and adds it to deck
    private void addCard(Deck deck, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        deck.addCard(question, answer);
    }
}
