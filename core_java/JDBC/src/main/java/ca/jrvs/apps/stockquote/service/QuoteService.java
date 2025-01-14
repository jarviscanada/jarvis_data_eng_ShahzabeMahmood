package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.SymbolNotFoundException;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class QuoteService {

    private static final Logger logger = LoggerUtil.getLogger();
    private final QuoteDAO quoteDAO;
    private final QuoteHttpHelper quoteHttpHelper;

    public QuoteService(Connection databaseConnection) {
        this.quoteDAO = new QuoteDAO(databaseConnection);
        this.quoteHttpHelper = new QuoteHttpHelper();
    }

    public Quote getQuoteBySymbol(String ticker) {
        return quoteDAO.findBySymbol(ticker);
    }

    /**
     * Retrieves the latest quote from an external API endpoint.
     * @param ticker the stock symbol
     * @return an Optional containing the fetched Quote or empty if not found
     */
    public Optional<Quote> fetchLatestQuote(String ticker, boolean showDetails) {
        try {
            Quote retrievedQuote = quoteHttpHelper.fetchQuoteInfo(ticker);
            if (showDetails) {
                displayQuote(retrievedQuote);
            }
            return Optional.of(retrievedQuote);
        } catch (SymbolNotFoundException ex) {
            logger.error("ERROR: Stock symbol [{}] not found", ticker);
            System.out.println("ERROR: Invalid stock symbol");
            return Optional.empty();
        }
    }

    public Optional<Quote> fetchLatestQuote(String ticker) {
        return fetchLatestQuote(ticker, false);
    }

    /**
     * Fetches and stores quote data in the database.
     * If the symbol exists, updates it; otherwise, creates a new record.
     * @param ticker the stock symbol
     * @return Optional containing the fetched Quote, or empty if not found
     */
    public Optional<Quote> fetchAndStoreQuote(String ticker) {
        Optional<Quote> retrievedQuoteOpt = fetchLatestQuote(ticker);
        if (retrievedQuoteOpt.isEmpty()) {
            return Optional.empty();
        }
        Quote fetchedQuote = retrievedQuoteOpt.get();
        Quote existingQuote = quoteDAO.findBySymbol(ticker);

        if (existingQuote == null) {
            quoteDAO.create(fetchedQuote);
        } else {
            quoteDAO.update(fetchedQuote);
        }

        return retrievedQuoteOpt;
    }

    /**
     * Prints a summary of the quote.
     * @param quote the Quote object
     */
    private void displayQuote(Quote quote) {
        StringBuilder quoteDetails = new StringBuilder();
        quoteDetails.append("Stock Symbol: ").append(quote.getSymbol()).append("\n")
                .append("Current Price: $").append(quote.getPrice()).append("\n")
                .append("Last Updated: ").append(quote.getTimestamp()).append("\n");
        System.out.println(quoteDetails);
    }
}
