import java.util.*;
import java.util.concurrent.*;

class DSaturALT implements Callable<Boolean> {
  // Rules: 
  // only one person can color
  // everyone waits when someone is coloring
  @Override
  public Boolean call() {
    int V = Shared.adjList.length;
    boolean finished = false, someoneIsColoring = false;
    int satList[];

    while (!finished) {
      synchronized(Shared.jobLock) { // mutex
        finished = Shared.nodeList.size() == 0;
        if (finished) break;
        someoneIsColoring = Shared.someoneIsColoring;
        if (!finished && someoneIsColoring) { // if there are no nodes being colored
          Shared.pending++;
        }
      }

      if (someoneIsColoring) {
        // System.out.println("Waiting outside " + Thread.currentThread().getName());
        turnstileLock(); // wait here
        turnstileUnlock(); // the last guy has finished coloring and informed us
        synchronized(Shared.pendingLock) { 
          // System.out.println("Waiting inside pendingLock " + Thread.currentThread().getName());
          Shared.pending--;
          if (Shared.pending == 0) { // last guy locks the turnstile
            turnstileLock();
          }
        }
        continue;
      }
      
      // If no one is coloring  
      satList = new int[V];
      for (int u=0; u < V; u++) {
        satList[u] = !Shared.visited[u] ? Shared.adjList[u].size() : 0;
      }
      int node = getNextNode(satList);
      // System.out.println("Waiting outside colorLock " + Thread.currentThread().getName());
      synchronized(Shared.colorLock) {
        Shared.someoneIsColoring = true;
        Shared.visited[node] = true;
        boolean colorFound = false;
        for (int color: Shared.colorSet) {
          if (isValidColoring(node, color)) {
            Shared.color[node] = color;
            Shared.someoneIsColoring = false;
            colorFound = true;
            break;
          }
        }
        if (!colorFound) { // add a new color
          int end = Shared.colorSet.size() - 1;
          Shared.color[node] = Shared.colorSet.get(end) + 1; // last color+1
          Shared.colorSet.add(Shared.color[node]);
        }
        Shared.nodeList.remove(Integer.valueOf(node));
      }
      if (Shared.pending > 0) {
        turnstileUnlock(); // open the turnstile 
        // System.out.println("Waiting inside color turnstile " + Thread.currentThread().getName());
        turnstileLock(); 
        turnstileUnlock(); // this guy is done coloring, open the turnstile for the next guy
      }
    }
    return true;
  }

  private boolean isValidColoring(int node, int color) {
    for (int neighbor: Shared.adjList[node])
      if (Shared.color[neighbor] == color)
        return false;
    return true;
  }

  private int getNextNode(int[] satList) {
    int max = -1, ans = -1, maxd = -1, degree = -1;
    for (int i=0; i < satList.length; i++) { // get highest saturation
      max = Math.max(max, satList[i]);
    }
    for (int u=0; u < satList.length; u++) { // get node with highest degree
      if (!Shared.visited[u] && satList[u] == max) {
        degree = Shared.adjList[u].size();
        if (degree > maxd) {
          maxd = degree; ans = u;
        }
      }
    }
    return ans;
  }
  
  private void turnstileLock() {
    try {
      Shared.turnstile.acquire();
    } catch(InterruptedException e){}
  }

  private void turnstileUnlock() {
    Shared.turnstile.release();
  }
}