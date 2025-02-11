import greenfoot.*;
import java.util.ArrayList;

public class Player extends Actor
{
    // Fields
    private Dealer dealer;
    private Card[] cardsSelected;
    private ArrayList<Card> cardsOnBoard;
    private ArrayList<Integer> selectedCardsIndex;
    
    // Constructor
    // Initializes the fields using the provided Dealer
    public Player(Dealer dealer)
    {
        this.dealer = dealer;
        cardsSelected = new Card[3];                // Array for holding 3 selected cards
        cardsOnBoard = new ArrayList<Card>();         // List to hold cards on board (will be set in addedToWorld)
        selectedCardsIndex = new ArrayList<Integer>(); // List to hold indices of selected cards
    }
    
    // Task 4: Implementing addedToWorld
    // This method initializes cardsOnBoard using the world's getObjects method.
    public void addedToWorld(World world)
    {
        // getObjects returns a List, cast it to an ArrayList
        cardsOnBoard = (ArrayList<Card>) world.getObjects(Card.class);
    }
    
    // Task 5: Implementing act method
    public void act()
    {
        // First, check for mouse clicks and update selection status.
        selectCards();
        
        // If exactly 3 cards have been selected, set cardsSelected accordingly.
        boolean threeCardsHaveBeenSelected = setCardsSelected();
        
        // When three cards are selected:
        if(threeCardsHaveBeenSelected)
        {
            // Pass the selected information to the dealer.
            dealer.setCardsSelected(cardsOnBoard, selectedCardsIndex, cardsSelected);
            // Check if the three selected cards form a triple.
            dealer.checkIfTriple();
            // Reset the selection on the board.
            resetCardsSelected();
        }
    }
    
    // Task 6: Implementing selectCards
    // This method iterates through the cards on the board and toggles selection
    // if the user clicks on a card.
    private void selectCards()
    {
        // Loop through every card currently on the board.
        for (int i = 0; i < cardsOnBoard.size(); i++)
        {
            Card card = cardsOnBoard.get(i);
            
            // If the card is clicked:
            if(Greenfoot.mouseClicked(card))
            {
                if(card.getIsSelected())
                {
                    // If already selected, unselect it:
                    card.setIsSelected(false);
                    // Remove its index from the selectedCardsIndex list.
                    // Using Integer.valueOf(i) ensures the object is removed, not by index.
                    selectedCardsIndex.remove(Integer.valueOf(i));
                }
                else
                {
                    // Otherwise, select the card:
                    card.setIsSelected(true);
                    // Add the index of the card to the selectedCardsIndex list.
                    selectedCardsIndex.add(i);
                }
            }
        }
    }
    
    // Task 7: Implementing setCardsSelected
    // If exactly 3 cards are selected, assign them to the cardsSelected array and return true.
    // Otherwise, return false.
    private boolean setCardsSelected()
    {
        if(selectedCardsIndex.size() == 3)
        {
            // For each of the three selected indices, retrieve the card from cardsOnBoard.
            for (int i = 0; i < 3; i++)
            {
                int index = selectedCardsIndex.get(i);
                cardsSelected[i] = cardsOnBoard.get(index);
            }
            return true;
        }
        return false;
    }
    
    // Task 8: Implementing resetCardsSelected
    // Iterates through all the cards on the board to reset their selection status
    // and then clears the list of selected indices.
    private void resetCardsSelected()
    {
        // For each card on the board, set it to unselected.
        for (Card card : cardsOnBoard)
        {
            card.setIsSelected(false);
        }
        // Clear the list of selected card indices.
        selectedCardsIndex.clear();
    }
}