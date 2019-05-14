package FordFulkerson;

public class Edge {
    private Vertex srcVert;
    private Vertex destVert;
    private int capacity;
    private int flow;

    public Edge(Vertex srcVert, Vertex destVert, int capacity) {
        this.srcVert = srcVert;
        this.destVert = destVert;
        this.capacity = capacity;
        this.flow = 0;
    }

    public Vertex to() {
        return destVert;
    }

    public int getFlow() {
        return flow;
    }

    public Vertex other(Vertex v) {
        if (v.getIndex() == srcVert.getIndex()) {
            return destVert;
        } else if (v.getIndex() == destVert.getIndex()) {
            return srcVert;
        }
        else throw new IllegalArgumentException();
    }

    public int residualCapacityTo(Vertex v)
    {
        if (v.getIndex() == srcVert.getIndex()) return flow;
        else if (v.getIndex() == destVert.getIndex()) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualFlowTo(Vertex v, int delta)
    {
        if (v.getIndex() == srcVert.getIndex()) flow -= delta;
        else if (v.getIndex() == destVert.getIndex()) flow += delta;
    }

}
