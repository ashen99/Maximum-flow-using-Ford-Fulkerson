import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//referece sources : https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
                     //https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
                     //https://www.letscodethemup.com/graph-representation-adjacency-matrix/

public class Main {
    static int startN = 0;
    int numOfVertices;        //this is to hold number of vertices in the graph
    int[][] graph;            //this is to hold the input graph
    public ArrayList<int []> graphList = new ArrayList<>(); //holds the int[] arrays we created when reading the text file
    public static ArrayList<String> Apath = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);



    public static void main(String[] args) throws FileNotFoundException {
        Main ff = new Main();
        ff.inputGraph(ff.menu());
        ff.createGraph();
        ff.display(ff);
    }



    public void display(Main ff){      //this method will display infor into the console
        System.out.println("------------- GRAPH VIEW -------------");
        for (int i=0; i < numOfVertices; i++){
            for (int j=0; j < numOfVertices; j++){
                System.out.print(ff.graph[i][j]);
            }
            System.out.println();
        }


        long start = System.nanoTime();            //the start time
        int maxF = ff.fordFulkersonAlgo(ff.graph,0, ff.numOfVertices-1);
        long end = System.nanoTime();              //the end time

        System.out.println("");
        System.out.println("max flow is "+ maxF);
        System.out.println("Execution Time: " + ((end - start) / 1000000.0) + "ms");
        System.out.println("");

    }


    public String menu() {
        String file = " ";
        home:
        while (true) {
            System.out.println("=========================");
            System.out.println("1 : Load bridge 1 ");
            System.out.println("2 : Load bridge 2 ");
            System.out.println("3 : Load bridge 3 ");
            System.out.println("4 : Load bridge 4 ");
            System.out.println("5 : Load bridge 5 ");
            System.out.println("6 : Load bridge 6 ");
            System.out.println("7 : Load bridge 7 ");
            System.out.println("8 : Load bridge 8 ");
            System.out.println("9 : Load bridge 9 ");
            System.out.println("10 : Load ladder 1 ");
            System.out.println("11 : Load ladder 2 ");
            System.out.println("12 : Load ladder 3 ");
            System.out.println("13 : Load ladder 4 ");
            System.out.println("14 : Load ladder 5 ");
            System.out.println("15 : Load ladder 6 ");
            System.out.println("16 : Load ladder 7 ");
            System.out.println("17 : Load ladder 8 ");
            System.out.println("18 : Load ladder 9 ");
            System.out.println("=========================");
            System.out.print("select option: ");
            int option = sc.nextInt();              // Ask user choice
            System.out.println("=========================");

            switch (option) {
                case (1):
                    file = "Networks/bridge_1.txt";
                    break home;
                case (2):
                    file = "Networks/bridge_2.txt";
                    break home;
                case (3):
                    file = "Networks/bridge_3.txt";
                    break home;
                case (4):
                    file = "Networks/bridge_4.txt";
                    break home;
                case (5):
                    file = "Networks/bridge_5.txt";
                    break home;
                case (6):
                    file = "Networks/bridge_6.txt";
                    break home;
                case (7):
                    file = "Networks/bridge_7.txt";
                    break home;
                case (8):
                    file = "Networks/bridge_8.txt";
                    break home;
                case (9):
                    file = "Networks/bridge_9.txt";
                    break home;
                case (10):
                    file = "Networks/ladder_1.txt";
                    break home;
                case (11):
                    file = "Networks/ladder_2.txt";
                    break home;
                case (12):
                    file = "Networks/ladder_3.txt";
                    break home;
                case (13):
                    file = "Networks/ladder_4.txt";
                    break home;
                case (14):
                    file = "Networks/ladder_5.txt";
                    break home;
                case (15):
                    file = "Networks/ladder_6.txt";
                    break home;
                case (16):
                    file = "Networks/ladder_7.txt";
                    break home;
                case (17):
                    file = "Networks/ladder_8.txt";
                    break home;
                case (18):
                    file = "Networks/ladder_9.txt";
                    break home;
                default:
                    System.out.println("Invalid input. please try again!!!");
                    continue;
            }
        }
        return file;
    }


    public void inputGraph(String file){   //this method reads the number of nodes and ggraph from a text file and assin it to a 2D array
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);

            numOfVertices = Integer.parseInt(br.readLine().trim());
            System.out.println("number of vertices : "+numOfVertices);
            String line;
            while((line = br.readLine()) != null) {
                String[] data= line.split(" ");

                int arr[] = new int[3];
                String s = data[0];
                String e = data[1];
                String c = data[2];



                arr[0] = Integer.parseInt(s);
                arr[1] = Integer.parseInt(e);
                arr[2] = Integer.parseInt(c);

                graphList.add(arr);                 /// add values to array list
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } ;
    }

    public void createGraph(){       //this will create a 2d array
        graph = new int[numOfVertices][numOfVertices];
        for (int[] element: graphList){
            graph[element[0]][element[1]] = element[2];
        }
    }

    //this is the method that implements bredth first search to travel through the graph
    //source is the source node
    //sink is the sink node
    public boolean breadthFirstSearch(int source, int sink, int[][] residualGraph, int[] residualpath){

        //boolean array to check if the node is visited or not
        boolean[] visited = new boolean[numOfVertices];

        //marking all nodes as not visited
        for (int i=0; i < numOfVertices ; i++){
            visited[i] = false;
        }

        //creating a queue to add a node when visited
        LinkedList<Integer> queue = new LinkedList<Integer>();

        queue.add(source);      //adding source node to the queue
        visited[source] = true; //making source node as visited
        residualpath[source] = -1;

        //travelling through the graph
        while (queue.size()!=0){
            int q = queue.poll();
            for (int j = 0; j < numOfVertices; j++){
                if (!visited[j] && residualGraph[q][j] > 0){
                    queue.add(j);    //adding a node to the queue if its not visited
                    residualpath[j] = q;
                    visited[j] = true; //making the visited node as true
                }
            }
        }
        return (visited[sink]); //if the sink is reached, true is returned and will return true untill bfs can find a path
    }



    //ford-fulkerson algorithm that will return the maxium flow
    public int fordFulkersonAlgo(int[][] graph, int s, int t) {
        int x;
        int y;
        int max_Flow = 0;  //initializing the max flow as 0

        //creates a 2 dimensional array to represent a  residual graph
        //if the residualraph[x][y]=0 that means there is no edge between 2 nodes
        int[][] residualGraph = new int[numOfVertices][numOfVertices];

        //an array to store the path by bfs
        int[] residualPath = new int[numOfVertices];

        //putting residual graph values to a graph given as a parameter
        for (x=0; x < numOfVertices ; x++){
            for (y=0; y < numOfVertices ; y++){
                residualGraph[x][y] = graph[x][y];
            }
        }

        //augmenting the path from souce node to sink node
        while (breadthFirstSearch(s,t,residualGraph,residualPath)){

            //finding the bottleneck capacity of a arumenting path
            int bottleNeckValue = Integer.MAX_VALUE;
            for (y=t; y !=0; y = residualPath[y]){
                x = residualPath[y];
                bottleNeckValue = Math.min(bottleNeckValue , residualGraph[x][y]);
            }

            //updating the residual capacities each of the edges and reversing edges
            for (y = t ; y != 0; y = residualPath[y]){
                x=residualPath[y];
                residualGraph[x][y] -= bottleNeckValue;
                residualGraph[y][x] += bottleNeckValue;
                Apath.add(x+"-->"+y+",");
            }
            max_Flow += bottleNeckValue;  //adding bottleneck value to maxflow if there is an argumenting path
        }
        return max_Flow;
    }
}
