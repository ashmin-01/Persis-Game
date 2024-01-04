import java.io.*;
import java.util.*;

class Board {
    private final int numRows;
    private final  int numCols;
    private final char[][] board;
    public static final char SAFE_ZONE_CELL = 'X';
    public static final char EMPTY_CELL = '_';
    public static final char BORDER_CELL = '#';


    // constructors
    // the initialization of the board
    public Board(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        numRows = lines.size();
        numCols = lines.get(0).length();
        board = new char[numRows][numCols];


        for (int i = 0; i < numRows; i++) {
            String row = lines.get(i);
            for (int j = 0; j < numCols; j++) {
                board[i][j] = row.charAt(j);
            }
        }
    }
    // Print the board
    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + "  ");
            }
            System.out.println();
        }
    }

    public void printRotatedBoard() {
        // Counter Variable
        int counter = 0;

        while (counter < 2 * numRows - 1) {
            // Print leading spaces
            for (int i = 0; i < Math.abs(numRows - counter - 1); i++) {
                System.out.print(" ");
            }

            Vector<Character> diagonalElements = new Vector<>();

            // Iterate over columns
            for (int col = 0; col < numCols; col++) {
                // Iterate over rows
                for (int row = 0; row < numRows; row++) {
                    // Check if the element is on the diagonal
                    if (col + row == counter) {
                        diagonalElements.add(board[row][col]);
                    }
                }
            }

            // Print reversed diagonal elements
            for (int i = diagonalElements.size() - 1; i >= 0; i--) {
                System.out.print(diagonalElements.get(i) + " ");
            }

            System.out.println();
            counter += 1;
        }
    }


    public static void main(String[] args) throws IOException {
        Board board = new Board("src/Board.txt");
        System.out.println("Original Board:");
        board.printBoard();

        System.out.println("\nRotated Board (45 degrees):");
        board.printRotatedBoard();
        Player player = new Player("human");
        int result;
        int numOfTrials = 0;
        do {
            result = player.Toss();
            numOfTrials++;
        } while (result != 1 && result != 5);

        System.out.println("got " + result + " in " + numOfTrials + " tries!");
    }
}
