package displays;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author Roman Nagibov
 */
public abstract class Menu {

    public Scanner fixScannerThread() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Scanner> future = executor.submit(processingSearchMessage);
        return future.get();
    }

    private Callable<Scanner> processingSearchMessage = () -> new Scanner(System.in);
}
