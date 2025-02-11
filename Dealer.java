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
        this.cardsOnBoard = new ArrayList<>();
        this.selectedCardsIndex = new ArrayList<>();
        this.cardsSelected = new Card[3];
    }
    
    public void addedToWorld(World world) {
        dealBoard();
        setUI();
    }
    
        public void dealBoard() {
        Greenfoot.playSound("shuffle.wav"); // Play shuffle sound
    
        // Place 15 cards in a 5x3 grid.
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                if (deck.getNumCardsInDeck() > 0) {
                    Card card = deck.getTopCard();
                    cardsOnBoard.add(card);
                    World currentWorld = getWorld();
                    if (currentWorld != null) {
                        // Adjust the y-coordinate from 100 to 80 to move cards upward by 20 pixels.
                        currentWorld.addObject(card, 100 + col * 120, 50 + row * 80);
                    }
                }
            }
        }
    }

    
    public void setUI() {
        World currentWorld = getWorld();
        if (currentWorld != null) {
            int cardsRemaining = deck.getNumCardsInDeck();
            int score = Scorekeeper.getScore();
            currentWorld.showText("Cards Remaining: " + cardsRemaining, 230, 470);
            currentWorld.showText("Score: " + score, 270, 500);
        }
    }
    
    public void endGame() {
        World currentWorld = getWorld();
        if (currentWorld != null) {
            currentWorld.showText("Game Over!", 250, 300);
            Greenfoot.stop();
        }
    }
    
    public void checkIfTriple() {
        if (selectedCardsIndex.size() == 3) {
            Card c1 = cardsSelected[0];
            Card c2 = cardsSelected[1];
            Card c3 = cardsSelected[2];
            if (isTriple(c1, c2, c3)) {
                actionIfTriple();
            }
        }
    }
    
    public void actionIfTriple() {
        World currentWorld = getWorld();
        if (currentWorld == null)
            return;
        
        // Remove the triple from the board
        for (Card card : cardsSelected) {
            currentWorld.removeObject(card);
            cardsOnBoard.remove(card);
        }
        
        // Update the score and UI
        Scorekeeper.updateScore();
        setUI();
        
        // Replace removed cards with new ones
        for (int i = 0; i < 3; i++) {
            if (deck.getNumCardsInDeck() > 0) {
                Card newCard = deck.getTopCard();
                cardsOnBoard.add(newCard);
                currentWorld.addObject(newCard, cardsSelected[i].getX(), cardsSelected[i].getY());
            }
        }
        
        selectedCardsIndex.clear();
    }
    
    private boolean isTriple(Card c1, Card c2, Card c3) {
        return allSameOrDifferent(c1.getShape(), c2.getShape(), c3.getShape()) &&
               allSameOrDifferent(c1.getColor(), c2.getColor(), c3.getColor()) &&
               allSameOrDifferent(c1.getNumberOfShapes(), c2.getNumberOfShapes(), c3.getNumberOfShapes()) &&
               allSameOrDifferent(c1.getShading(), c2.getShading(), c3.getShading());
    }
    
    private boolean allSameOrDifferent(Object a, Object b, Object c) {
        if (a == null || b == null || c == null) {
            return false;
        }
        return (a.equals(b) && b.equals(c)) || (!a.equals(b) && !b.equals(c) && !a.equals(c));
    }
    
    // Updated setCardsSelected method with correct parameter types:
    public void setCardsSelected(ArrayList<Card> boardCards, ArrayList<Integer> selectedIndexes, Card[] selectedCards) {
        this.cardsOnBoard = boardCards;
        this.selectedCardsIndex = selectedIndexes;
        this.cardsSelected = selectedCards;
    }
}
