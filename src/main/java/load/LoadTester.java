package load;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadTester {
    private static final String ADDRESS = "http://www.google.com";
    private static final int TIMEOUT = 1000;
    private static final double PERCENT_OF_ALLOWED_EXPIRED_REQUESTS = 30.0;
    private static final double PERCENT_TO_CHANGE = 10.0;
    private static final Random RANDOM = new Random();
    
    private Menu menu;
    private Sender sender;

    public LoadTester(Menu menu, Sender sender) {
        this.menu = menu;
        this.sender = sender;
    }

    public void loadTest() {
        int numberOfRequests = menu.readNumberOfRequests();
        boolean running = true;

        while (running) {
            makeRequests(numberOfRequests);

            int expiredRequests = sender.getExpiredRequestsCount();
            double percentOfExpiredRequests = calculatePercentOfExpiredRequests(numberOfRequests, expiredRequests);

            menu.printResult(ADDRESS, numberOfRequests, TIMEOUT, expiredRequests,
                    percentOfExpiredRequests, PERCENT_OF_ALLOWED_EXPIRED_REQUESTS);

            numberOfRequests = calculateNewNumberOfRequests(percentOfExpiredRequests, numberOfRequests);
            System.out.println("Number of requests is changed to " + numberOfRequests + "\n");

            sender.resetExpiredRequestsCount();
            running = menu.isContinue();
        }
    }

    private void makeRequests(int numberOfRequests) {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfRequests; i++) {
            es.execute(() -> sender.sendRequest(ADDRESS, generateHttpMethod(), TIMEOUT));
        }
        es.shutdown();

        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            es.shutdownNow();
            System.out.println("Tasks could not be completed.");
            Thread.currentThread().interrupt();
        }
    }

    private String generateHttpMethod() {
        return RANDOM.nextBoolean() ? "GET" : "POST";
    }

    private double calculatePercentOfExpiredRequests(int numberOfRequests, int expiredRequests) {
        return expiredRequests / ((double) numberOfRequests / 100);
    }

    private int calculateNewNumberOfRequests(double percentOfExpiredRequests, int oldNumber) {
        return (percentOfExpiredRequests < PERCENT_OF_ALLOWED_EXPIRED_REQUESTS) ?
                oldNumber + (int) Math.ceil(oldNumber / PERCENT_TO_CHANGE) :
                oldNumber - (int) Math.floor(oldNumber / PERCENT_TO_CHANGE);
    }
}