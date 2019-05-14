package astar;

public class Vertex {
    private String label;
    private int index;
    private Vertex parent;

    private double f;
    private double g;
    private double h;


    public Vertex(String label) {
        this.label = label;
        int number = Integer.parseInt(label);
        if (number >= 0) {
            h = (int) label.charAt(0);
        } else {
            char minus = label.charAt(0);
            int indexMinusInAscii = (int) minus;
            char num = label.charAt(1);
            int indexNumInAscii = (int) num;
            h = indexMinusInAscii + indexNumInAscii;
        }

        /*
        h = Integer.parseInt(label);
         */
    }

    public String getLabel() {
        return label;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getF() {
        return f;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
