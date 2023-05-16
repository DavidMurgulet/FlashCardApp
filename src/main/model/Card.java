package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a card having a question, answer and status (correct or incorrect)
public class Card implements Writable {
    private String question;    // question side of card
    private String answer;      // answer side of card
    private boolean status;     // correctness status

    // REQUIRES: question and answer have non-zero lengths
    // EFFECTS: constructs a new card object with given question, answer
    // and status set to false (incorrect)
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.status = false;
    }

    // MODIFIES: this
    // EFFECTS: returns either correct as a string if status of card is true, or incorrect
    // if status of the card is false.
    public String getCorrectness() {
        if (status) {
            return "Correct";
        } else {
            return "Incorrect";
        }
    }

    // getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getStatus() {
        return status;
    }

    // setters
    public void setStatusFalse() {
        status = false;
    }

    public void setStatusTrue() {
        status = true;
    }

    // EFFECTS: returns this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        json.put("status", status);
        return json;
    }
}
