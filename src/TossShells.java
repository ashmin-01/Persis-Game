import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TossShells {
    private final int numTosses = 6;
    private final int maxTosses = 10;
    private final double probabilityOfShellFacingDown = 0.6;
    private List<TossResult> tossResults = new ArrayList<>();
    private Map<Integer, Double> tossProbabilityMap = createTossProbabilityMap();
    private Map<Integer, Integer> stepsMap = createStepsMap();

    public List<TossResult> tossShells() {
        int tossCount = 0; // for number of times allowed to re-toss after getting 1, 5, 6, or 0

        do {
            int[] results = simulateBinomialShellToss(numTosses, probabilityOfShellFacingDown);
            int shellCount = countShellsFacingUp(results);
            double tossProbability = tossProbabilityMap.getOrDefault(shellCount, probabilityOfShellFacingDown);
            int steps = stepsMap.getOrDefault(shellCount, 0);
            tossResults.add(new TossResult(shellCount, tossProbability, steps));
            tossCount++;

        } while (!shouldBreak(tossResults.get(tossResults.size() - 1).getShellCount()) && tossCount < maxTosses);

        return tossResults;
    }

    private int countShellsFacingUp(int[] results) {
        int shellCount = 0;
        for (int result : results) {
            if (result == 1) {
                shellCount++;
            }
        }
        return shellCount;
    }

    private boolean shouldBreak(int shellCount) {
        return shellCount == 2 || shellCount == 3 || shellCount == 4;
    }

    public static int[] simulateBinomialShellToss(int numTosses, double probabilityOfShellFacingDown) {
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
     // for probabilityOfShellFacingDown = 0.5
     /*   map.put(0, 0.015625); // bara , all down , %1.5
        map.put(1, 0.09375); // banj , one up, %9.3
        map.put(2, 0.234375); // deux, two up, %23.43
        map.put(3, 0.3125); // three, three up, %31.25
        map.put(4, 0.234375); // four , four up , %23.43
        map.put(5, 0.09375); // dust , five up , %9.3
        map.put(6, 0.015625); // shakka, all up , %1.5 */
    private Map<Integer, Double> createTossProbabilityMap() {
        Map<Integer, Double> map = new HashMap<>();
        // Assign probabilities for each shell count
        map.put(0, 0.047); // bara , all down , %4.7
        map.put(1, 0.187); // banj , one up, %18.7
        map.put(2, 0.311); // deux, two up, %31.1
        map.put(3, 0.276); // three, three up, %27.6
        map.put(4, 0.138); // four , four up , %13.8
        map.put(5, 0.037); // dust , five up , %3.7
        map.put(6, 0.004); // shakka, all up , %0.4
        return map;
    }

    private Map<Integer, Integer> createStepsMap() {
        Map<Integer, Integer> map = new HashMap<>();
        // Assign steps for each shell count
        map.put(0, 12); // bara
        map.put(1, 25); // banj 24 + bonus , logic should be handled in the move method
        map.put(2, 2); // deux
        map.put(3, 3); // three
        map.put(4, 4); // four
        map.put(5, 11); // dust 10 + bonus
        map.put(6, 6); // shakka
        return map;
    }

    public List<List<Object>> getTossResults() {
        List<List<Object>> resultList = new ArrayList<>();

        // Call tossShells to get the list of TossResult objects
        List<TossResult> tossResults = tossShells();

        // Transform TossResult objects into a list of lists
        for (TossResult tossResult : tossResults) {
            List<Object> resultProperties = new ArrayList<>();
            resultProperties.add(tossResult.getShellCount());
            resultProperties.add(tossResult.getTossProbability());
            resultProperties.add(tossResult.getSteps());
            resultList.add(resultProperties);
        }

        return resultList;
    }
}

class TossResult {
    private int shellCount;
    private double tossProbability;
    private int steps;

    public TossResult(int shellCount, double tossProbability, int steps) {
        this.shellCount = shellCount;
        this.tossProbability = tossProbability;
        this.steps = steps;
    }

    public int getShellCount() {
        return shellCount;
    }

    public double getTossProbability() {
        return tossProbability;
    }

    public int getSteps() {
        return steps;
    }
}


