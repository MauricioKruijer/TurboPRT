package turboprt;


import java.util.Stack;

public class GraphDriver {
    
    public int[] calculateRouteFromIntersection(int beginningIntersection,int DestinationIntersection)
    {
        int GRAPH_SIZE = 14;
        int[] distances;
        int[] links;
        AdjacencyMatrix myGraph;
        int v = beginningIntersection;

            myGraph = new AdjacencyMatrix(GRAPH_SIZE, true);

            int[][][] connections = {
                /*  Intersection 0  */{{1, 10}, {4, 5}, {5, 10}},//Intersect0 connection {{from 0 to 1 cost 5},{from 0 to 4 cost 10}},
                /*  Intersection 1  */ {{0, 10}, {2, 12}, {6, 10}}, //Intersect1 connections  {{from  1 to 0 cost 1}, {from 1 to 2 cost 1}}
                /*  Intersection 2  */ {{1, 12}, {3, 8}, {7, 12}},
                /*  Intersection 3  */ {{2, 8}, {8, 14}},
                /*  Intersection 4  */ {{0, 5}, {5, 6}},
                /*  Intersection 5  */ {{0, 10}, {4, 6}, {6, 10}, {9, 10}},
                /*  Intersection 6  */ {{1, 10}, {5, 10}, {7, 10}},
                /*  Intersection 7  */ {{2, 12}, {6, 10}, {8, 12}, {10, 12}},
                /*  Intersection 8  */ {{3, 14}, {7, 12}, {13, 16}},
                /*  Intersection 9  */ {{5, 10}, {10, 16}},
                /*  Intersection 10 */ {{7, 12}, {9, 16}, {11, 10}, {12, 10}},
                /*  Intersection 11 */ {{10, 10}, {13, 8}},
                /*  Intersection 12 */ {{10, 10}, {13, 16}},
                /*  Intersection 13 */ {{8, 16}, {11, 8}, {12, 16}}
            };

            for (int i = 0; i < connections.length; i++) {
                for (int j = 0; j < connections[i].length; j++) {
                    
                    myGraph.addAdjacency(i, connections[i][j][0], connections[i][j][1]);
                    System.out.println("myGraph.addAdjacency(" + i + ", " + connections[i][j][0] + ", " + connections[i][j][1] + ")");

                }
            }
            myGraph.solver(v);
            distances = myGraph.getDistances();

            System.out.println("Distance list for node " + v);

            for (int i = 0; i < distances.length; i++) {

                if (distances[i] == Integer.MAX_VALUE || distances[i]
                        == -Integer.MAX_VALUE + 1) {
                    System.out.print("Inf ");
                } else {
                    System.out.print(distances[i] + " ");
                }

            }

            System.out.println();

                int j = DestinationIntersection;
                System.out.println("Optimal node path from " + v + " to " + j);

                if (distances[j] == Integer.MAX_VALUE || distances[j]
                        == -Integer.MAX_VALUE + 1) {
                    System.out.println("No path exists.");
                    return new int[] {beginningIntersection};
                }

                links = myGraph.getLinks();
                Stack<Integer> stack = new Stack<Integer>();
                int current_vertex = j;

                while (current_vertex != v) {
                    stack.push(current_vertex);
                    current_vertex = links[current_vertex];
                }
                stack.push(v);
                int teller = 0;
                int[] defroute = new int[stack.size()];
        
                while (!stack.isEmpty()) {
                    int pop = stack.pop();
                    
                        defroute[teller] = pop;
                        teller++;
                    
                    System.out.println("teller " + teller);
                    
                }
                return defroute;
    }
}
