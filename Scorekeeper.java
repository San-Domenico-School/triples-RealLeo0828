public class Scorekeeper {
    private static int deckSize;
    private static int score;
    private static long startTime = System.currentTimeMillis();
    
    public static void setDeckSize(int size) {
        deckSize = size;
    }
    
    public static void updateScore() {
        if (deckSize == 27) {
            score += 27; 
        } else {
            long elapsedTime = System.currentTimeMillis() - startTime;
            score += Math.max(1000 - (int)(elapsedTime / 10), 10); // Minimum increment is 10.
            startTime = System.currentTimeMillis();
        }
    }
    
    public static int getScore() {
        return score;
    }
}
