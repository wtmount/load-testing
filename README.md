An application to perform load testing.

The application asks the user to enter the initial number of requests. Then is sends both GET and POST requests to specific address with specified timeout. Having made all requests, it calculates the percent of expired requests.

If the percent of expired requests exceeds the allowed percent of expired requests, the number of requests is decreased by 10 percent. Otherwise, the number of requests is increased by 10 percent.

After each test, the application prints the result and asks the user if it should continue.
