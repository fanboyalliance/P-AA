package greedy;

import java.util.ArrayList;

public class Graph {
    private Vertex[] vertexArr;
    private double[][] adjMat;
    private ArrayList<Vertex> path;
    private int verticesNumber;

    private Vertex startVertex;
    private Vertex endVertex;
    private int startVertexIndex;
    private int endVertexIndex;

    public Graph(int initSize) {
        path = new ArrayList<>();
        vertexArr = new Vertex[initSize];
        adjMat = new double[initSize][initSize];
        verticesNumber = 0;
        for (int i = 0; i < initSize; i++) {
            for (int j = 0; j < initSize; j++) {
                adjMat[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private void defineStartAndEndIndexes() {
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i].getLabel().equals(startVertex.getLabel())) {
                startVertexIndex = i;
            }
        }
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i].getLabel().equals(endVertex.getLabel())) {
                endVertexIndex = i;
            }
        }
    }

    public void getGreedyResult() {
        defineStartAndEndIndexes();
        if (startVertexIndex == endVertexIndex) return;

        path.add(startVertex);

        for (int i = startVertexIndex; i < verticesNumber; ) {
            if (i == endVertexIndex) break;

            double minDistance = Integer.MAX_VALUE;
            int indexMin = startVertexIndex;

            for (int j = 0; j < verticesNumber; j++) {

                if (adjMat[i][j] < minDistance && minDistance != Integer.MAX_VALUE) {
                    minDistance = adjMat[i][j];
                    indexMin = j;
                } else if (adjMat[i][j] != Integer.MAX_VALUE && minDistance == Integer.MAX_VALUE) {
                    minDistance = adjMat[i][j];
                    indexMin = j;
                }
                if (!CheckRow(indexMin)) {
                    if (indexMin != endVertexIndex) {
                        minDistance = Integer.MAX_VALUE;
                        indexMin = startVertexIndex;
                        j++;
                    }
                }
            }

            if (minDistance != Integer.MAX_VALUE) {
                path.add(vertexArr[indexMin]);
                i = indexMin;
            }
        }

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getLabel());
        }

    }

    private boolean CheckRow(int rowIndex) {
        for (int i = 0; i < verticesNumber; i++) {
            if (adjMat[rowIndex][i] != Integer.MAX_VALUE) {
                return true;
            }
        }
        return false;
    }

    public void addVertex(String label) {
        vertexArr[verticesNumber++] = new Vertex(label);
    }

    public void addEdge(String start, String end, double weight) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i].getLabel().equals(start)) {
                startIndex = i;
            }
            if (vertexArr[i].getLabel().equals(end)) {
                endIndex = i;
            }
        }
        adjMat[startIndex][endIndex] = weight;
    }


    public void initStartVertex(String startVertex) {
        this.startVertex = new Vertex(startVertex);
    }

    public void initEndVertex(String endVertex) {
        this.endVertex = new Vertex(endVertex);
    }
}
