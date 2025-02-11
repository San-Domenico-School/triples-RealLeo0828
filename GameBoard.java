import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)

public class GameBoard extends World {
    // Choose either 27 or 81 cards (27 makes the game easier)
    private final int NUM_CARDS_IN_DECK = 27;  

    public GameBoard() {    
        super(430, 600, 1, false);
        prepareScene();
        
        // If you use Tester for debugging, you can enable these lines.
        // new Tester("Scorekeeper");
        // new Tester("Wobble");
        // new Tester("Slide");
        // new Tester(NUM_CARDS_IN_DECK);
    }
    
    public void prepareScene() {
        Dealer dealer = new Dealer(NUM_CARDS_IN_DECK);
        addObject(dealer, -30, -30);
        
        // Create a Player passing the dealer and add it off-screen.
        Player player = new Player(dealer);
        addObject(player, -30, -30);
    }

}
