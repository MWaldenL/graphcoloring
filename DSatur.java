/**
 * A* DSatur implementation
 */
import java.util.*;

class DSatur extends GraphColoring {

  private ArrayList<Integer> nodeList;

  public DSatur(ArrayList<Integer>[] adjList, char[] colorSet) {
    super(adjList, colorSet);
    nodeList = new ArrayList<Integer>();
  }

  public void run() {
    DSatur();
  }

  private int getStartingNode() {
    int ans = -1;
    for (int u=0; u<adjList.length; u++) {
      int degree = adjList[u].size();
      ans = Math.max(ans, degree);
    }    
    return ans;
  }

  private int sat(int u) {
    Set<Integer> unique = new HashSet<Integer>(); 
    for (int v: adjList[u]) {
      unique.add(color[v]);
    }
    return unique.size();
  }

  private int getNextNode(int[] satList) {
    int max = -1, ans = -1;
    for (int i=0; i < satList.length; i++) {
      max = Math.max(val, satList[i]);
    }
    for (int u=0; u < satList.length; u++) {
      if (satList[u] == max) {
        int degree = adjList[u].size();
        ans = Math.max(ans, degree);
      }
    }
    return ans;
  }

  private void DSatur() {
    int u0 = getStartingNode(); 
    int[] satList;
    color[u0] = 'R'; // can be changed
    nodeList.remove(u0);
    while (nodeList.size() > 0) {
      satList = new int[adjList.size()];
      for (int v=0; v < adjList.length; v++) {
        satList[v] = sat(v);
      }
      int node = getNextNode(satList);
      for (char col: colorSet) {
        if (isValidColoring(nextNode, col)) {
          color[node] = col;
        }
      }
      nodes.remove(node);
    }
  }
}