import java.util.ArrayList;

public class SharedPathsExample {
    public static void main(String[] args) {
        // Create two ArrayLists of ArrayLists
        ArrayList<ArrayList<Integer>> arrayList1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> arrayList2 = new ArrayList<>();

        // Initialize each inner ArrayList with numbering
        for (int i = 1; i <= 68; i++) {
            arrayList1.add(createNumberedArrayList(i, 5));
            arrayList2.add(createNumberedArrayList(i, 5));
        }

        // Print the content of the ArrayLists
        printArrayLists(arrayList1, arrayList2);
    }

    // Create an ArrayList with specified numbering and number of elements
    private static ArrayList<Integer> createNumberedArrayList(int number, int numElements) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(number);
        for (int i = 1; i < numElements; i++) {
            arrayList.add(0);
        }
        return arrayList;
    }

    // Print the content of the ArrayLists
    private static void printArrayLists(ArrayList<ArrayList<Integer>> arrayList1, ArrayList<ArrayList<Integer>> arrayList2) {
        for (int i = 0; i < arrayList1.size(); i++) {
            System.out.println("ArrayList1[" + i + "]: " + arrayList1.get(i));
            System.out.println("ArrayList2[" + i + "]: " + arrayList2.get(i));
        }
    }
}
