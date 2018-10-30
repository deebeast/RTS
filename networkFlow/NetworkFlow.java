package networkFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

import ffa.MatrixGraph;

class Frame {
    // start time and end time of frame
    int start, end, frameSize;
    ArrayList<Task> al;

    Frame(int s, int e) {
        start = s;
        end = e;
        frameSize = e - s;
    }
}

class Task {
    // period and ex3cution time of a task
    Double P, E;

    // c0nstructor
    Task(Double p, Double e) {
        P = p;
        E = e;
    }
}

class taskComparator implements Comparator<Task> {
    @Override
    public int compare(Task d, Task d1) {
        if (Math.ceil(d.P - d1.P) >= 1) {
            return 1;
        } else
            return -1;
    }
}

class NetworkFlow {
    static int globalVertices = 1; // Number of vertices in graph
    static long HyperPeriod = 1;

    /**
     * finding the maximum of all execution time
     * 
     * @param al
     * @return
     */
    private static double findMaxE(ArrayList<Task> al) {
        double maxE = 0;
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).E > maxE)
                maxE = al.get(i).E;
        }
        return maxE;
    }

    /**
     * simple function to calculate greatest common divisor
     * 
     * @param P
     * @param F
     * @return
     */
    static int gcdOf(Double P, Integer F) {
        int a = P.intValue();
        int b = F;
        if (a == 0 || b == 0)
            return 0;// Everything divides 0
        if (a == b)
            return a; // base case
        if (a > b)
            return gcdOf((double) a - b, b); // a is greater
        return gcdOf((double) a, b - a);
    }

    /**
     * printing the schedule
     * 
     * @param al
     */
    private static void printSchedule(ArrayList<Task> al) {

    }

    /**
     * if there exists a path from source to destination in residual graph return 1 and store the path in updateSource
     * matrix
     * 
     * @param residualGraph
     * @param source
     * @param destination
     * @param updateSource
     * @return
     */
    static int pathExist(int residualGraph[][], int source, int destination, int updateSource[]) {
        int visited[] = new int[globalVertices];
        Arrays.fill(visited, 0);
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = 1;
        updateSource[source] = -1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < globalVertices; v++) {
                if (visited[v] == 0 && residualGraph[u][v] > 0) {
                    queue.add(v);
                    updateSource[v] = u;
                    visited[v] = 1;
                }
            }
        }
        return (visited[destination]);
    }

    /**
     * Returns the maximum flow from source to destination in the given graph
     * 
     * @param graph
     * @param source
     * @param destination
     * @return
     */
    static int ffa(int graph[][], int source, int destination) {
        int residualGraph[][] = graph;
        int updateSource[] = new int[globalVertices];
        int maximumFlow = 0;
        int i, j;
        while (pathExist(residualGraph, source, destination, updateSource) == 1) {
            int pathFlow = Integer.MAX_VALUE;
            for (j = destination; j != source; j = updateSource[j]) {
                i = updateSource[j];
                pathFlow = Math.min(pathFlow, residualGraph[i][j]);
            }
            System.out.println("The Matrix Transformations shows how edges are reversed : ");
            for (j = destination; j != source; j = updateSource[j]) {
                i = updateSource[j];
                residualGraph[i][j] -= pathFlow;
                residualGraph[j][i] += pathFlow;
                System.out.println();
                print2D(residualGraph);
                System.out.println();
            }
            maximumFlow += pathFlow;
        }
        return maximumFlow;
    }

    /**
     * printing the whole 2d matrix
     * 
     * @param mat
     */
    public static void print2D(int mat[][]) {
        // Loop through all rows
        for (int[] mat1 : mat) {
            System.out.print("          ");
            for (int j = 0; j < mat1.length; j++) {
                System.out.print(mat1[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Deciding the possible frame size for the graph
     * 
     * @param possFrames
     * @param al
     * @return
     */
    static ArrayList<Integer> frameDecide(ArrayList<Integer> possFrames, ArrayList<Task> al) {
        double maxE = findMaxE(al);
        for (int i = 0; i < possFrames.size() || possFrames.size() >= 2;) {
            if (possFrames.get(i) >= maxE) {
                i++;
            } else {
                System.out.println("Frame size " + possFrames.get(i) + " is less than maxE " + maxE + " Hence removed");
                possFrames.remove(i);
            }
            if (i >= possFrames.size())
                break;
        }
        for (int i = 0; i < possFrames.size();) {
            for (int j = 0; j < al.size(); j++) {
                if ((2 * possFrames.get(i) - gcdOf(al.get(j).P, possFrames.get(i))) <= al.get(j).P) {
                    i++;
                } else {
                    System.out.println("Frame size " + possFrames.get(i) + " doesn't satisfy Third Condition " + maxE
                            + " Hence removed");
                    possFrames.remove(i);
                    i = 0;
                }
            }
        }
        return possFrames;
    }

    /**
     * stating all the possible frame size for the graph
     * 
     * @param HyperPeriod
     * @return
     */
    private static ArrayList<Integer> possFrameSizes(long HyperPeriod) {
        ArrayList<Integer> al = new ArrayList<>();
        for (int i = 1; i <= HyperPeriod; i++) {
            if (HyperPeriod % i == 0)
                al.add(i);
        }
        return al;
    }

    /**
     * calculate max flow required
     * 
     * @param al
     * @return
     */
    static Double maxFlow(ArrayList<Task> al) {
        double maxFlow = 0;
        maxFlow = al.stream().map((curr) -> curr.E * (HyperPeriod / curr.P)).reduce(maxFlow,
                (accumulator, _item) -> accumulator + _item);
        return maxFlow;
    }

    /**
     * to calculate hyperperiod
     * 
     * @param element_array
     * @return
     */
    public static long lcm_of_array_elements(int[] element_array) {
        long lcm_of_array_elements = 1;
        int divisor = 2;
        while (true) {
            int counter = 0;
            boolean divisible = false;
            for (int i = 0; i < element_array.length; i++) {
                // lcm_of_array_elements (n1,n2,... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0)
                    return 0;
                else if (element_array[i] < 0)
                    element_array[i] = element_array[i] * (-1);
                if (element_array[i] == 1)
                    counter++;
                // divide element_array by devisor if complete division i.e. without
                // remainder then replace number with quotient; used for find
                // next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }
            // If divisor able to completely divide any number from array
            // multiply with lcm_of_array_elements and store into lcm_of_array_elements
            // and continue to same divisor
            // for next factor finding. else increment divisor

            if (divisible)
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            else
                divisor++;
            // Check if all element_array is 1 indicate we found all factors and
            // terminate while loop.
            if (counter == element_array.length)
                return lcm_of_array_elements;
        }
    }

    /**
     * to get the periods of all tasks
     * 
     * @param al
     * @return
     */
    static int[] getPeriods(ArrayList<Task> al) {
        int[] periods = new int[al.size()];
        for (int i = 0; i < al.size(); i++) {
            periods[i] = al.get(i).P.intValue();
        }
        return periods;
    }

    /**
     * calculating the utilization of the task system
     * 
     * @param al
     * @return
     */
    static double calculateUt(ArrayList<Task> al) {
        double Ut = 0;
        for (int i = 0; i < al.size(); i++) {
            Ut = Ut + ((al.get(i).E) / (al.get(i).P));
        }
        return Ut;
    }

    /**
     * print task details
     * 
     * @param al
     */
    static void printTask(ArrayList<Task> al) {
        for (int i = 0; i < al.size(); i++) {
            System.out.println("T" + (i + 1) + "(" + al.get(i).P + ", " + al.get(i).E + ")");
        }
    }

    /**
     * The Main function
     * 
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws java.lang.Exception {

        Scanner scan = new Scanner(System.in);

        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("Enter number of periodic tasks : ");
        int numOfTasks = scan.nextInt();

        ArrayList<Task> al = new ArrayList<>();
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("Enter Period & Execution of Tasks as \"P E\"");
        for (int i = 0; i < numOfTasks; i++) {
            double p, e;
            p = scan.nextDouble();
            e = scan.nextDouble();
            al.add(new Task(p, e));
        }

        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The Tasks are ");
        printTask(al);

        System.out.println("The Rate Ascending Sorted Tasks are ");
        al.sort(new taskComparator());
        printTask(al);

        double Ut = calculateUt(al);
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The System utilization " + Ut);
        /**
         * utilization cannot be greater than one
         */
        if (Ut > 1) {
            System.out.println("The System is NOT Schedulable");
            return;
        }

        int[] periods = getPeriods(al);
        HyperPeriod = lcm_of_array_elements(periods);
        double maxflow = maxFlow(al);
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The Max Flow Required is " + maxflow);
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The HyperPeriod is " + HyperPeriod);

        ArrayList<Integer> possFrames = possFrameSizes(HyperPeriod);
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("1) Frame size must be a multiple of HyperPeriodyperperiod i.e." + HyperPeriod);
        System.out.println("2) Frame size must be greater than equal to maxOf(All EXECUTION TASKS)");
        System.out.println("3) The Frame size must satisfy {2f- gcd(Pi - f) <= Di}");
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The Possible Frame Sizes are " + possFrames);
        possFrames = frameDecide(possFrames, al);
        System.out.println("The Possible Frame Sizes are " + possFrames);
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The Selected Frame Size is " + possFrames.get(0));
        System.out.println();

        MatrixGraph m = new MatrixGraph();
        int n = m.numOfVertices;
        globalVertices = n;
        int graph[][] = new int[n][n];

        graph = m.graph;
        // for (int i = 0; i < n; i++) {
        // for (int j = 0; j < n; j++) {
        // graph[i][j] = scan.nextInt();
        // }
        // System.out.println();
        // }
        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The matrixGraph for given system is ");
        print2D(graph);
        System.out
                .println("-------------------------------------------------------------------------------------------");

        double ffaFLOW = ffa(graph, 0, n - 1);
        System.out.println("The maximum possible flow is " + ffaFLOW);
        System.out.println();

        System.out
                .println("-------------------------------------------------------------------------------------------");
        System.out.println("The System is " + ((ffaFLOW < maxflow) ? "NOT " : "") + "schedulable");
        System.out
                .println("-------------------------------------------------------------------------------------------");

        // printSchedule(al);
    }

}
