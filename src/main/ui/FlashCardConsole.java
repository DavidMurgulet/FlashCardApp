package ui;


import model.Card;
import model.Deck;
import model.DeckCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// represents a FlashCardApp instance.
public class FlashCardConsole {
    private Scanner userInput;         // scanner input
    private DeckCollection userDecks;  // list of decks belonging to instance
    private Deck selectedDeck;         // currently selected deck
    private boolean runProgram;        // determines whether program should continue execution
    private int currentMenu;           // int representing the current menu being displayed

    private static final String JSON_STORE = "./data/deckCollection.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    // EFFECTS: runs the flash card application
    public FlashCardConsole() {
        runProgram();
    }

    // format similar to TellerApp
    // MODIFIES: this
    // EFFECTS: initializes flash card program.
    private void init() {
        userInput = new Scanner(System.in);
        userDecks = new DeckCollection();
        selectedDeck = null;
        runProgram = true;
        currentMenu = 0;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // format extracted from TellerApp
    // MODIFIES: this
    // EFFECTS: starts the program and checks if program should keep running.
    public void runProgram() {
        init();
        mainMenu();
        while (runProgram) {
            String input = userInput.next();
            navigateMenus(input);
        }
    }

    // REQUIRES: c parameter must be non-empty string
    // MODIFIES: this
    // EFFECTS: keeps track of current menu and resets the current menu upon invalid input
    private void navigateMenus(String c) {
        if (c.equals("q")) {
            runProgram = false;
            quitProgram();
        } else if (currentMenu == 0) {
            mainMenuCommands(c);
        } else if (currentMenu == 1) {
            deckViewCommands(c);
        } else if (currentMenu == 2) {
            selectedDeckCommands(c);
        } else if (currentMenu == 3) {
            postPracticeMenuCommands(c);
        }
    }

    // EFFECTS: displays main menu options
    private void mainMenu() {
        System.out.println("Welcome to Flash Card App! Please select an option.");
        System.out.println("\tv -> view decks");
        System.out.println("\tq -> quit");
    }

    // REQUIRES: c must be a non-empty string
    // MODIFIES: this
    // EFFECTS: processes appropriate command for the main menu options
    private void mainMenuCommands(String c) {
        if (c.equals("v")) {
            currentMenu = 1;
            printDecks();
            deckViewMenu();
        } else if (c.equals("q")) {
            quitProgram();
        } else {
            System.out.println("Invalid input, please try again.");
        }
    }

    // EFFECTS: displays the deck menu options; create new deck, remove a deck, select a deck
    // and quit the program.
    private void deckViewMenu() {
        System.out.println("Select an option.");
        System.out.println("\tn -> create a new deck");
        System.out.println("\tr -> remove a deck");
        System.out.println("\ts -> select a deck");
        System.out.println("\tl -> load decks from file");
        System.out.println("\tu -> save decks to file");
        System.out.println("\tq -> quit program");
    }

    // REQUIRES: c must be a non-empty string
    // MODIFIES: this
    // EFFECTS: processes appropriate command for the deck menu, sets currentMenu to 2 if
    // input is "s".
    private void deckViewCommands(String c) {
        if (c.equals("n")) {
            createDeck();
        } else if (c.equals("r")) {
            removeDeck();
        } else if (c.equals("s")) {
            currentMenu = 2;
            selectDeck();
        } else if (c.equals("q")) {
            quitProgram();
        } else if (c.equals("u")) {
            saveUserDecks();
            printDecks();
            deckViewMenu();
        } else if (c.equals("l")) {
            loadUserDecks();
            printDecks();
            deckViewMenu();
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // EFFECTS: prints a list of current deck titles and their corresponding number.
    private void printDecks() {
        if (userDecks.getSize() == 0) {
            System.out.println();
            System.out.println("There are no decks.");
            System.out.println();
        } else {
            System.out.println("Current decks:");
            int num = 1;
            for (Deck d : userDecks) {
                System.out.println(num + ": " + d.getTitle());
                num++;
            }
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new deck with a given title and adds it to userDecks.
    private void createDeck() {
        System.out.println("Name the deck.");
        userInput.nextLine();
        String name = userInput.nextLine();
        Deck deck = new Deck(name);
        userDecks.addDeck(deck);
        System.out.println(name + " has been created.");
        printDecks();
        deckViewMenu();
    }

    // REQUIRES: input must be an int
    // MODIFIES: this
    // EFFECTS: selects a deck by its corresponding number
    private void selectDeck() {
        System.out.println("Select a deck by its corresponding number.");
        int input = userInput.nextInt();
        selectedDeck = userDecks.getDeck(input - 1);
        printCards();
        selectedDeckMenu();
    }

    // EFFECTS: displays the options for the selected deck menu
    private void selectedDeckMenu() {
        System.out.println("Select an option.");
        System.out.println("\ta -> add a card");
        System.out.println("\tr -> remove a card");
        System.out.println("\tp -> practice set");
        System.out.println("\tb -> previous menu");
    }


    // REQUIRES: c cannot be empty-string.
    // MODIFIES: this
    // EFFECTS: processes appropriate command for the selected deck menu
    private void selectedDeckCommands(String c) {
        if (c.equals("a")) {
            addCardToDeck();
        } else if (c.equals("r")) {
            removeCardFromDeck();
        } else if (c.equals("p")) {
            practiceDeck();
        } else if (c.equals("b")) {
            printDecks();
            deckViewMenu();
            currentMenu = 1;
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // EFFECTS: prints a list of cards belonging to the currently selected deck
    private void printCards() {
        if (selectedDeck.getCardCount() == 0) {
            System.out.println("This deck is empty.");
        } else {
            System.out.println("Cards belonging to current deck:");
            int num = 1;
            for (Card c : selectedDeck.getCards()) {
                System.out.println(num + ": " + c.getQuestion());
                num++;
            }
            System.out.println();
        }
    }

    // REQUIRES: question and answer inputs cannot be empty-string
    // MODIFIES: this
    // EFFECTS: adds a card with a given answer and question to the currently selected deck.
    private void addCardToDeck() {
        System.out.println("Input a question.");
        userInput.nextLine();
        String question = userInput.nextLine();
        System.out.println("Input an answer.");
        String answer = userInput.nextLine();
        selectedDeck.addCard(question, answer);
        System.out.println("Card added!");
        printCards();
        selectedDeckMenu();
    }

    // REQUIRES: CardCount of deck >= 0
    // MODIFIES: this, Card
    // EFFECTS: practices the current deck.
    private void practiceDeck() {
        if (selectedDeck.getCardCount() == 0) {
            printCards();
            selectedDeckMenu();
        } else {
            practiceDeckLoop();
            showPracticeSummary();
            postPracticeMenu();
        }
    }

    // REQUIRES: correctness inputs are must be either "y" or "n".
    // MODIFIES: this, Card
    // EFFECTS: Begins practice on the selected deck. Question of a card will be displayed and users
    // will be prompted to declare if they got the answer correct. This will occur for each card in the deck.
    private void practiceDeckLoop() {
        System.out.println("Ok! Let's practice.");
        System.out.println();
        for (Card c : selectedDeck.getCards()) {
            System.out.println("Question: " + c.getQuestion());
            System.out.println();
            System.out.println("Input any key to flip card.");
            String input = userInput.next();
            System.out.println("Answer: " + c.getAnswer());
            System.out.println();
            System.out.println("Was your answer correct? (y or n)");
            String y = userInput.next();
            if (y.equals("y")) {
                c.setStatusTrue();
            } else if (y.equals("n")) {
                c.setStatusFalse();
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: lists out the cards of the selected deck and whether they were answered correctly or not
    // in the format: card number: "question" | "Correct" or "Incorrect".
    private void showPracticeSummary() {
        currentMenu = 3;
        System.out.println("Summary of your practice session:");
        System.out.println();
        int num = 1;
        for (Card c : selectedDeck.getCards()) {
            System.out.println(num + ": " + c.getQuestion() + " | " + c.getCorrectness());
            num++;
        }
    }

    // REQUIRES: input must be an integer
    // MODIFIES: this
    // EFFECTS: removes a deck from userDecks and returns to the deck menu
    private void removeDeck() {
        System.out.println("Select a deck by its corresponding number.");
        int input = userInput.nextInt();
        userDecks.removeDeck(input - 1);
        System.out.println("Deck has been removed.");
        printDecks();
        deckViewMenu();
    }

    // EFFECTS: displays the menu options after practicing a deck
    private void postPracticeMenu() {
        System.out.println();
        System.out.println("Please select an option.");
        System.out.println("r -> retry current deck.");
        System.out.println("b -> return back to selected deck menu");
    }

    // REQUIRES: input must be an integer.
    // MODIFIES: this, Deck
    // EFFECTS: removes a card from a deck, prints the cards in selected deck
    // and returns to selected deck menu
    private void removeCardFromDeck() {
        System.out.println("Select a card by its corresponding number.");
        int input = userInput.nextInt();
        selectedDeck.removeCard(input - 1);
        System.out.println("Card removed!");
        printCards();
        selectedDeckMenu();
    }

    // REQUIRES: c must be a non-empty string
    // MODIFIES: this
    // EFFECTS: removes a card from a deck
    private void postPracticeMenuCommands(String c) {
        if (c.equals("r")) {
            resetSelectedDeck();
            practiceDeck();
        } else if (c.equals("b")) {
            printCards();
            selectedDeckMenu();
            currentMenu = 2;
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // REQUIRES: cardCount of deck > 0
    // MODIFIES: this, Card, Deck
    // EFFECTS: resets cards in selected desk to false.
    private void resetSelectedDeck() {
        selectedDeck.resetStatus();
    }


    // Implementation inspired by JsonSerializationDemo
    // EFFECTS: saves the DeckCollection to file
    private void saveUserDecks() {
        try {
            jsonWriter.open();
            jsonWriter.writeCollection(userDecks);
            jsonWriter.close();
            System.out.println("Saved current decks to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Implementation extracted from JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads DeckCollection from file, if there are already decks loaded, adds decks from file
    //          to current decks.
    private void loadUserDecks() {
        try {
            DeckCollection copy = userDecks;
            userDecks = jsonReader.read();

            for (Deck d : copy) {
                userDecks.addDeck(d);
            }

            System.out.println("Loaded decks from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // EFFECTS: terminates the program
    private void quitProgram() {
        System.out.println("Thank you for using Flash Card App!");
        System.exit(0);
    }
}