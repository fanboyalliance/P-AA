package astar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Runner {

    public void start() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int counter = 0;
        ArrayList<String> allVertices = new ArrayList<>();
        String startVertex = null;
        String endVertex = null;
        ArrayList<Double> weights = new ArrayList<>();
        HashSet<String> notRepeatVertex = new HashSet<>();
        while ((line = input.readLine()) != null && !line.trim().equals("") && line.length() > 0) {
            String[] inits = line.split(" ");
            String start = inits[0];
            String end = inits[1];
            if (counter != 0) {
                double weight = Double.parseDouble(inits[2]);
                allVertices.add(start);
                allVertices.add(end);
                weights.add(weight);
                notRepeatVertex.add(start);
                notRepeatVertex.add(end);
            } else {
                startVertex = start;
                endVertex = end;
            }
            counter++;
        }
        if (counter == 0) return;

        Graph graph = new Graph(notRepeatVertex.size());
        for (String s : notRepeatVertex) {
            graph.addVertex(s);
        }
        graph.initStartVertex(startVertex);
        graph.initEndVertex(endVertex);

        for (int i = 0, j = 0; i < weights.size(); i++, j++) {
            graph.addEdge(allVertices.get(j), allVertices.get(j + 1), weights.get(i));
            j += 1;
        }

        graph.searchAStar();
    }

    public static void main(String[] args) throws IOException {
        new Runner().start();
    }
}
