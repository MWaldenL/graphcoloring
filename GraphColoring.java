import java.util.*;

public class GraphColoring {
  public ArrayList<Integer>[] adjList;
  public char[] colorSet, color;
  public boolean[] visited;

  public GraphColoring(ArrayList<Integer>[] adjList, char[] colorSet) {
    this.colorSet = colorSet;
    this.adjList = adjList;
    color = new char[adjList.length];
    visited = new boolean[adjList.length];
    for (int i=0; i<adjList.length; i++) {
      visited[i] = false;
    }
  }

  public boolean isValidColoring(int node, char col) {
    for (int neighbor: adjList[node])
      if (color[neighbor] == col)
        return false;
    return true;
  }
}