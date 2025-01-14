package ca.jrvs.apps.stockquote.model;

import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;


public class TestQuoteHttpHelper {
    public static void main(String[] args) {
        try {
            QuoteHttpHelper helper = new QuoteHttpHelper();
            Quote quote = helper.fetchQuoteInfo("AAPL");  // Test with a valid symbol (e.g., AAPL)
            System.out.println("Symbol: " + quote.getSymbol());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
