import java.util.*;


class GraphGenerator {
  public static String getEdgeList(int v, int e) {
    ArrayList<String> edgeList = generate(v, e);
    StringBuilder sb = new StringBuilder();
    for (String edge: edgeList) {
      sb.append(edge);
    }
    return sb.toString();
  }

  private static ArrayList<String> generate(int v, int e) {
    ArrayList<String> edges = new ArrayList<String>();
    ArrayList<Boolean> selection = new ArrayList<Boolean>();
    for (int i=0; i < v; i++) {
      for (int j=i+1; j < v; j++) {
        selection.add(edges.size() < e);
        String edge = String.format("%d %d\n", i, j);
        edges.add(edge);
      }
    }
    for (int i=0; i < selection.size(); i++) {
      int target = getRandom(i, edges.size());
      Boolean cur = selection.get(i), next = selection.get(target);
      selection.set(i, next);
      selection.set(target, cur);
    }
    ArrayList<String> res = new ArrayList<String>();
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
