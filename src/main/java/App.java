import displays.ViewDisplay;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Roman Nagibov
 */
public class App {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
      new ViewDisplay().viewResult();

    }

}
