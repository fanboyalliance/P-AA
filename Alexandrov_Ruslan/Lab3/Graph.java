package FordFulkerson;

import java.util.*;

public class Graph {
    private Vertex[] vertexArr;
    private LinkedList<Edge>[] adjList;
    private int totalVerts;
    private Vertex startVertex;
    private Vertex endVertex;
    private int startVertexIndex;
    private int endVertexIndex;
    private int maxFlow;
    private Edge[] edgeTo;
    public Graph(int initSize) {
        vertexArr = new Vertex[initSize];
        totalVerts = 0;
        adjList = new LinkedList[initSize];
        for (int i = 0; i < initSize; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    private boolean searchBFS(Vertex src, Vertex dest) {
        edgeTo = new Edge[totalVerts];
        boolean[] marked = new boolean[totalVerts];
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(src);
        marked[src.getIndex()] = true;
        while (!queue.isEmpty()) {
            Vertex curV = queue.poll();
            for (Edge e : getEdge(curV)) {
                Vertex destCurv = e.other(curV);
                if (!marked[destCurv.getIndex()] && (e.residualCapacityTo(destCurv) > 0)) {
                    edgeTo[destCurv.getIndex()] = e;
                    marked[destCurv.getIndex()] = true;
                    queue.add(destCurv);
                }
            }
        }
        return marked[dest.getIndex()];
    }

    public void getMaxFlow() {
        defineStartEndIndexes();
        int flow = 0;
        startVertex = vertexArr[startVertexIndex];
        endVertex =  vertexArr[endVertexIndex];
        while (searchBFS(startVertex, endVertex)) {
            int pathFlow = Integer.MAX_VALUE;
            for (Vertex v = endVertex; v.getIndex() != startVertex.getIndex(); v = edgeTo[v.getIndex()].other(v)) {
                pathFlow = Math.min(pathFlow, edgeTo[v.getIndex()].residualCapacityTo(v));
            }
            for (Vertex v = endVertex; v.getIndex() != startVertex.getIndex(); v = edgeTo[v.getIndex()].other(v)) {
                edgeTo[v.getIndex()].addResidualFlowTo(v, pathFlow);
            }
            flow += pathFlow;
        }
        maxFlow = flow;
    }


    private void defineStartEndIndexes() {
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

    public void addVertex(String label) {
        vertexArr[totalVerts] = new Vertex(label);
        vertexArr[totalVerts].setIndex(totalVerts);
        totalVerts++;
    }

    public void addEdge(String start, String end, int weight) {
        Vertex startV = getVertexByName(start);
        Vertex endV = getVertexByName(end);
        Edge edge = new Edge(startV, endV, weight);
        adjList[startV.getIndex()].add(edge);
        adjList[endV.getIndex()].add(edge);
    }

    private LinkedList<Edge> getEdge(Vertex v) {
        return adjList[v.getIndex()];
    }

    private Vertex getVertexByName(String label) {
        for (int i = 0; i < totalVerts; i++) {
            if (vertexArr[i].getLabel().equalsIgnoreCase(label)) {
                return vertexArr[i];
            }
        }
        return vertexArr[startVertexIndex];
    }

    public void initStartVertex(String startVertex) {
        this.startVertex = new Vertex(startVertex);
    }

    public void initEndVertex(String endVertex) {
        this.endVertex = new Vertex(endVertex);
        defineStartEndIndexes();
    }

    private class Answer {
        Vertex first;
        Vertex second;
        int flow;
        Answer(Vertex first, Vertex second, int flow) {
            this.first = first;
            this.second = second;
            this.flow = flow;
        }
    }

    public void printGraphFlow() {
        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 0; i < totalVerts; i++) {
            LinkedList<Edge> list = adjList[i];
            for (int j = 0; j < list.size(); j++) {
                if (i == list.get(j).to().getIndex()) continue;
                Answer answer = new Answer(vertexArr[i], list.get(j).to(), list.get(j).getFlow());
                answers.add(answer);
            }
        }

        Collections.sort(answers, Comparator.comparing((Answer o) -> o.first.getLabel()).thenComparing(o -> o.second.getLabel()));
        System.out.println(maxFlow);
        for (int i = 0; i < answers.size(); i++) {
            System.out.println(answers.get(i).first.getLabel() + " " + answers.get(i).second.getLabel() + " " + answers.get(i).flow);
        }
    }
}
