package com.Studying;

import static org.junit.Assert.*;

public class GraphTest {
    @org.junit.Test
    public void allEdgesShortestPath() throws Exception {
        int expected = 13;
        Graph graph = new Graph("TGraph1.txt");
        assertEquals(expected, graph.allEdgesShortestPath());
    }

}