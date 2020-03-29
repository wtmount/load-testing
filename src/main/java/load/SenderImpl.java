package load;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class SenderImpl implements Sender {
    private AtomicInteger expiredRequestsCount = new AtomicInteger(0);

    @Override
    public void sendRequest(String address, String httpMethod, int timeout) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(timeout);
            conn.setRequestMethod(httpMethod);
            if (httpMethod.equals("POST")) {
                conn.setRequestProperty("Content-Length", "0");
                conn.setDoOutput(true);
            }

            conn.getResponseCode();
        } catch (MalformedURLException e) {
            System.out.println(address + " is not a valid url.");
        } catch (SocketTimeoutException e) {
            expiredRequestsCount.incrementAndGet();
        } catch (IOException e) {
            System.out.println("Error when trying to connect to the server.");
        }
    }

    @Override
    public int getExpiredRequestsCount() {
        return expiredRequestsCount.get();
    }

    @Override
    public void resetExpiredRequestsCount() {
        expiredRequestsCount.set(0);
    }
}