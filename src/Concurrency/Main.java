package Concurrency;

public class Main {
    public static void main(String[] args) {
        var thread1 = new Thread(new RunnableOne());
        var thread2 = new Thread(new RunnableTwo());

        thread1.start();
        thread2.start();
    }
}
