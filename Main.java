import java.util.*;

class Main {
  public static void main(String[] args) {
    String raw = 
      "0 1\n" + 
      "0 3\n" + 
      "1 2\n" + 
      "1 4\n" +
      "2 5\n" + 
      "3 4\n" + 
      "3 6\n" + 
      "4 5\n" + 
      "4 7\n" + 
      "5 8\n" + 
      "6 7\n" + 
      "7 8\n";
    String[] edgeList = raw.split("\n");
    int V = 8;
    ArrayList<Integer>[] adjList = new ArrayList[V+1];
    char[] colors = {'R', 'G', 'B', 'Y'};
    for (int i=0; i<=V; i++) {
      adjList[i] = new ArrayList<Integer>();
    }
    for (String edge: edgeList) { // form adjList
      String[] vals = edge.split(" ");
      int u = Integer.parseInt(vals[0]);
      int v = Integer.parseInt(vals[1]);
      adjList[u].add(v);
      adjList[v].add(u);
    }
    System.out.println("Graph: ");
    for (int u=0; u<=V; u++) {
      System.out.printf(u+": ");
      for (int v: adjList[u]) {
        System.out.printf("%d ", v);
      } System.out.println();
    }
    Vanilla gcHelper = new Vanilla(adjList, colors);
    gcHelper.run();
  }
}