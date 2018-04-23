package com.Studying;

import static org.junit.Assert.*;

public class GraphTest {
    @org.junit.Test
    public void allEdgesShortestPath() throws Exception {
        int expected1 = 13;
        int expected2 = 5;
        //Graph graph1 = new Graph("TGraph1.txt");
        Graph graph2 = new Graph("TGraph2.txt");
        //assertEquals(expected1, graph1.allEdgesShortestPath());
        assertEquals(expected2, graph2.allEdgesShortestPath());
    }

}