package load;

public interface Menu {
    int readNumberOfRequests();

    void printResult(String address, int requestsPerSecond, int timeout, int expiredRequests,
                     double expReqPercent, double allowedExpReqPercent);

    boolean isContinue();
}
