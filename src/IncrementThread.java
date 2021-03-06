import org.omg.CORBA.INTERNAL;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;

/**
 * Created by Вова on 29.10.2017.
 */
public class IncrementThread implements Runnable {

    private BakeryLock lock;
    private MyInteger counter;
    private  CyclicBarrier barrier;
    private int a;



    public IncrementThread(MyInteger counter,BakeryLock lock, CyclicBarrier barrier, int a){
        this.lock = lock;
        this.counter = counter;
        this.barrier = barrier;
        this.a = a;
    }

    @Override
    public void run() {

        lock.register();

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }


        while(true){

            lock.lock();
            counter.plus(a);
            lock.unlock();
        }
        //lock.unregister();
    }


}
