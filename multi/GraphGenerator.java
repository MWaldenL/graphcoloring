import java.util.*;

class GraphGenerator {
  public static ArrayList<Edge> generate(int v, int e) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Boolean> selection = new ArrayList<Boolean>();
    for (int i=0; i < v; i++) {
      for (int j=i+1; j < v; j++) {
        selection.add(edges.size() < e);
        edges.add(new Edge(i, j));
      }
    }
    for (int i=0; i < selection.size(); i++) {
      int target = getRandom(i, edges.size());
      Boolean cur = selection.get(i), next = selection.get(target);
      selection.set(i, next);
      selection.set(target, cur);
    }
    ArrayList<Edge> res = new ArrayList<Edge>();
    for (int i=0; i < edges.size(); i++) {
      if (selection.get(i)) {
        res.add(edges.get(i));
      }
    }
    return res;
  }

  private static int getRandom(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}

