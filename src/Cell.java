import java.util.ArrayList;
public class Cell {
    private int label;
    private boolean isProtected;
    private ArrayList<Pawn> pawns;

    public Cell(int label) {
        this.label = label;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "label=" + label +
                ", isProtected=" + isProtected +
                '}';
    }

}
