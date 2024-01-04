import java.util.Random;

public class TossShells {
    private final int numTosses = 6;
    private double probabilityOfShellFacingDown = 0.6;
    public int tossShells() {
        int shellCount = 0; // how many shells facing up
        int[] results = simulateBinomialCoinToss(numTosses, probabilityOfShellFacingDown);

        for (int i = 0; i < numTosses; i++) {
            if(results[i]==0)
                shellCount++;
        }
        return shellCount;
    }
    public static int[] simulateBinomialCoinToss(int numTosses, double probabilityOfShellFacingDown) {
        int[] outcomes = new int[numTosses];
        Random random = new Random();

        for (int i = 0; i < numTosses; i++) {
            outcomes[i] = generateBinomialOutcome(probabilityOfShellFacingDown, random);
        }

        return outcomes;
    }
    private static int generateBinomialOutcome(double probabilityOfShellFacingDown, Random random) {
        double randomValue = random.nextDouble(); // Generate a random value between 0 and 1

        // Determine the outcome based on the binomial distribution
        return (randomValue < probabilityOfShellFacingDown) ? 1 : 0; // 1 for down, 0 for up
    }
    // countZero --> 6 steps
    // countOne --> 10 steps + bonus
    // countTwo --> 2 steps
    // countThree --> 3 steps
    // countFour --> 4 steps
    // countFive --> 24 steps + bonus
    // countSix --> 12 steps
}
