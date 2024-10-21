
import java.util.*;

public class ListCorruptionExample {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();

      Runnable add=()->{
          for(int i=0;i<100;i++){
              list.add(i);
          }
      };
      Runnable remove=()->{
          for(int i=0;i<100;i++){
              if(!list.isEmpty()) {
                  list.remove(90);
              }
          }
      };
        Thread t1=new Thread(add);
        Thread t2=new Thread(remove);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

      System.out.println("list :" +list);
      System.out.println("Final size of this list : "+ list.size());

    }
}

