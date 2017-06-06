import views.MenuTemplate;
import views.ViewMenu;

/**
 * Created by Roman Nagibov
 */
public class App {

    private static ViewMenu menu = ViewMenu.getInstance();

    public static void main(String[] args) {
        System.out.println(MenuTemplate.GREETING);
        menu.getMainMenu();
    }

}
