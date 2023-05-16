package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a deck of cards as a title, number of cards in the deck,
// and a list of the cards that belong to the deck
public class Deck implements Writable {
    private String title;          // name of the deck
    private int cardCount;         // number of cards that belong to the deck
    private ArrayList<Card> cards; // list of cards that belong to the deck


    // EFFECTS: constructs a new deck with a given title, a card count of 0,
    // and an empty list of cards.
    public Deck(String title) {
        this.title = title;
        cardCount = 0;
        cards = new ArrayList<>();
    }

    // REQUIRES: question and answer have non-zero length
    // MODIFIES: this
    // EFFECTS: adds a new card with given question and answer to end of deck, and increases cardCount by one.
    public void addCard(String question, String answer) {
        Card card = new Card(question, answer);
        cards.add(card);
        cardCount++;
        EventLog.getInstance().logEvent(new Event("Added Card: "
                + card.getQuestion() + " to Deck: " + getTitle()));
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: removes card with given index and decreases cardCount by one.
    public void removeCard(int index) {
        EventLog.getInstance().logEvent(new Event("Removed Card: "
                + cards.get(index).getQuestion() + " from Deck: " + getTitle()));
        cards.remove(index);
        cardCount--;
    }

    // MODIFIES: this, Card
    // EFFECTS: resets the status of every card in a deck to false
    public void resetStatus() {
        for (Card c : cards) {
            c.setStatusFalse();
        }
    }

    // getters
    public String getTitle() {
        return title;
    }

    public int getCardCount() {
        return cardCount;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public int getCardInt(Card c) {
        return cards.indexOf(c);
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("# of cards", cardCount);
        json.put("cards", cardsToJson());
        return json;
    }

    // EFFECTS: returns cards in deck as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
