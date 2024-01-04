public class CustomProbabilityCoinTossSimulator {

    public static void main(String[] args) {
        int numTrials = 6;
        double probabilityOfHeads = 0.5; // Customize this based on your desired probability
        // Specify the probabilities for each outcome
        for (int i = 0; i <= numTrials; i++) {
            double probability = calculateBinomialProbability(numTrials, i, probabilityOfHeads);
            probability*=100;
            System.out.println("Exactly " + i + " head(s): " + probability);
        }
    }

    public static double calculateBinomialProbability(int numTrials, int numSuccesses, double probabilityOfSuccess) {
        // Calculate binomial coefficient
        long binomialCoefficient = calculateBinomialCoefficient(numTrials, numSuccesses);

        // Calculate probability using binomial coefficient and success/failure probabilities
        return binomialCoefficient * Math.pow(probabilityOfSuccess, numSuccesses) * Math.pow(1 - probabilityOfSuccess, numTrials - numSuccesses);
    }

    public static long calculateBinomialCoefficient(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }

        if (k == 0 || k == n) {
            return 1;
        }

        // Calculate binomial coefficient using Pascal's triangle recurrence relation
        long[][] dp = new long[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }

        return dp[n][k];
    }
}
