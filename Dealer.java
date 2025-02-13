import greenfoot.*;
import java.util.ArrayList;

public class Dealer extends Actor {
    private Deck deck;
    private ArrayList<Card> cardsOnBoard;
    private ArrayList<Integer> selectedCardsIndex;
    private Card[] cardsSelected;
    private int numCardsInDeck;
    private int triplesRemaining;

    public Dealer(int numCardsInDeck) {
        this.numCardsInDeck = numCardsInDeck;
        this.triplesRemaining = numCardsInDeck / 3;
        this.deck = new Deck(numCardsInDeck);
        this.cardsOnBoard = new ArrayList<Card>();
        this.selectedCardsIndex = new ArrayList<Integer>();
        this.cardsSelected = new Card[3];
    }

    // Called automatically when the Dealer is added to the world.
    public void addedToWorld(World world) {
        dealBoard();
        setUI();
    }

    // Deals 15 cards in a 5x3 grid (moved upward by adjusting the y-coordinate).
    public void dealBoard() {
        Greenfoot.playSound("shuffle.wav"); // Play shuffle sound
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                if (deck.getNumCardsInDeck() > 0) {
                    Card card = deck.getTopCard();
                    cardsOnBoard.add(card);
                    World currentWorld = getWorld();
                    if (currentWorld != null) {
                        // Original x coordinate unchanged; subtract 20 from the y-coordinate.
                        currentWorld.addObject(card, 100 + col * 120, 80 + row * 80);
                    }
                }
            }
        }
    }

    // Updates the UI with the number of cards remaining and the current score.
    public void setUI() {
        World currentWorld = getWorld();
        if (currentWorld != null) {
            int cardsRemaining = deck.getNumCardsInDeck();
            int score = Scorekeeper.getScore();
            currentWorld.showText("Cards Remaining: " + cardsRemaining, 250, 470);
            currentWorld.showText("Score: " + score, 250, 500);
        }
    }

    // Task 1: Checks if three selected cards form a triple.
    public void checkIfTriple() {
        if (selectedCardsIndex.size() == 3) {
            Card c1 = cardsSelected[0];
            Card c2 = cardsSelected[1];
            Card c3 = cardsSelected[2];
            if (isTriple(c1, c2, c3)) {
                actionIfTriple();
            } else {
                // If not a triple, play the wobble animation to indicate an error.
                Animations.wobble(cardsSelected);
            }
        }
    }

    // Task 2: If a triple is found, animate, remove the cards, deal new cards,
    // update the score and UI, decrement triplesRemaining, and check for game over.
    public void actionIfTriple() {
        World world = getWorld();
        if (world == null) return;
        
        // 1. Store the (x,y) positions of each selected card.
        int[][] positions = new int[3][2];
        for (int i = 0; i < 3; i++) {
            positions[i][0] = cardsSelected[i].getX();
            positions[i][1] = cardsSelected[i].getY();
        }
        
        // 2. Call the slide and turn animation.
        Animations.slideAndTurn(cardsSelected);
        
        // 3. Remove the three cards from the board.
        for (Card card : cardsSelected) {
            world.removeObject(card);
            cardsOnBoard.remove(card);
        }
        
        // 4. Deal new cards at the saved positions.
        for (int i = 0; i < 3; i++) {
            if (deck.getNumCardsInDeck() > 0) {
                Card newCard = deck.getTopCard();
                cardsOnBoard.add(newCard);
                world.addObject(newCard, positions[i][0], positions[i][1]);
            }
        }
        
        // 5. Decrement the number of triples remaining.
        triplesRemaining--;
        
        // 6. Update the score.
        Scorekeeper.updateScore();
        
        // 7. Update the UI.
        setUI();
        
        // 8. Check if the game is over.
        if (triplesRemaining <= 0) {
            endGame();
        }
        
        // Clear the selection indices.
        selectedCardsIndex.clear();
    }

    // Task 3: Ends the game by displaying a message and stopping the simulation.
    public void endGame() {
        World currentWorld = getWorld();
        if (currentWorld != null) {
            currentWorld.showText("Game Over!", 250, 300);
            Greenfoot.stop();
        }
    }

    // Helper method to determine if three cards form a triple.
    private boolean isTriple(Card c1, Card c2, Card c3) {
        return allSameOrDifferent(c1.getShape(), c2.getShape(), c3.getShape()) &&
               allSameOrDifferent(c1.getColor(), c2.getColor(), c3.getColor()) &&
               allSameOrDifferent(c1.getNumberOfShapes(), c2.getNumberOfShapes(), c3.getNumberOfShapes()) &&
               allSameOrDifferent(c1.getShading(), c2.getShading(), c3.getShading());
    }

    // Returns true if the three attributes are either all the same or all different.
    private boolean allSameOrDifferent(Object a, Object b, Object c) {
        if (a == null || b == null || c == null) {
            return false;
        }
        return (a.equals(b) && b.equals(c)) || (!a.equals(b) && !b.equals(c) && !a.equals(c));
    }
    
    // This method is called (from the Player class) to update the selected cards.
    // It takes the current board cards, a list of indices of the selected cards, and
    // the cardsSelected array, then sets the dealer's fields accordingly.
    public void setCardsSelected(ArrayList<Card> boardCards, ArrayList<Integer> selectedIndexes, Card[] selectedArray) {
        // For each of the three selected indices, get the corresponding card.
        for (int i = 0; i < selectedIndexes.size() && i < 3; i++) {
            int index = selectedIndexes.get(i);
            selectedArray[i] = boardCards.get(index);
        }
        this.cardsSelected = selectedArray;
        this.selectedCardsIndex = selectedIndexes;
        this.cardsOnBoard = boardCards;
    }
}