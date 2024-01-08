import java.util.ArrayList;

public class Cell {
    private int label;
    private boolean isProtected;
    private ArrayList<Pawn> pawns = new ArrayList<>();

    public Cell(int label) {
        this.label = label;
        this.isProtected = isLabelProtected(label);
    }

    private boolean isLabelProtected(int label) {
        int[] protectedLabels = {32, 38, 21, 15, 66, 4, 49, 55};
        for (int protectedLabel : protectedLabels) {
            if (label == protectedLabel) {
                return true;
            }
        }
        return false;
    }

    public void addPawnToCell(Pawn pawn){
        pawns.add(pawn);
    }

    public void removePawnFromCell(Pawn pawn){
        pawns.remove(pawn);
    }

    public boolean getProtected() {
        return isProtected;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "label=" + label +
                ", isProtected=" + isProtected +
                '}';
    }
}
