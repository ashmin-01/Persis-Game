import java.util.ArrayList;

class Path {
    public static ArrayList<Cell> createFirstPath() {
        ArrayList<Cell> firstPath = new ArrayList<>();

        // Create the first 7 cells labeled as -1
        for (int i = 0; i < 7; i++) {
            firstPath.add(new Cell(-1));
        }

        // Create cells labeled from 1 to 68
        for (int i = 1; i <= 68; i++) {
            firstPath.add(new Cell(i));
        }

        // Add the cell labeled 1 again
        firstPath.add(firstPath.get(7));

        // Add same first 7 cells to the end of the path
        firstPath.addAll(firstPath.subList(0, 7));

//        // Create another 7 cells labeled as 0
//        for (int i = 0; i < 7; i++) {
//            firstPath.add(new Cell(0));
//        }


        return firstPath;
    }

    public static ArrayList<Cell> createSecondPath(ArrayList<Cell> firstPath) {
        ArrayList<Cell> secondPath = new ArrayList<>();

        // Create the first 7 cells labeled as 0
        for (int i = 0; i < 7; i++) {
            secondPath.add(new Cell(0));
        }

        // Copy cells from firstPath with different arrangement
        secondPath.addAll(firstPath.subList(41, 75));
        secondPath.addAll(firstPath.subList(7, 42));

        // Add same first 7 cells to the end of the path
        secondPath.addAll(secondPath.subList(0, 7));


//        // Create another 7 cells labeled as 0
//        for (int i = 0; i < 7; i++) {
//            secondPath.add(new Cell(0));
//        }

        return secondPath;
    }
}