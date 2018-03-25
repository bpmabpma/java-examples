package tw.bill;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService service = Executors.newCachedThreadPool();
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.execute(() -> {
            System.out.println("execute demo");
        });
        Future<String> future = service.submit(() -> {
            return "future demo";
        });
        System.out.println(future.get());
        service.shutdown();
    }
}
