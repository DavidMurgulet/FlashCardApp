# Flash Card Program

## Introduction
This is a flash card program. It's intended use is for studying.
Users will be able to create a **deck** of **cards** related 
to any specific topic in order to test their knowledge on the said topic.
This topic was interesting to me because it was simple and many flash card programs 
hide features behind paywall. I'm intending on implementing some of those features into this project.

## Cards and Decks
Decks can be created at any time and are organized by order in which they were added.
Upon selecting a deck, the user will be able to add or remove cards to the deck.
Each card will have two sides, which are both picked by the user when prompted 
by the console. One side will have a question and the other side will have the answer.
During a practice session, upon flipping the card to the answer side, the user will have to declare
whether they got the card correct or incorrect by inputting either "y", or "n". 
After practicing an entire deck, the user will be given a summary of their practice session
and will have the option to repeat the deck, or return to the menu to select a different deck.
Users will be able to navigate the program through several option menus via the console.

## User Stories
- As a user, I want to be able to add a card to a deck any amount of times.
- As a user, I want to select a specific deck in order to manipulate it.
- As a user, I want to be able to remove any card belonging to a deck.
- As a user, I want to be able to add multiple decks to an instance of the application.
- As a user, I want to be able to save my decks to a file
- As a user, I want to be able to load my saved decks from the file.


## Phase 4: Task 2
Fri Apr 01 11:24:08 PDT 2022
Added Card: card 1 to Deck: Test

Fri Apr 01 11:24:10 PDT 2022
Added Card: card 2 to Deck: Test

Fri Apr 01 11:24:14 PDT 2022
Added Card: card 3 to Deck: Test

Fri Apr 01 11:24:16 PDT 2022
Added Card: card 4 to Deck: Test

Fri Apr 01 11:24:18 PDT 2022
Removed Card: card 4 from Deck: Test

Fri Apr 01 11:24:20 PDT 2022
Removed Card: card 3 from Deck: Test

Fri Apr 01 11:24:21 PDT 2022
Removed Card: card 2 from Deck: Test



## Phase 4: Task 3
- If I had more time, I might've removed the nested classes in the FlashCardUI class and made them
their own separate classes while also maybe having them all extend an abstract class to clean up the code
a little.
- I would've also liked to separate the FlashCardUI class into separate
classes depending on which menu is being displayed to the user. This would reduce the large amount of fields that are
present in the FlashCardUI class and would also increase cohesion.
- Refactoring parts of the Deck class and Card class would also be good as the implementation of some methods are
inconsistent with other similar methods.

