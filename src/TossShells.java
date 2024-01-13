import java.util.*;

public class TossShells {
    private static final int numShells = 6;
    private static final double probabilityOfShellFacingDown = 0.4;
    private static final  List<Double> tossProbability= new ArrayList<>(List.of(0.004,0.037,0.311,0.276,0.138,0.187,0.047 ));

    public static ArrayList<Integer> tossShells(Player player) {
        ArrayList<Integer> stepsList = new ArrayList<>();
        int tossCount = 0;
        int downShellCount;


        do {
            // tossing
            List<TossOutcome> outcomes = generateOutcomes();
            downShellCount = countShellsFacingDown(outcomes);
//            if(player.getType()=="human"){
            System.out.println("Down shell count: " + downShellCount);
//            }


            // check for khal
            if(downShellCount == 1 || downShellCount == 5)
                stepsList.add(1);

            // count steps for each shell count
            int steps = setStepsForShellCount(downShellCount);
            stepsList.add(steps);

            tossCount++;

        } while (!shouldBreak(downShellCount) && tossCount < 10);

        return stepsList;
    }

    private static int countShellsFacingDown(List<TossOutcome> outcomes) {
        int downShellCount = 0;
        for (TossOutcome outcome : outcomes) {
            if (outcome == TossOutcome.FACING_DOWN) {
                downShellCount++;
            }
        }
        return downShellCount;
    }

    private static boolean shouldBreak(int downShellCount) {
        return downShellCount == 2 || downShellCount == 3 || downShellCount == 4;
    }

    private static List<TossOutcome> generateOutcomes() {
        List<TossOutcome> outcomes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numShells; i++) {
            outcomes.add((random.nextDouble() < probabilityOfShellFacingDown) ? TossOutcome.FACING_DOWN : TossOutcome.FACING_UP);
        }

        return outcomes;
    }

    private static int setStepsForShellCount(int downShellCount) {
        int steps;


        switch (downShellCount) {
            case 0:  // shakka
                steps = 6;
                break;
            case 1: // dust
                steps = 10;
                break;
            case 2:
                steps = 2;
                break;
            case 3:
                steps = 3;
                break;
            case 4:
                steps = 4;
                break;
            case 5: // banj
                steps = 25;
                break;
            case 6:  // bara
                steps = 12;
                break;
            default:
                // Handle any unexpected values of shellCount
                throw new IllegalArgumentException("Invalid shell count: " + downShellCount);
        }
        return steps;
    }
    public static ArrayList<Integer> getTossSteps(int downShellCount) {
        ArrayList<Integer> steps = new ArrayList<>();


        switch (downShellCount) {
            case 0:  // shakka
                steps.add(6);
                break;
            case 1: // dust
                steps.add(10);
                steps.add(1);
                break;
            case 2:
                steps.add(2);
                break;
            case 3:
                steps.add(3);
                break;
            case 4:
                steps.add(4);
                break;
            case 5: // banj
                steps.add(25);
                steps.add(1);
                break;
            case 6:  // bara
                steps.add(12);
                break;
            default:
                // Handle any unexpected values of shellCount
                throw new IllegalArgumentException("Invalid shell count: " + downShellCount);
        }
        return steps;
    }

    public final static List<Double> getTossProbability() {
        return tossProbability;
    }

    // Enum for improved readability
    private enum TossOutcome {
        FACING_UP,
        FACING_DOWN,
    }

}