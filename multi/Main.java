import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class Main {
  static boolean PRINT = true;
  static final int N_THREADS = 2;

  public static void main(String[] args) {
    setup(); 
    ThreadPoolExecutor e = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);
    DSatur task = new DSatur();
    ArrayList<Future<?>> results = new ArrayList<Future<?>>();
    // Log starting time here
    for (int i=0; i<N_THREADS; i++) {
      Future<?> f = e.submit(task); // each thread will run dsatur
      results.add(f);
    }
    e.shutdown();
    for (int i=0; i<N_THREADS; i++) {
      Future<?> res = results.get(i);
      try {
        res.get();
      } catch(Exception exc) {}
    }
    // Log ending time here 
    System.out.println(Shared.color);
  }

  private static void setup() {
    int V = 10;
    String raw = getEdgeListFromFile();
    String[] edgeList = raw.split("\n");
    ArrayList<Integer>[] adjList = new ArrayList[V];
    char[] colorSet = {'R', 'G', 'B', 'Y'};
    for (int i=0; i < V; i++) {
      adjList[i] = new ArrayList<Integer>();
    }
    for (String edge: edgeList) { // form adjList
      String[] vals = edge.split(" ");
      int u = Integer.parseInt(vals[0]);
      int v = Integer.parseInt(vals[1]);
      adjList[u].add(v);
      adjList[v].add(u);
    }
    if (PRINT) {
      System.out.println("Graph: ");
      for (int u=0; u<V; u++) {
        System.out.println(u+": "+adjList[u]);
      }
    }
    Shared shared = new Shared(adjList, colorSet);
  }
  private static String getEdgeListFromFile() {
    File file = new File("test.txt");
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null) {
        sb.append(String.format("%s\n", st));  
      }
      br.close();
    } catch(Exception e) {}
    return sb.toString();
  }
}