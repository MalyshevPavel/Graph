package com.Studying;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graph {

    private class Edge {
        int v1;
        int v2;

        Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    public int verticesCount;
    public int edgesCount;
    public ArrayList<Integer>[] adjacencyList;
    public Edge[] edgeList;
    private int[][] distances;
    private int[][] next;
    private int[] visitedEdges;
    private ArrayList<Edge> currentEdgePath;
    private ArrayList<Edge> minEdgePath;


    private int getInt(FileReader reader) throws Exception {
        int Int = 0;
        int c;
        ArrayList<Integer> mas = new ArrayList();
        while (true) {
            c = reader.read();
            if (c == -1)
            {
                return -1;
            }
            else {
                if ((c >= '0') && (c <= '9'))
                {
                    break;
                }
                else if (c != ' ' && c!= '\n'){
                    System.out.println("Неверный формат ввода");
                    throw new IOException();
                }
            }
        }
        while ((c >= '0') && (c <= '9')) {
            mas.add(c - '0');
            c = reader.read();
        }
        for (int i = 0; i < mas.size(); i++) {
            Int = Int * 10;
            Int += mas.get(i);
        }
        return Int;
    }

    Graph(String dir) throws Exception{
        FileReader reader = new FileReader(dir);
        verticesCount = getInt(reader);
        edgesCount = getInt(reader);
        adjacencyList = new ArrayList[verticesCount];
        edgeList = new Edge[edgesCount];
        for (int i = 0; i < verticesCount; i++) {
            adjacencyList[i] = new ArrayList();
        }
        int vertex1;
        int vertex2;
        for (int i = 0; i < edgesCount; i++) {
            vertex1 = getInt(reader);
            vertex2 = getInt(reader);
            edgeList[i] = new Edge(vertex1, vertex2);
            adjacencyList[vertex1].add(vertex2);
            adjacencyList[vertex2].add(vertex1);
        }
    }

    private void floyd() {
        distances = new int[verticesCount][verticesCount];
        next = new int[verticesCount][verticesCount];

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                    next[i][j] = j;
                }
                else {
                    distances[i][j] = 1000;
                    next[i][j] = -1;
                }
            }
        }

        for (int i = 0; i < verticesCount; i++) {
            for (Integer j : adjacencyList[i]) {
                distances[i][j] = 1;
                next[i][j] = j;
            }
        }

        for (int k =0; k < verticesCount; k++) {
            for (int i = 0; i < verticesCount; i++) {
                for (int j = 0; j < verticesCount; j++) {
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    private void visitEdge(int v1, int v2) {
        boolean visited = false;
        for (int i = 0; i < edgesCount; i++) {
            if ((edgeList[i].v1 == v1 && edgeList[i].v2 == v2) || (edgeList[i].v1 == v2 && edgeList[i].v2 == v1)) {
                visited = true;
                visitedEdges[i]++;
                currentEdgePath.add(new Edge(v1, v2));
            }
        }
        if (visited == false) {
            System.exit(-1);
        }
    }

    private int goToVertex(int from, int to) {
        int visitedEdgesCount = 0;
        while (from != to) {
            visitEdge(from, next[from][to]);
            from = next[from][to];
            visitedEdgesCount++;
        }
        return visitedEdgesCount;
    }

    private void removeEdgeFromList(int v1, int v2){
        boolean visited = false;
        for (int i = 0; i < edgesCount; i++) {
            if ((edgeList[i].v1 == v1 && edgeList[i].v2 == v2) || (edgeList[i].v1 == v2 && edgeList[i].v2 == v1)) {
                visited = true;
                visitedEdges[i]--;
            }
        }
        if (visited == false) {
            System.exit(-1);
        }
    }

    public int allEdgesShortestPath() {
        visitedEdges = new int[edgesCount];
        currentEdgePath = new ArrayList<Edge>();
        minEdgePath = new ArrayList<Edge>();
        floyd();

        for (int i = 0; i < edgesCount; i++) {
            visitedEdges[i] = 0;
        }

        allEdgesShortestPath(0);

        for (Edge e : minEdgePath) {
            System.out.println(e.v1 + " " + e.v2);
        }
        return minEdgePath.size();
    }

    private void allEdgesShortestPath(int v) {
        boolean notEnd = false;
        for (int i = 0; i < edgesCount; i++) {
            if (visitedEdges[i] == 0) {
                notEnd = true;

                int visitedEdgesCount;

                visitedEdgesCount = goToVertex(v ,edgeList[i].v1);
                visitEdge(edgeList[i].v1, edgeList[i].v2);
                allEdgesShortestPath(edgeList[i].v2);
                for (int k = 0; k < visitedEdgesCount + 1; k++) {
                    removeEdgeFromList(currentEdgePath.get(currentEdgePath.size() - 1).v1, currentEdgePath.get(currentEdgePath.size() - 1).v2);
                    currentEdgePath.remove(currentEdgePath.size() - 1);
                }

                visitedEdgesCount = goToVertex(v, edgeList[i].v2);
                visitEdge(edgeList[i].v2, edgeList[i].v1);
                allEdgesShortestPath(edgeList[i].v1);
                for (int k = 0; k < visitedEdgesCount + 1; k++) {
                    removeEdgeFromList(currentEdgePath.get(currentEdgePath.size() - 1).v1, currentEdgePath.get(currentEdgePath.size() - 1).v2);
                    currentEdgePath.remove(currentEdgePath.size() - 1);
                }
            }
        }

        if (notEnd == false) {
            if (currentEdgePath.size() < minEdgePath.size() || minEdgePath.size() == 0) {
                minEdgePath = new ArrayList<Edge>(currentEdgePath);
            }
        }
    }


}
