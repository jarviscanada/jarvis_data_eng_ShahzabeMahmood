package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuoteHttpHelperTest {

    @Test
    public void fetchQuoteInfo() {
        String apiKey = "f8b8139f87msh56d1e15a58f765dp1ce917jsn21b61aa3b2ce";
        QuoteHttpHelper helper = new QuoteHttpHelper();

        // Fetch a Quote object using the helper
        Quote result = helper.fetchQuoteInfo("AAPL");

        // Assert that the returned Quote is not null
        assertNotNull(result);

        // Validate the symbol in the Quote
        assertEquals("AAPL", result.getSymbol());
    }
}
