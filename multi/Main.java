import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {
    int V = 10;
    String raw = getEdgeListFromFile();
    String[] edgeList = raw.split("\n");
    ArrayList<Integer>[] adjList = new ArrayList[V];
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
    System.out.println("Graph: ");
    for (int u=0; u<V; u++) {
      System.out.println(u+": "+adjList[u]);
    }
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
