import java.util.*;

public class GraphColoring {
  public ArrayList<Integer>[] adjList;
  public char[] colorSet, color;
  public boolean[] visited;
  protected int V;

  public GraphColoring(ArrayList<Integer>[] adjList, char[] colorSet) {
    this.colorSet = colorSet;
    this.adjList = adjList;
    this.V = adjList.length;
    color = new char[V];
    visited = new boolean[V];
    for (int i=0; i<V; i++) {
      visited[i] = false;
    }
  }

  protected boolean isValidColoring(int node, char col) {
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
    for (char c: color) {
      System.out.printf("%c ", c);
    } System.out.println();
  }
}