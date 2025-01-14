package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuoteHttpHelper {

    private String apiKey;
    private OkHttpClient client;

    // Constructor to initialize OkHttpClient and API key from properties file
    public QuoteHttpHelper() {
        this.apiKey = loadApiKeyFromProperties();  // Load API key from properties file
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalArgumentException("API_KEY is missing from the properties file.");
        }
        this.client = new OkHttpClient();
    }

    // Method to load API key from the properties file
    private String loadApiKeyFromProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/properties")) {
            properties.load(fis);  // Load properties from the file
            return properties.getProperty("api.key");  // Fetch the API key value
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to load API_KEY from properties file.", e);
        }
    }

    // Method to fetch stock quote information
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        try {
            // Build the URL
            String url = String.format(
                    "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                    symbol, apiKey);

            // Create the HTTP request
            Request request = new Request.Builder().url(url).build();

            // Send the request and get the response
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IllegalArgumentException("Invalid ticker symbol or API issue");
                }

                // Parse the JSON response
                ObjectMapper mapper = new ObjectMapper();
                String jsonData = response.body().string();

                // Extract relevant data from JSON and populate Quote object
                JsonNode rootNode = mapper.readTree(jsonData);
                JsonNode globalQuoteNode = rootNode.get("Global Quote");

                if (globalQuoteNode == null || globalQuoteNode.isEmpty()) {
                    throw new IllegalArgumentException("Invalid response structure");
                }

                Quote quote = new Quote();
                quote.setSymbol(globalQuoteNode.get("01. symbol").asText());
                return quote;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error fetching quote data", e);
        }
    }
}
