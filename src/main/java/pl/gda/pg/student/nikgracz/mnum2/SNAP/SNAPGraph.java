package pl.gda.pg.student.nikgracz.mnum2.SNAP;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class representing Graph from SNAP website.
 */
public class SNAPGraph {

    private Set<Pair<Integer, Integer>> graph;
    private int nodes;

    public SNAPGraph(File file) throws IOException {

        graph = new LinkedHashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf('#') == -1) {
                    String[] nodes = line.split("\t");
                    int nodeFrom = Integer.valueOf(nodes[0]);
                    int nodeTo = Integer.valueOf(nodes[1]);

                    if (nodeFrom >= this.nodes) {
                        this.nodes = nodeFrom + 1;
                    }

                    if (nodeTo >= this.nodes) {
                        this.nodes = nodeTo + 1;
                    }

                    graph.add(new Pair<>(nodeFrom, nodeTo));
                }
            }
        }
    }

    public SNAPGraph(int nodes, Set<Pair<Integer, Integer>> graph) {
        this.nodes = nodes;
        this.graph = graph;
    }

    public Set<Pair<Integer, Integer>> getGraph() {
        return graph;
    }

    public void setGraph(Set<Pair<Integer, Integer>> graph) {
        this.graph = graph;
    }

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SNAPGraph) {
            SNAPGraph other = (SNAPGraph) o;
            return nodes == other.nodes && graph.equals(other.graph);
        }
        return false;
    }
}
