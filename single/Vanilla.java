/**
 * Vanilla graph coloring with backtracking
 */
import java.util.*;

class Vanilla extends GraphColoring {
  public Vanilla(ArrayList<Integer>[] adjList, char[] colorSet) {
    super(adjList, colorSet);
    color[0] = 'R';
  }

  public void run() {
    dfs(0);
    printSolution();
  }
  
  private void dfs(int curNode) {
    visited[curNode] = true;
    for (int neighbor: adjList[curNode]) { // try all neighbors
      if (visited[neighbor]) continue;
      for (char col: colorSet) { // try all colors
        if (isValidColoring(neighbor, col)) {
          color[neighbor] = col;
          dfs(neighbor);
        }
      }
    }
  }
}
