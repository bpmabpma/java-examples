package tw.bill;

public class GroupDemo {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("group") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.printf("%s: %s%n", t.getName(), e.getMessage());
            }
        };

        Thread thread1 = new Thread(group, () -> {throw new RuntimeException("Exception test");});
        thread1.start();

//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            System.out.printf("global");
//        });

        Thread thread2 = new Thread(() -> {throw new RuntimeException("Exception tes 2");});
        thread2.setUncaughtExceptionHandler((t, e) -> {
            System.out.printf("%s: %s%n", t.getName(), e.getMessage());
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {throw new RuntimeException("Exception tes 3");});
        thread3.start();


    }
}
