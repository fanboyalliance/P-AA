package FordFulkerson;

public class Vertex {
    private String label;
    private int index;

    public Vertex(String label) {
        this.label = label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }
}
