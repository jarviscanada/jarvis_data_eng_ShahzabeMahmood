package ca.jrvs.apps.jbdc.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AlphaVantageApiClient {
    private static final String API_KEY = "f8b8139f87msh56d1e15a58f765dp1ce917jsn21b61aa3b2ce";
    private static final String HOST = "alpha-vantage.p.rapidapi.com";
    private static final String BASE_URL = "https://alpha-vantage.p.rapidapi.com/query";

    public static void main(String[] args) {
        String symbol = "MSFT";


        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json"))
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();
            System.out.println("Raw JSON: " + jsonResponse);

            // Parse JSON response to Java object
            ObjectMapper objectMapper = new ObjectMapper();
            GlobalQuoteResponse globalQuoteResponse = objectMapper.readValue(jsonResponse, GlobalQuoteResponse.class);

            // Print parsed data
            System.out.println("Symbol: " + globalQuoteResponse.getGlobalQuote().getSymbol());
            System.out.println("Price: " + globalQuoteResponse.getGlobalQuote().getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
