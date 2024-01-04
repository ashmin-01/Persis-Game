import java.util.Random;

public class BinomialCoinTossSimulator {

    public static void main(String[] args) {
        int numTosses = 6;
        double probabilityOfHeads = 0.6; // Customize this based on your desired probability
        int[] headCountarr = new int[100];
        for (int j = 0; j < 100; j++) {
            int[] coinResults = simulateBinomialCoinToss(numTosses, probabilityOfHeads);
            int headCount = 0;
            // Display the results
            for (int i = 0; i < 6; i++) {
                if(coinResults[i]==1)
                    headCount++;
            }
            headCountarr[j]= headCount;
            System.out.println("Results of tossing 6 coins for the " + j + "th time is : " + headCount);
        }
        int countFour= 0 , countThree= 0, countTwo= 0, countFive= 0, countSix= 0, countOne= 0, countZero = 0;
        for(int i = 0; i < 100; i++){
            if(headCountarr[i]==0)
                countZero++;
            else if(headCountarr[i]==1)
                countOne++;
            else if(headCountarr[i]==2)
                countTwo++;
            else if(headCountarr[i]==3)
                countThree++;
            else if(headCountarr[i]==4)
                countFour++;
            else if(headCountarr[i]==5)
                countFive++;
            else
                countSix++;
        }
        System.out.println("The number of zero heads in a 6 coin toss for 100 times is : " + countZero);
        System.out.println("The number of one heads in a 6 coin toss for 100 times is : " + countOne);
        System.out.println("The number of two heads in a 6 coin toss for 100 times is : " + countTwo);
        System.out.println("The number of three heads in a 6 coin toss for 100 times is : " + countThree);
        System.out.println("The number of four heads in a 6 coin toss for 100 times is : " + countFour);
        System.out.println("The number of five heads in a 6 coin toss for 100 times is : " + countFive);
        System.out.println("The number of six heads in a 6 coin toss for 100 times is : " + countSix);
    }

    public static int[] simulateBinomialCoinToss(int numTosses, double probabilityOfHeads) {
        int[] outcomes = new int[numTosses];
        Random random = new Random();

        for (int i = 0; i < numTosses; i++) {
            outcomes[i] = generateBinomialOutcome(probabilityOfHeads, random);
        }

        return outcomes;
    }

    private static int generateBinomialOutcome(double probabilityOfHeads, Random random) {
        double randomValue = random.nextDouble(); // Generate a random value between 0 and 1

        // Determine the outcome based on the binomial distribution
        return (randomValue < probabilityOfHeads) ? 1 : 0; // 1 for heads, 0 for tails
    }
}
