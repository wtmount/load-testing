package load;

public interface Sender {
    void sendRequest(String address, String httpMethod, int timeout);

    int getExpiredRequestsCount();

    void resetExpiredRequestsCount();
}