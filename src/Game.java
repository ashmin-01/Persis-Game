import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    Board board ;

    public Game(String player1Type, String player2Type){
        board = new Board(player1Type, player2Type);
    }

    private void humanTurn(Player player){
        System.out.println("\nHuman Turn");
        // Tossing
        ArrayList<TossResult> tossResults = TossShells.tossShells();

        // choosing a toss result until results is empty
        while (!tossResults.isEmpty()) {
            // choose result
            int resultIndex = chooseTossResult(tossResults);
            TossResult result = tossResults.get(resultIndex);

            // choose valid pawn for result
            ArrayList<Integer> validPawns = board.validPawnList(result, player.getPawns());

            // clear toss results since there are no pawns to move
            if (validPawns.isEmpty()){
                System.out.println("There are no pawns you can move.");
                tossResults.clear();
            }
            else {
                // choose pawn
                int pawnIndex = choosePawn(validPawns);
                Pawn p = player.getPawns().get(pawnIndex - 1);
                Pawn.GameStatus pStatus = p.getStatus();


                // check if it can move this particular pawn :
                int steps = tossResults.get(resultIndex).getSteps();
                if(result.getIsKhal() && pStatus == Pawn.GameStatus.OUT_GAME){
                    board.move(player, p, tossResults.get(resultIndex), steps);
                    result.setIsKhal(false);
                }else if (!result.getIsKhal()) {
                    board.move(player, p, tossResults.get(resultIndex), steps);
                    tossResults.remove(resultIndex);
                } else
                {
                    steps = handleKhalCases(p, tossResults,resultIndex);
                    board.move(player, p, tossResults.get(resultIndex), steps);
                }
            }
        }
    }

    public int handleKhalCases(Pawn pawn,ArrayList<TossResult> tossResults, int resultIndex){
        TossResult result = tossResults.get(resultIndex);
        int steps = tossResults.get(resultIndex).getSteps();
        if (result.getIsKhal() && pawn.getStatus() == Pawn.GameStatus.IN_GAME){
            Scanner scanner = new Scanner(System.in);
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("Choose one of the following:\n" +
                        "1. Move the pawn one step only (Khal)\n" +
                        "2. Move the pawn " + result.getSteps() + " steps and keep the Khal\n" +
                        "3. Move the pawn " + (result.getSteps() + 1) + " steps and (use the Khal)\n");
               int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        result.setIsKhal(false);
                        steps = 1;
                        validChoice = true;
                        break;
                    case 2:
                        result.setSteps(0);
                        steps = tossResults.get(resultIndex).getSteps();
                        validChoice = true;
                        break;
                    case 3:
                        result.setIsKhal(false);
                        result.setSteps(0);
                        steps = (tossResults.get(resultIndex).getSteps() + 1);
                        tossResults.remove(resultIndex);
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }

        return steps;
    }

    private int choosePawn(ArrayList<Integer> validPawnList) {
        System.out.print("Choose pawn number: ");
        for(int i : validPawnList){
            System.out.print(i + ", ");
        }
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            try {
                choice = scanner.nextInt();
                if (validPawnList.contains(choice)) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Please enter a valid pawn number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }

    private int chooseTossResult(ArrayList<TossResult> results){
        // print all toss result
        for(int i=0; i< results.size(); i++){
            TossResult result = results.get(i);
            System.out.println("result" + i + ": " + result.toString());
        }

        // Get the result index from the user
        System.out.print("Choose toss result: ");
        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        int choice = -1; // Initialize the choice variable
        boolean valid = false; // Initialize the valid flag
        while (!valid) { // Loop until a valid input is entered
            try {
                choice = scanner.nextInt(); // Read an integer input
                if (choice >= 0 && choice < results.size()) { // Check if the choice is within the range of the results
                    valid = true; // Set the valid flag to true
                } else {
                    System.out.println("Invalid choice. Please enter a number between 0 and " + (results.size() - 1)); // Print an error message
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number."); // Print an error message
                scanner.next(); // Clear the input buffer
            }
        }
        return choice;
    }

    private void computerTurn(Player player){
        System.out.println("Computer Turn");
    }

    private void play(){
        while (true) {
            // show board
            board.printInfo();

            // player1
            humanTurn(board.player1);
            if (board.isWin(board.player1)) {
                System.out.println("Congratulations you won :'(");
                break;
            }
            board.printInfo();

            // player2
            humanTurn(board.player2);
            if (board.isWin(board.player2)) {
                System.out.println("Congratulations you won :'(");
                break;
            }

        }
    }

    public static void main(String[] args) {
        Game game = new Game("human", "human");
        game.play();
    }

}
