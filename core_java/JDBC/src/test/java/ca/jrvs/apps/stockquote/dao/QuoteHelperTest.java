package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class QuoteHelperTest {

    private QuoteHttpHelper quoteHttpHelper;

    @Before
    public void setup() {
        // Create a mock instance of QuoteHttpHelper
        quoteHttpHelper = mock(QuoteHttpHelper.class);
    }

    @Test
    public void testFetchQuoteInfo() {
        // Mock a Quote object to return
        Quote mockQuote = new Quote();
        mockQuote.setTicker("MSFT");
        mockQuote.setPrice(327.73);

        // Simulate the response of fetchQuoteInfo
        when(quoteHttpHelper.fetchQuoteInfo("MSFT")).thenReturn(mockQuote);

        // Act
        Quote result = quoteHttpHelper.fetchQuoteInfo("MSFT");

        // Assert
        assertNotNull(result);
        assertEquals("MSFT", result.getTicker());
        assertEquals(327.73, result.getPrice(), 0.01);

        // Verify the mock method was called
        verify(quoteHttpHelper).fetchQuoteInfo("MSFT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFetchQuoteInfo_ThrowsException() {
        // Mock exception behavior
        when(quoteHttpHelper.fetchQuoteInfo("INVALID")).thenThrow(IllegalArgumentException.class);

        // Act
        quoteHttpHelper.fetchQuoteInfo("INVALID");
    }
}
