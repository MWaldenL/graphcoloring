/**
 * A* DSatur implementation
 */
import java.util.*;

class DSatur extends GraphColoring {
  private ArrayList<Integer> nodeList;
  private int[] marked;

  public DSatur(ArrayList<Integer>[] adjList, ArrayList<Integer> colorSet) {
    super(adjList, colorSet);
    nodeList = new ArrayList<Integer>();
    for (int i=0; i < adjList.length; i++) {
      nodeList.add(i);
    }
  }

  public void run() {
    dsatur();
    printSolution();
  }

  private int getStartingNode() { // get the node with highest degree
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
    int max = -1, ans = -1, maxd = -1;
    for (int i=0; i < satList.length; i++) { // get highest saturation
      max = Math.max(max, satList[i]);
    }
    for (int u=0; u < satList.length; u++) { // get node with highest degree
      if (!visited[u] && satList[u] == max) {
        int degree = adjList[u].size();
        if (degree > maxd) {
          maxd = degree; ans = u;
        }
      }
    }
    return ans;
  }

  private void dsatur() {
    int u0 = getStartingNode(), satList[]; // get the node with the highest degree
    visited[u0] = true;
    color[u0] = colorSet.get(0); // set initial color
    nodeList.remove(u0);
    while (nodeList.size() > 0) { // if done coloring, stop
      satList = new int[V]; 
      for (int u=0; u < V; u++) {
        satList[u] = !visited[u] ? sat(u) : 0;
      }
      int node = getNextNode(satList);
      visited[node] = true;
      boolean found = false;
      for (int col: colorSet) {
        if (isValidColoring(node, col)) {
          color[node] = col;
          found = true;
          break;
        }
      }
      if (!found) {
        color[node] = colorSet.get(colorSet.size()-1) + 1;
        colorSet.add(color[node]);
        System.out.println("not found, color = " + color[node]);
      }
      nodeList.remove(Integer.valueOf(node));
    }
  }
}
