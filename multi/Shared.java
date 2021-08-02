import java.util.*;
import java.util.concurrent.*;

public class Shared {
  public static ArrayList<Integer> adjList[], nodeList;
  public static char[] colorSet, color;
  public static boolean someoneIsColoring, visited[];
  public static int C, pending;
  private int V;

  // Concurrency objects
  public static Semaphore turnstile = new Semaphore(1);
  public static Object pendingLock = new Object();
  public static Object colorLock = new Object();
  public static Object jobLock = new Object();

  public Shared(ArrayList<Integer>[] adjList, char[] colorSet) {
    this.V = adjList.length;
    Shared.colorSet = colorSet;
    Shared.adjList = adjList;
    Shared.color = new char[V];
    Shared.visited = new boolean[V];
    Shared.nodeList = new ArrayList<Integer>();
    Shared.someoneIsColoring = false;
    Shared.pending = 0;

    for (int i=0; i<V; i++) {
      nodeList.add(i);
    }

    // initial state: color node with highest degree and remove it
    int u0 = getStartingNode();
    Shared.visited[u0] = true;
    Shared.color[u0] = Shared.colorSet[0];
    Shared.nodeList.remove(u0);
  }

  private int getStartingNode() { // get the node with highest degree
    int ans = -1; 
    for (int u=0; u<V; u++) {
      int degree = Shared.adjList[u].size();
      ans = Math.max(ans, degree);
    }    
    return ans;
  }
}