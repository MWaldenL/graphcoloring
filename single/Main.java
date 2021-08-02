import java.util.*;

class Main {
  static boolean PRINT = true;
  public static void main(String[] args) {
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
    char[] colors = {'R', 'G', 'B', 'Y'};
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
    DSatur gcHelper = new DSatur(adjList, colors);
    gcHelper.run();
  }
}