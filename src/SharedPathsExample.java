import java.util.ArrayList;

public class SharedPathsExample {
    public static void main(String[] args) {
        // Create humanPath
        ArrayList<Cell> humanPath = createHumanPath();

        // Mark specific cells as protected in humanPath
        markCellsAsProtected(humanPath, 32, 38, 21, 15, 66, 4, 49, 55);
        // create computerPath
        ArrayList<Cell> computerPath = createComputerPath(humanPath);

        // Print the content of humanPath and computerPath
        printPath(humanPath, "Human Path");
        printPath(computerPath, "Computer Path");
    }

    private static ArrayList<Cell> createHumanPath() {
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
    private static ArrayList<Cell> createComputerPath(ArrayList<Cell> humanPath) {
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

    private static void markCellsAsProtected(ArrayList<Cell> path, int... labels) {
        for (int label : labels) {
            Cell cell = path.get(label - 1);
            cell.setProtected(true);
        }
    }

    private static void printPath(ArrayList<Cell> path, String pathName) {
        for (int i = 0; i < path.size(); i++) {
            System.out.println(pathName + "[" + i + "]: " + path.get(i));
        }
    }
}


