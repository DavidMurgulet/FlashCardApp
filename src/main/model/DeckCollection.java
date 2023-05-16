package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Represents current instance of decks in the FlashCardApp as a list of decks
public class DeckCollection implements Writable, Iterable<Deck> {
    private List<Deck> deckList;   // list of Decks belonging to the DeckCollection

    // EFFECTS: constructs a new deck collection containing an empty list of decks
    public DeckCollection() {
        deckList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds deck d to the deck collection
    public void addDeck(Deck d) {
        deckList.add(d);
    }

    // REQUIRES: i >= 0
    // MODIFIES: this
    // EFFECTS: removes a deck with the given index from the deck collection
    public void removeDeck(int i) {
        deckList.remove(i);
    }

    // getters
    public int getSize() {
        return deckList.size();
    }

    public Deck getDeck(int i) {
        return deckList.get(i);
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("decks", decksToJson());
        return json;
    }


    // EFFECTS: returns decks in deckCollection as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Deck d : deckList) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns this as an iterator
    @Override
    public Iterator iterator() {
        return deckList.iterator();
    }
}

