package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// class representing an instance of the FlashCardApp with GUI
public class FlashCardUI extends JFrame {
    private static final String JSON_STORE = "./data/deckCollection.json";
    private static final Font FONT = new Font("Calibri", Font.BOLD, 24);
    private JPanel mainPanel;
    private JPanel practiceButtonsPanel;
    private JPanel subPanel;
    private JFrame submitDeck;
    private JFrame submitCard;
    private JFrame practiceSession;
    private JFrame imageFrame;
    private JLabel submitDeckLabel;
    private JLabel labelCardQ;
    private JLabel labelCardA;
    private JLabel displayQuestion;
    private JMenu fileMenu;
    private JMenuItem saveDecks;
    private JMenuItem loadDecks;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JScrollPane summaryScroll;

    private JTextField inputDeckField;
    private JTextField inputCardQuestionField;
    private JTextField inputCardAnswerField;

    private JLabel imageLabel;

    private JButton newDeck;
    private JButton removeDeck;
    private JButton viewDeck;
    private JButton submitDeckButton;
    private JButton submitCardButton;
    private JButton newCard;
    private JButton removeCard;
    private JButton practiceDeck;
    private JButton mainMenu;
    private JButton flipCard;
    private JButton answerCorrect;
    private JButton answerIncorrect;
    private JButton exitPracticeButton;

    private JPanel mainMenuButtonPanel;
    private JPanel cardViewButtonPanel;

    private JPanel deckPane;

    private JList deckList;
    private DefaultListModel deckListModel;
    private DefaultListModel summaryListModel;

    private DeckCollection userDecks;
    private Deck selectedDeck;
    private Card currentCard;

    // EFFECTS: constructs a new FlashCardUI object
    public FlashCardUI() {
        super("Flash Card App");
        initializePanels();
        initializeFields();
        initializeJFrames();
        initializeMenuBar();
        initializeButtons();
        initializeLabels();
        initWindowClose();
        setSize(new Dimension(700, 550));
        setLayout(new BorderLayout());
        initializeDeckList();
        setButtonSizes();
        displayMainMenuButtons();
        initializeButtonActions();
        setVisible(true);
        add(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes window Listener and sets frame to exit on close
    public void initWindowClose() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            // EFFECTS: this
            // MODIFIES: prints the logged events upon closing the window.
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
                super.windowClosing(e);
            }
        }
        );
    }


    // MODIFIES: this
    // EFFECTS: initializes all components of the menu bar at top of panel
    public void initializeMenuBar() {
        fileMenu = new JMenu("File");
        JMenuBar menuBar = new JMenuBar();
        saveDecks = new JMenuItem("Save");
        loadDecks = new JMenuItem("Load");
        fileMenu.add(saveDecks);
        fileMenu.add(loadDecks);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: initializes JPanel fields to their appropriate layouts
    public void initializePanels() {
        mainMenuButtonPanel = new JPanel(new GridLayout(1, 3));
        cardViewButtonPanel = new JPanel(new GridLayout(2, 2));
        mainPanel = new JPanel(new BorderLayout(10, 10));
        practiceButtonsPanel = new JPanel(new GridLayout(1, 3));
        subPanel = new JPanel();
        deckPane = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes all JLabel fields
    public void initializeLabels() {
        submitDeckLabel = new JLabel("Please enter a deck title:");
        labelCardQ = new JLabel("Please enter a question:");
        labelCardA = new JLabel("Please enter an answer:");
        imageLabel = new JLabel();

        labelCardQ.setFont(FONT);
        labelCardA.setFont(FONT);
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame fields
    public void initializeJFrames() {
        submitDeck = new JFrame("Create a Deck!");
        submitCard = new JFrame("Create a Card!");
        practiceSession = new JFrame("Practice Session!");
        imageFrame = new JFrame("Practice Complete!");
    }

    // MODIFIES: this
    // EFFECTS: removes the menu bar from the main frame
    public void removeMenuBar() {
        this.setJMenuBar(null);
    }

    // MODIFIES: this
    // EFFECTS: initializes all JButton fields
    public void initializeButtons() {
        newDeck = new JButton("New Deck");
        removeDeck = new JButton("Remove Deck");
        viewDeck = new JButton("View Deck");
        newCard = new JButton("New Card");
        removeCard = new JButton("Remove Card");
        practiceDeck = new JButton("Practice Deck");
        mainMenu = new JButton("Return to menu");
        submitDeckButton = new JButton("Submit");
        submitCardButton = new JButton("Submit");
        flipCard = new JButton("Flip Card");
        answerCorrect = new JButton("Correct");
        answerIncorrect = new JButton("Incorrect");
        exitPracticeButton = new JButton("Exit");
    }

    // EFFECTS: prints all logged events to the console
    public void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes fields that are not JSwing
    public void initializeFields() {
        userDecks = new DeckCollection();
        selectedDeck = null;
        currentCard = null;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialize the main deck/card display list
    public void initializeDeckList() {
        deckListModel = new DefaultListModel();
        deckList = new JList(deckListModel);
        deckList.setFont(FONT);
        deckList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(deckList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        deckPane.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(deckPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes all buttons actions with respective ActionListener classes
    public void initializeButtonActions() {
        newDeck.addActionListener(new NewDeckListener());
        removeDeck.addActionListener(new RemoveDeckListener());
        loadDecks.addActionListener(new LoadDecksListener());
        saveDecks.addActionListener(new SaveDecksListener());
        viewDeck.addActionListener(new ViewDecksListener());
        newCard.addActionListener(new NewCardListener());
        removeCard.addActionListener(new RemoveCardListener());
        practiceDeck.addActionListener(new PracticeDeckListener());
        mainMenu.addActionListener(new ReturnToMenuListener());
        submitDeckButton.addActionListener(new SubmitDeckButtonListener());
        submitCardButton.addActionListener(new SubmitCardButtonListener());
        flipCard.addActionListener(new FlipCardListener());
        answerCorrect.addActionListener(new AnswerCorrectListener());
        answerIncorrect.addActionListener(new AnswerIncorrectListener());
        exitPracticeButton.addActionListener(new ExitButtonListener());
    }

    // MODIFIES: this
    // EFFECTS: displays the main menu button options
    public void displayMainMenuButtons() {
        mainMenuButtonPanel.add(newDeck);
        mainMenuButtonPanel.add(viewDeck);
        mainMenuButtonPanel.add(removeDeck);
        mainPanel.add(mainMenuButtonPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: sets sizes for buttons in application
    public void setButtonSizes() {
        newDeck.setPreferredSize(new Dimension(300, 100));
        removeDeck.setPreferredSize(new Dimension(300, 100));
        viewDeck.setPreferredSize(new Dimension(300, 100));
        saveDecks.setPreferredSize(new Dimension(100, 50));
        loadDecks.setPreferredSize(new Dimension(100, 50));
        fileMenu.setPreferredSize(new Dimension(75, 50));
        newCard.setPreferredSize(new Dimension(300, 80));
        practiceDeck.setPreferredSize(new Dimension(300, 80));
        removeCard.setPreferredSize(new Dimension(300, 80));
        mainMenu.setPreferredSize(new Dimension(300, 80));
        submitDeckButton.setPreferredSize(new Dimension(100, 50));
        submitCardButton.setPreferredSize(new Dimension(200, 100));
    }

    // represents the listener for the NewDeck button
    public class NewDeckListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: creates a new deck and adds it to userDecks upon clicking button
        @Override
        public void actionPerformed(ActionEvent e) {
            submitDeck.setSize(new Dimension(700, 500));
            submitDeck.setLayout(new GridLayout(3, 3));
            submitDeckLabel.setFont(FONT);
            inputDeckField = new JTextField();
            inputDeckField.setPreferredSize(new Dimension(100, 40));
            inputDeckField.setFont(FONT);
            submitDeck.add(submitDeckLabel, BorderLayout.CENTER);
            submitDeck.add(inputDeckField);
            submitDeck.add(submitDeckButton);
            submitDeck.revalidate();
            submitDeck.setVisible(true);
        }
    }

    // represents the listener for the RemoveDeck button
    public class RemoveDeckListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: removes a deck from userDecks and also removes from the display list
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = deckList.getSelectedIndex();
            deckListModel.remove(index);
            userDecks.removeDeck(index);
            deckList.revalidate();
        }
    }

    // represents the listener for the SubmitDeck button
    public class SubmitDeckButtonListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: submits the deckTitle and creates a new deck with that title, adding
        // it to userDecks and the display list
        @Override
        public void actionPerformed(ActionEvent e) {
            String deckTitle = inputDeckField.getText();
            Deck d = new Deck(deckTitle);
            userDecks.addDeck(d);
            deckListModel.addElement(d.getTitle() + " . . . . . . . " + d.getCardCount() + " cards");
            clearSubmitDeck();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all parts of submitDeck panel and closes it
    public void clearSubmitDeck() {
        submitDeck.remove(submitDeckLabel);
        submitDeck.remove(inputDeckField);
        submitDeck.remove(submitDeckButton);
        submitDeck.dispose();
    }

    // MODIFIES: this
    // EFFECTS: removes all parts of submitCard panel and closes it
    public void clearSubmitCard() {
        submitCard.remove(labelCardQ);
        submitCard.remove(inputCardQuestionField);
        submitCard.remove(labelCardA);
        submitCard.remove(inputCardAnswerField);
        subPanel.remove(submitCardButton);
        submitCard.remove(subPanel);
        submitCard.dispose();
    }

    // represents a listener for the LoadDecks button
    class LoadDecksListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: loads the saved decks into the userDecks collection from the file in which
        // they were saved in
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                userDecks = jsonReader.read();
                deckListModel.removeAllElements();
                addDecksToList();

            } catch (IOException ex) {
                System.out.println("Unable to read from file " + JSON_STORE);
            }
        }
    }

    // represents a listener for the SaveDecks button
    class SaveDecksListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: saves the current userDecks into a JSON file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.writeCollection(userDecks);
                jsonWriter.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // represents a listener for the ViewDecks button
    class ViewDecksListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: prints the card belonging to the currently selected deck to the display
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = deckList.getSelectedIndex();
            selectedDeck = userDecks.getDeck(index);
            deckListModel.removeAllElements();

            for (Card c : selectedDeck.getCards()) {
                deckListModel.addElement(c.getQuestion());
            }

            mainPanel.remove(mainMenuButtonPanel);
            removeMenuBar();

            initCardViewButtons();

            mainPanel.add(cardViewButtonPanel, BorderLayout.SOUTH);
            mainPanel.revalidate();
        }
    }


    // represents a listener for the ReturnToMenu button
    public class ReturnToMenuListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: returns to the main menu upon clicking the mainMenu button
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.remove(cardViewButtonPanel);
            deckListModel.removeAllElements();
            addDecksToList();
            displayMainMenuButtons();
            initializeMenuBar();
            loadDecks.addActionListener(new LoadDecksListener());
            saveDecks.addActionListener(new SaveDecksListener());
            mainPanel.revalidate();
        }
    }

    // represents a listener for the NewCard button
    public class NewCardListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: opens a new prompt up to add a new Card to the selected deck
        @Override
        public void actionPerformed(ActionEvent e) {
            submitCard.setSize(new Dimension(700, 500));
            submitCard.setLayout(new GridLayout(3, 2));

            inputCardQuestionField = new JTextField();
            inputCardAnswerField = new JTextField();
            inputCardAnswerField.setFont(FONT);
            inputCardQuestionField.setFont(FONT);

            submitCardButton.setPreferredSize(new Dimension(600, 200));

            submitCard.add(labelCardQ);
            submitCard.add(inputCardQuestionField);
            submitCard.add(labelCardA);
            submitCard.add(inputCardAnswerField);

            subPanel.add(submitCardButton);
            submitCard.add(subPanel, BorderLayout.SOUTH);

            submitCard.revalidate();
            submitCard.setVisible(true);
        }
    }

    // represents a listener for the RemoveCard button
    public class RemoveCardListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: removes a card from the current deck upon clicking remove button
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = deckList.getSelectedIndex();
            deckListModel.remove(index);
            selectedDeck.removeCard(index);
            deckList.setSelectedIndex(index);
            deckList.revalidate();
        }
    }


    // represents a listener for the PracticeDeck button
    public class PracticeDeckListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: starts a practice session for the current deck
        @Override
        public void actionPerformed(ActionEvent e) {
            practiceSession.setSize(new Dimension(700, 300));
            disableAnswerButtons();
            imageFrame.remove(imageLabel);
            practiceSession.setLayout(new GridLayout(2, 3));
            currentCard = selectedDeck.getCard(0);
            String firstQuestion = currentCard.getQuestion();
            displayQuestion = new JLabel(firstQuestion);
            displayQuestion.setHorizontalAlignment(SwingConstants.CENTER);
            displayQuestion.setFont(FONT);
            initPracticeButtons();
            practiceSession.add(displayQuestion, BorderLayout.CENTER);
            practiceSession.add(practiceButtonsPanel);
            practiceSession.revalidate();
            practiceSession.repaint();
            practiceSession.setVisible(true);
        }
    }

    // represents a listener for the FlipCard button
    public class FlipCardListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: displays the card answer after clicking button
        @Override
        public void actionPerformed(ActionEvent e) {
            if (displayQuestion.getText() == currentCard.getQuestion()) {
                String flip = currentCard.getAnswer();
                displayQuestion.setText(flip);
            } else {
                String flip = currentCard.getQuestion();
                displayQuestion.setText(flip);
            }

            flipCard.setEnabled(false);
            enableAnswerButtons();
            practiceSession.revalidate();
        }
    }

    // represents a listener for the AnswerCorrect button
    public class AnswerCorrectListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: assigns current card status to correct and displays next question
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedDeck.getCardInt(currentCard) < selectedDeck.getCardCount() - 1) {
                currentCard.setStatusTrue();
                int nextIndex = selectedDeck.getCardInt(currentCard) + 1;
                currentCard = selectedDeck.getCard(nextIndex);
                String nextQuestion = currentCard.getQuestion();
                displayQuestion.setText(nextQuestion);
                disableAnswerButtons();
                flipCard.setEnabled(true);
            } else {
                currentCard.setStatusTrue();
                displayImage();
                resetPractice();
                initSummary();
                displayResults();
                practiceSession.add(exitPracticeButton, BorderLayout.SOUTH);
                practiceSession.repaint();
                practiceSession.revalidate();
            }
        }
    }

    // EFFECTS: displays the corresponding image after finishing practice session
    public void displayImage() {
        if (allCorrect()) {
            setHappyImage();
        } else {
            setSadImage();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the happy image in the image frame
    public void setHappyImage() {
        imageFrame.setSize(new Dimension(736, 875));
        imageFrame.setLayout(new BorderLayout());
        ImageIcon imageHappy = new ImageIcon("HappyFaceAllCorrect.jpg");
        imageLabel.setIcon(imageHappy);
        imageFrame.add(imageLabel, BorderLayout.CENTER);
        imageFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the sad image in the image frame
    public void setSadImage() {
        imageFrame.setSize(new Dimension(575, 780));
        imageFrame.setLayout(new BorderLayout());
        ImageIcon imageSad = new ImageIcon("SadFaceMistake.jpg");
        imageLabel.setIcon(imageSad);
        imageFrame.add(imageLabel, BorderLayout.CENTER);
        imageFrame.setVisible(true);
    }

    // EFFECTS: returns true if all cards have status correct
    //          and returns false otherwise
    public boolean allCorrect() {
        for (Card c : selectedDeck.getCards()) {
            if (!c.getStatus()) {
                return false;
            }
        }
        return true;
    }

    // represents a listener for the AnswerIncorrect button
    public class AnswerIncorrectListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: assigns current card status as incorrect and displays the next question
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedDeck.getCardInt(currentCard) < selectedDeck.getCardCount() - 1) {
                currentCard.setStatusFalse();
                int nextIndex = selectedDeck.getCardInt(currentCard) + 1;
                currentCard = selectedDeck.getCard(nextIndex);

                String nextQuestion = currentCard.getQuestion();
                displayQuestion.setText(nextQuestion);

                disableAnswerButtons();
                flipCard.setEnabled(true);
            } else {
                currentCard.setStatusFalse();
                displayImage();
                resetPractice();
                initSummary();
                displayResults();
                practiceSession.add(exitPracticeButton, BorderLayout.SOUTH);
                practiceSession.repaint();
                practiceSession.revalidate();
            }
        }
    }

    // represents a listener for the Exit button
    public class ExitButtonListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: returns to the card view menu
        @Override
        public void actionPerformed(ActionEvent e) {
            practiceSession.remove(summaryScroll);
            practiceSession.remove(exitPracticeButton);
            flipCard.setEnabled(true);
            practiceSession.dispose();
        }
    }

    // represents a listener for the SubmitCard button
    public class SubmitCardButtonListener implements ActionListener {

        // MODIFIES: FlashCardUI
        // EFFECTS: creates a new card with the entered information and adds it to the current deck
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardQuestion = inputCardQuestionField.getText();
            String cardAnswer = inputCardAnswerField.getText();

            selectedDeck.addCard(cardQuestion, cardAnswer);
            deckListModel.addElement(cardQuestion);

            clearSubmitCard();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all aspects of the practiceSession panel
    public void resetPractice() {
        practiceSession.remove(displayQuestion);
        practiceSession.remove(practiceButtonsPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the list for the post-practice summary
    public void initSummary() {
        summaryListModel = new DefaultListModel();
        JList summaryList = new JList(summaryListModel);
        summaryList.setFont(new Font("Calibri", Font.BOLD, 20));

        summaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        summaryScroll = new JScrollPane(summaryList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        summaryScroll.setPreferredSize(new Dimension(500, 500));

        practiceSession.add(summaryScroll, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds the results of a practice session to the display
    public void displayResults() {
        for (Card c : selectedDeck.getCards()) {
            summaryListModel.addElement(c.getQuestion() + " . . . . . . . " + c.getCorrectness());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all deck current deck titles to the decklist
    public void addDecksToList() {
        for (Deck d : userDecks) {
            deckListModel.addElement(d.getTitle() + " . . . . . . . " + d.getCardCount() + " cards");
        }
    }

    // MODIFIES: this
    // EFFECTS: disables the Correct/Incorrect buttons present during a practice session
    public void disableAnswerButtons() {
        answerCorrect.setEnabled(false);
        answerIncorrect.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: enables the Correct/Incorrect buttons present during a practice session
    public void enableAnswerButtons() {
        answerCorrect.setEnabled(true);
        answerIncorrect.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes practice buttons during a practice session
    public void initPracticeButtons() {
        practiceButtonsPanel.add(flipCard);
        practiceButtonsPanel.add(answerCorrect);
        practiceButtonsPanel.add(answerIncorrect);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons in the card view menu
    public void initCardViewButtons() {
        cardViewButtonPanel.add(newCard);
        cardViewButtonPanel.add(practiceDeck);
        cardViewButtonPanel.add(removeCard);
        cardViewButtonPanel.add(mainMenu);
    }
}
