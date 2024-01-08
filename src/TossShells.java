import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TossShells {
    private static final int numShells = 6;
    private static final double probabilityOfShellFacingDown = 0.6;

    public static ArrayList<TossResult> tossShells() {
        ArrayList<TossResult> tossResults = new ArrayList<>();
        int tossCount = 0;
        int upShellCount;

        do {
            List<TossOutcome> outcomes = generateOutcomes();
            upShellCount = countShellsFacingUp(outcomes);
            tossResults.add(new TossResult(upShellCount));
            tossCount++;

        } while (!shouldBreak(upShellCount) && tossCount < 10);

        return tossResults;
    }

    private static int countShellsFacingUp(List<TossOutcome> outcomes) {
        int upShellCount = 0;
        for (TossOutcome outcome : outcomes) {
            if (outcome == TossOutcome.FACING_UP) {
                upShellCount++;
            }
        }
        return upShellCount;
    }

    private static boolean shouldBreak(int upShellCount) {
        return upShellCount == 2 || upShellCount == 3 || upShellCount == 4;
    }

    private static List<TossOutcome> generateOutcomes() {
        List<TossOutcome> outcomes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numShells; i++) {
            outcomes.add((random.nextDouble() < probabilityOfShellFacingDown) ? TossOutcome.FACING_DOWN : TossOutcome.FACING_UP);
        }

        return outcomes;
    }

    // Enum for improved readability
    private enum TossOutcome {
        FACING_UP,
        FACING_DOWN,
    }
}
