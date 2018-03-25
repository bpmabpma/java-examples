package tw.bill;

import java.util.concurrent.*;

class Data {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

public class FutureDemo {
    private static Future<Data> setup() {
        FutureTask<Data> future = new FutureTask<Data>(() ->{
            Data data = new Data();
            for (int i = 1; i <= 10; i++) {
                System.out.println(i * 10 + "%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            data.setData("done");
            return data;
        });
        new Thread(future).start();
        return future;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Data> futureTask = setup();
        while (!futureTask.isDone());
        System.out.println(futureTask.get().getData());
    }
}

