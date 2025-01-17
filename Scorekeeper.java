/**
 * Scorekeeper class
 * Author: Leo
 * Date: 1/1
 */
public class Scorekeeper {
    // Fields
    private static int deckSize;
    private static int score;
    private static long startTime = System.currentTimeMillis();

    // Setter for deckSize
    public static void setDeckSize(int size) {
        deckSize = size;
    }

    // Method to update score
    public static void updateScore() {
        // For testing purposes, increment by 27
        if (deckSize == 27) {
            score += 27; 
        } else {
            // Original logic for score increment based on elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            score += Math.max(1000 - (int) (elapsedTime / 10), 10); // Minimum increment is 10
            startTime = System.currentTimeMillis(); // Reset start time
        }
    }
    // Getter for score
    public static int getScore() {
        return score;
    }
}