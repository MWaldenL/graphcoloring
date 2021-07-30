import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

class Main {
  static boolean PRINT = true;
  static final int N_THREADS = 2;

  public static void main(String[] args) {
    setup(); 
    // ThreadPoolExecutor e = (ThreadPoolExecutor)Executors.newFixedThreadPool(N_THREADS);
    // DSatur task = new DSatur();
    // Future<?> f = e.submit(task);
    // e.shutdown();
    // try {
    //   f.get();
    //   System.out.println(Shared.color);
    // } catch(Exception ex) {}
  }

  private static void setup() {
    int V = 10, E = 20;
    ArrayList<Edge> edgeList = new GraphGenerator().generate(V, E);
    ArrayList<Integer>[] adjList = new ArrayList[V+1];
    char[] colorSet = {'R', 'G', 'B', 'Y'};
    for (int i=0; i<=V; i++) {
      adjList[i] = new ArrayList<Integer>();
    }
    for (Edge edge: edgeList) { // form adjList
      int u = edge.u, v = edge.v;
      adjList[u].add(v);
      adjList[v].add(u);
    }
    if (PRINT) {
      System.out.println("Graph: ");
      for (int u=0; u<=V; u++) {
        System.out.printf(u+": ");
        for (int v: adjList[u]) {
          System.out.printf("%d ", v);
        } System.out.println();
      }
    }
    // Shared shared = new Shared(adjList, colorSet);
  }
}