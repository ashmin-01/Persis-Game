import java.util.ArrayList;

class Path {
    public static ArrayList<Cell> createHumanPath() {
        ArrayList<Cell> humanPath = new ArrayList<>();

        // Add the first 7 elements labeled as -1
        for (int i = 0; i < 7; i++) {
            humanPath.add(new Cell(-1));
        }

        // Add cells labeled from 1 to 68
        for (int i = 1; i <= 68; i++) {
            humanPath.add(new Cell(i));
        }

        // Add the same cell labeled 1
        humanPath.add(humanPath.get(7));

        // Add another 7 elements labeled as 0
        for (int i = 0; i < 7; i++) {
            humanPath.add(new Cell(0));
        }

        return humanPath;
    }

    public static ArrayList<Cell> createComputerPath(ArrayList<Cell> humanPath) {
        ArrayList<Cell> computerPath = new ArrayList<>();

        // Add the first 7 elements labeled as -1
        for (int i = 0; i < 7; i++) {
            computerPath.add(new Cell(-1));
        }
        // Copy cells to computerPath with different arrangement
        computerPath.addAll(humanPath.subList(41, 75));
        computerPath.addAll(humanPath.subList(7, 41));

        // Add the same cell labeled 35
        computerPath.add(computerPath.get(7));

        // Add another 7 elements labeled as 0
        for (int i = 0; i < 7; i++) {
            computerPath.add(new Cell(0));
        }

        return computerPath;
    }
}