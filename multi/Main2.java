import java.util.*;
import java.util.concurrent.*;

class Main2 {
  static boolean PRINT = true;
  static final int N_THREADS = 2;

  public static void main(String[] args) {
    setup(); 
    ThreadPoolExecutor e = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);
    DSatur task = new DSatur();
    ArrayList<Future<?>> results = new ArrayList<Future<?>>();
    // Log starting time here
    for (int i=0; i<N_THREADS; i++) {
      Future<?> f = e.submit(task);
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
    String raw = 
      "0 1\n" + 
      "0 2\n" + 
      "0 3\n" + 
      "1 2\n" + 
      "2 3\n" +
      "2 4\n" + 
      "4 5\n" + 
      "4 7\n" + 
      "5 6\n" + 
      "5 7\n" + 
      "6 7\n";
    String[] edgeList = raw.split("\n");
    int V = 8;
    ArrayList<Integer>[] adjList = new ArrayList[V];
    char[] colorSet = {'R', 'G', 'B', 'Y'};
    for (int i=0; i<V; i++) {
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
        System.out.printf(u+": ");
        for (int v: adjList[u]) {
          System.out.printf("%d ", v);
        } System.out.println();
      }
    }
    Shared shared = new Shared(adjList, colorSet);
  }
}