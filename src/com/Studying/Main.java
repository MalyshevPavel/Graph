package com.Studying;

public class Main {

    public static void main(String[] args) throws Exception {
        Graph graph = new Graph("TGraph1.txt");
        System.out.println();
        graph.allEdgesShortestPath();
    }
}
