package org.wikispeedia.roadrage2;

import java.util.concurrent.*;
import android.util.Log;

public class ConcurrentLinkedQueueExample {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
    }

}


// the producer puts strings on the queue
class Producer implements Runnable {

    ConcurrentLinkedQueue<String> queue;
    Producer(ConcurrentLinkedQueue<String> queue){
        this.queue = queue;
    }
    public void run() {
        Log.d("TAGG","Producer Started");
        try {
            for (int i = 1; i < 100; i++) {
                queue.add("String" + i);
                Log.d("TAGG","Added: String" + i);
                //Thread.currentThread().sleep(200);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

// the consumer removes strings from the queue
class Consumer implements Runnable {

    ConcurrentLinkedQueue<String> queue;
    Consumer(ConcurrentLinkedQueue<String> queue){
        this.queue = queue;
    }
    public void run() {
        String str;
        Log.d("TAGG","Consumer Started");
        for (int x = 0; x < 10000; x++) {
            while ((str = queue.poll()) != null) {
                Log.d("TAGG", "Removed: " + str);
            }
            try {
                Thread.currentThread().sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

