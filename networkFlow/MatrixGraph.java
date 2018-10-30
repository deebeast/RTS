/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkFlow;

/**
 *
 * @author deepak
 */
// 0 3 3 3 1 1 0 0 0 0
// 0 0 0 0 0 0 4 0 0 0
// 0 0 0 0 0 0 0 4 0 0
// 0 0 0 0 0 0 0 0 4 0
// 0 0 0 0 0 0 4 0 0 0
// 0 0 0 0 0 0 0 0 4 0
// 0 0 0 0 0 0 0 0 0 4
// 0 0 0 0 0 0 0 0 0 4
// 0 0 0 0 0 0 0 0 0 4
// 0 0 0 0 0 0 0 0 0 0

public class MatrixGraph {
    public int numOfVertices = 10; // 1(source) + 3(jobs of Task1) + 2(jobs of Task2) + 3(hyperperiod / frame size )i.e.
                                   // 12/4 + 1(destination)
    public int graph[][] = { { 0, 3, 3, 3, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 4, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 4, 0 }, { 0, 0, 0, 0, 0, 0, 4, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 4, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
    public int numOfVertices1 = 4;
    public int graph1[][] = { { 0, 20, 10, 0 }, { 0, 0, 30, 10 }, { 0, 0, 0, 20 }, { 0, 0, 0, 0 } };
    public int numOfVertices2 = 4;
    public int graph2[][] = { { 0, 100, 100, 0 }, { 0, 0, 1, 100 }, { 0, 0, 0, 100 }, { 0, 0, 0, 0 } };

}
