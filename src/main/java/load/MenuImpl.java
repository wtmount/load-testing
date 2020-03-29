package load;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuImpl implements Menu {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public int readNumberOfRequests() {
        int numberOfRequests = 0;

        do {
            try {
                System.out.print("Enter number of requests per second: ");
                numberOfRequests = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Number of requests must be a number");
            }
            scanner.nextLine();
        } while (numberOfRequests < 1);

        return numberOfRequests;
    }

    @Override
    public void printResult(String address, int requestsPerSecond, int timeout, int expiredRequests,
                            double expReqPercent, double allowedExpReqPercent) {
        System.out.printf("%-50s%s%n%-50s%d%n%-50s%d%n%-50s%d%n%-50s%.2f%n%-50s%.2f%n%n",
                "Website:", address, "Requests per second:", requestsPerSecond,
                "Timeout:", timeout, "Expired requests:", expiredRequests,
                "Percent of expired requests:", expReqPercent,
                "Percent of allowed expired requests:", allowedExpReqPercent);
    }

    @Override
    public boolean isContinue() {
        int option = 0;

        do {
            try {
                System.out.print("Press 1 to continue, 2 to stop: ");
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter either 1 or 2");
            }
            scanner.nextLine();
        } while ((option != 1) && (option != 2));

        return option == 1;
    }
}
