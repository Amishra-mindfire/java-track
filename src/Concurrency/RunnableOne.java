package Concurrency;

public class RunnableOne implements Runnable {

    @Override
    public void run() {
        try {
            for(int i = 0; i <= 10; i++) {
            IO.println("Current runnable one value -> " + i);
            Thread.sleep(1000);
        }
        } catch (InterruptedException e) {
            IO.println("Thread run interrupted");
        } catch(Exception e) {
            IO.println("Exception occured " + e.getMessage());
        }
    }
    
}
