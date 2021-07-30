import java.util.*;

public class Shared {
  public static ArrayList<Integer> adjList[], nodeList;
  public static char[] colorSet, color;
  public static boolean[] visited;
  public static int V, C, pending;

  public Shared(ArrayList<Integer>[] adjList, char[] colorSet) {
    this.colorSet = colorSet;
    this.adjList = adjList;
    this.V = adjList.length;
    this.C = colorSet.length;
    this.color = new char[V];
    this.visited = new boolean[V];
    this.nodeList = new ArrayList<Integer>();
    this.pending = 0;

    for (int i=0; i < Shared.V; i++) {
      nodeList.add(i);
    }

    // initial state: color node with highest degree and remove it
    int u0 = getStartingNode();
    this.visited[u0] = true;
    this.color[u0] = Shared.colorSet[0];
    this.nodeList.remove(u0);
  }

  private int getStartingNode() { // get the node with highest degree
    int ans = -1; 
    for (int u=0; u<Shared.V; u++) {
      int degree = Shared.adjList[u].size();
      ans = Math.max(ans, degree);
    }    
    return ans;
  }
}