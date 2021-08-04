import java.util.*;

public class GraphColoring {
  public ArrayList<Integer>[] adjList;
  public ArrayList<Integer> colorSet;
  public int[] color;
  public boolean[] visited;
  protected int V;

  public GraphColoring(ArrayList<Integer>[] adjList, ArrayList<Integer> colorSet) {
    this.colorSet = colorSet;
    this.adjList = adjList;
    this.V = adjList.length;
    color = new int[V];
    visited = new boolean[V];
    for (int i=0; i<V; i++) {
      visited[i] = false;
    }
  }

  protected boolean isValidColoring(int node, int col) {
    for (int neighbor: adjList[node])
      if (color[neighbor] == col)
        return false;
    return true;
  }

  protected void printSolution() {
    if (color.length == 0) {
      System.out.println("No solution");
      return;
    }
    for (int c: color) {
      System.out.printf("%d ", c);
    } System.out.println();
  }
}