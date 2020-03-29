package load;

public class App {
    public static void main(String[] args) {
        Menu menu = new MenuImpl();
        Sender sender = new SenderImpl();
        LoadTester loadTester = new LoadTester(menu, sender);
        loadTester.loadTest();
    }
}
