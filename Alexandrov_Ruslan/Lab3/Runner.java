package FordFulkerson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Runner {

    public void start() {
        Scanner sc = new Scanner(System.in);
        String line;
        int counter = sc.nextInt();
        ArrayList<String> allVertices = new ArrayList<>();
        String startVertex = sc.next();
        String endVertex = sc.next();
        ArrayList<Integer> weights = new ArrayList<>();
        HashSet<String> notRepeatVertex = new HashSet<>();
        sc.skip("\n");
        for (int i = 0; i < counter; ) {
            line = sc.nextLine();
            String[] inits = line.split(" ");
            String start = inits[0];
            String end = inits[1];
            int weight = Integer.parseInt(inits[2]);
            allVertices.add(start);
            allVertices.add(end);
            weights.add(weight);
            notRepeatVertex.add(start);
            notRepeatVertex.add(end);
            i++;
        }

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

        graph.getMaxFlow();
        graph.printGraphFlow();
    }


    public static void main(String[] args) {
        new Runner().start();
    }
}
