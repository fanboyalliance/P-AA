package astar;

import java.util.ArrayList;

public class Graph {
    private Vertex[] vertexArr;
    private double[][] adjMat;
    private int totalVerts;

    private Vertex startVertex;
    private Vertex endVertex;
    private int startVertexIndex;
    private int endVertexIndex;

    public Graph(int initSize) {
        vertexArr = new Vertex[initSize];
        adjMat = new double[initSize][initSize];
        totalVerts = 0;
        for (int i = 0; i < initSize; i++) {
            for (int j = 0; j < initSize; j++) {
                adjMat[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    private void defineStartEndIndexes() {
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i].getLabel().equals(startVertex.getLabel())) {
                startVertexIndex = i;
                startVertex.setIndex(vertexArr[startVertexIndex].getIndex());
            }
        }
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i].getLabel().equals(endVertex.getLabel())) {
                endVertexIndex = i;
                endVertex.setIndex(vertexArr[endVertexIndex].getIndex());
            }
        }
    }

    private Vertex getMinVert(ArrayList<Vertex> openSet) {
        double min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < openSet.size(); i++) {
            if (openSet.get(i).getF() <= min) {
                min = openSet.get(i).getF();
                index = openSet.get(i).getIndex();
            }
        }
        return vertexArr[index];
    }

    private void removeFromList(ArrayList<Vertex> openSet, Vertex v) {
        for (int i = 0; i < openSet.size(); i++) {
            if (openSet.get(i).getIndex() == v.getIndex()) {
                openSet.remove(openSet.get(i));
                break;
            }
        }
    }

    public void searchAStar() {
        defineStartEndIndexes();
        if (startVertexIndex == endVertexIndex) return;

        ArrayList<Vertex> openList = new ArrayList<>();
        ArrayList<Vertex> closedList = new ArrayList<>();
        vertexArr[startVertexIndex].setH(0);
        vertexArr[startVertexIndex].setG(0);
        vertexArr[startVertexIndex].setF(0);

        openList.add(startVertex);
        while (!openList.isEmpty()) {
            Vertex curVert = getMinVert(openList);
            removeFromList(openList, curVert);
            if (curVert.getIndex() == endVertexIndex) {
                closedList.add(curVert);
                printAnswer(closedList);
                return;
            }
            closedList.add(curVert);
            ArrayList<Vertex> children = getChildren(curVert.getIndex());
            for (Vertex child : children) {
                if (closedList.contains(child)) continue;

                double weightScore = curVert.getG() + adjMat[curVert.getIndex()][child.getIndex()];

                if (openList.contains(child) && weightScore >= child.getG()) continue;

                double heuristicScore;
                if (!openList.contains(child)) {
                    heuristicScore = Math.abs(endVertex.getH() - child.getH());
                    child.setH(heuristicScore);
                    /*
                    if (endVertex.getH() < 0) {
                        if (child.getH() < 0) {
                            // lower priority
                            heuristicScore = Math.abs(endVertex.getH() - child.getH());
                        } else {
                            heuristicScore = Math.abs(endVertex.getH() + child.getH());
                        }
                    } else if (child.getH() < 0) {
                        if (endVertex.getH() < 0) {
                            // lower priority
                            heuristicScore = Math.abs(endVertex.getH() - child.getH());
                        } else {
                            heuristicScore = Math.abs(endVertex.getH() + child.getH());
                        }
                    } else {
                        heuristicScore = Math.abs(endVertex.getH() + child.getH());
                    }
                     */
                }

                child.setParent(curVert);
                child.setG(weightScore);
                child.setF(weightScore + child.getH());

                if (openList.contains(child)) {
                    for (int j = 0; j < openList.size(); j++) {
                        if (openList.get(j).getIndex() == child.getIndex()) {
                            openList.set(j, child);
                            break;
                        }
                    }
                } else {
                    openList.add(child);
                }
            }
        }
    }

    private void printAnswer(ArrayList<Vertex> closedSet) {
        Vertex lastVertex = closedSet.get(closedSet.size() - 1);
        ArrayList<String> VertexesAnswer = new ArrayList<>();
        VertexesAnswer.add(lastVertex.getLabel());
        while (lastVertex.getParent() != null) {
            VertexesAnswer.add(lastVertex.getParent().getLabel());
            lastVertex = lastVertex.getParent();
        }

        for (int i = VertexesAnswer.size() - 1; i >= 0; i--) {
            System.out.print(VertexesAnswer.get(i) + " ");
        }
        System.out.println();
    }

    private ArrayList<Vertex> getChildren(int index) {
        ArrayList<Vertex> children = new ArrayList<>();
        for (int i = 0; i < totalVerts; i++) {
            if (adjMat[index][i] != Integer.MAX_VALUE) {
                children.add(vertexArr[i]);
            }
        }
        return children;
    }

    public void addVertex(String label) {
        vertexArr[totalVerts] = new Vertex(label);
        vertexArr[totalVerts].setIndex(totalVerts);
        totalVerts++;
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

    public void displayAj() {
        for (int i = 0; i < vertexArr.length; i++) {
            System.out.print(vertexArr[i].getLabel() + "    ");
        }
        System.out.println();
        for (int i = 0; i < adjMat.length; i++) {
            for (int j = 0; j < adjMat[i].length; j++) {
                if (j == 0) {
                    System.out.print(vertexArr[i].getLabel() + " ");
                }
                if (adjMat[i][j] == Integer.MAX_VALUE) {
                    System.out.print("-1   ");
                } else {
                    System.out.print(adjMat[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }
}
