import java.io.*;
import java.util.*;
import java.util.concurrent.*;


public class RQ1 {
    private static final int N_THREADS = 16;

    public static void main(String[] args) {
        setup();
        System.out.println("THREADS: " + N_THREADS);
        ThreadPoolExecutor e = (ThreadPoolExecutor)Executors.newFixedThreadPool(N_THREADS);
        ArrayList<Future<?>> results = new ArrayList<Future<?>>();
        DSatur task = new DSatur();

        long t0 = System.currentTimeMillis();
        // Log starting time here
        for (int i=0; i<N_THREADS; i++) {
            Future<?> f = e.submit(task); // each thread will run dsatur
            results.add(f);
        }
        e.shutdown();
        for (int i=0; i<N_THREADS; i++) {
            Future<?> res = results.get(i);
            try {
            res.get();
            } catch(Exception exc) {}
        }
        // Log ending time here 
        long t1 = System.currentTimeMillis();
        System.out.println("TIME: " + (t1-t0));
        
    }

    private static void setup() {
        int V = 15;
        int e = (V*(V-1))/2;
        String graph = (new GraphGenerator()).getEdgeList(V, e);
        String[] edgeList = graph.split("\n");
        ArrayList<Integer>[] adjList = new ArrayList[V];
        char[] colorSet = {'R', 'G', 'B', 'Y'};
        for (int i=0; i < V; i++) {
          adjList[i] = new ArrayList<Integer>();
        }
        for (String edge: edgeList) { // form adjList
          String[] vals = edge.split(" ");
          int u = Integer.parseInt(vals[0]);
          int v = Integer.parseInt(vals[1]);
          adjList[u].add(v);
          adjList[v].add(u);
        }

        System.out.println("Graph: ");
        for (int u=0; u<V; u++) {
        System.out.println(u+": "+adjList[u]);

        Shared shared = new Shared(adjList, colorSet);
      }
    }
}
