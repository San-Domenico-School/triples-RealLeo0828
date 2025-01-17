import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)

/**
 * Game Board for Triples
 *
 * @author Leo
 * @version (a version number or a date)
 */
public class GameBoard extends World {
    private final int NUM_CARDS_IN_DECK = 27;

    public GameBoard() {
        super(430, 600, 1, false);

        // Test Scorekeeper
        new Tester("Scorekeeper");

        // Test Wobble
        new Tester("Wobble");

        // Test Slide
        new Tester("Slide");
    }
}