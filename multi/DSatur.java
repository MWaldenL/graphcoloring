import java.util.*;
import java.util.concurrent.*;

class DSatur implements Callable<Boolean> {
  private Semaphore turnstile = new Semaphore(1);
  private Object pendingLock = new Object();
  private Object colorLock = new Object();
  private Object jobLock = new Object();

  // Rules: 
  // only one person can color
  // everyone waits when someone is coloring
  @Override
  public Boolean call() {
    int V = Shared.adjList.length;
    boolean finished = false, someoneIsColoring = false;
    int satList[];

    while (!finished) {
      synchronized(jobLock) {
        finished = Shared.nodeList.size() == 0;
        if (!finished && someoneIsColoring) { // if there are no nodes being colored
          Shared.pending++;
        }
      } 
      if (finished) break;

      if (someoneIsColoring) {
        turnstileLock(); // wait here
        turnstileUnlock(); // the last guy has finished coloring and informed us
        synchronized(pendingLock) { 
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
        satList[u] = !Shared.visited[u] ? sat(u) : 0;
      }
      int node = getNextNode(satList);
      synchronized(colorLock) {
        Shared.visited[node] = true;
        for (char color: Shared.colorSet) {
          if (isValidColoring(node, color)) {
            Shared.color[node] = color;
            someoneIsColoring = false;
            break;
          }
        }
        Shared.nodeList.remove(Integer.valueOf(node));
        if (Shared.pending > 0) {
          turnstileUnlock(); // open the turnstile 
          turnstileLock(); 
          turnstileUnlock(); // this guy is done coloring, open the turnstile for the next guy
        }
      }
    }
    return true;
  }

  private void turnstileLock() {
    try {
      turnstile.acquire();
    } catch(InterruptedException e){}
  }

  private void turnstileUnlock() {
    turnstile.release();
  }

  private boolean isValidColoring(int node, char color) {
    for (int neighbor: Shared.adjList[node])
      if (Shared.color[neighbor] == color)
        return false;
    return true;
  }

  private int sat(int u) { // heuristic: compute for saturation
    Set<Character> unique = new HashSet<Character>(); 
    for (int v: Shared.adjList[u]) {
      unique.add(Shared.color[v]);
    }
    return unique.size();
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
}