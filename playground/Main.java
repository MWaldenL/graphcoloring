import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.*;


class Main {
  public static void main(String[] args) {
    System.out.println("8 threads");
    ThreadPoolExecutor e = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
    ArrayList<Future<Object>> list = new ArrayList<Future<Object>>();
    Callable<Boolean> task = () -> {
      long x = 1614L;
      for (long i=2; i < x; i++) {
        if (x%i == 0) {
          Shared.prime = false;
          return false;
        }
      }
      return true;
    };

    long t0 = System.currentTimeMillis();
    Future<?> f = e.submit(task);
    e.shutdown();
    try{
      f.get(); 
      System.out.println(Shared.prime);
      long t1 = System.currentTimeMillis();
      System.out.println(t1-t0);
    } catch (Exception ex){}
  }
}

class Shared {
  static volatile int n = 5;
  static volatile boolean prime = true;
}