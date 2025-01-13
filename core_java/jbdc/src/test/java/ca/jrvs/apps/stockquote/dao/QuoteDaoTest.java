package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuoteDaoTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private QuoteDao quoteDao;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        quoteDao = new QuoteDao(mockConnection);
    }

    @Test
    public void testSave() throws SQLException {
        Quote quote = new Quote();
        quote.setTicker("AAPL");
        quote.setOpen(150.0);
        quote.setHigh(155.0);
        quote.setLow(149.0);
        quote.setPrice(153.0);
        quote.setVolume(1000000);

        // Correct way to set a java.sql.Date for the latest trading day
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        quote.setLatestTradingDay(sqlDate);

        quote.setPreviousClose(151.0);
        quote.setChange(2.0);
        quote.setChangePercent("1.32%");

        String query = "INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "open = VALUES(open), " +
                "high = VALUES(high), " +
                "low = VALUES(low), " +
                "price = VALUES(price), " +
                "volume = VALUES(volume), " +
                "latest_trading_day = VALUES(latest_trading_day), " +
                "previous_close = VALUES(previous_close), " +
                "change = VALUES(change), " +
                "change_percent = VALUES(change_percent);";

        // Ensure that the mock connection prepares the statement
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);

        // Mock the executeUpdate method to simulate an update query returning 1 (indicating 1 row affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Call the save method
        quoteDao.save(quote);

        // Verify that the correct values were set in the PreparedStatement
        verify(mockPreparedStatement).setString(1, "AAPL");
        verify(mockPreparedStatement).setDouble(2, 150.0);
        verify(mockPreparedStatement).setDouble(3, 155.0);
        verify(mockPreparedStatement).setDouble(4, 149.0);
        verify(mockPreparedStatement).setDouble(5, 153.0);
        verify(mockPreparedStatement).setInt(6, 1000000);
        verify(mockPreparedStatement).setDate(7, sqlDate); // Ensure you're passing the correct Date object
        verify(mockPreparedStatement).setDouble(8, 151.0);
        verify(mockPreparedStatement).setDouble(9, 2.0);
        verify(mockPreparedStatement).setString(10, "1.32%");

        // Verify that executeUpdate was called to save the quote
        verify(mockPreparedStatement).executeUpdate();
    }





    @Test
    public void testFindById_ValidSymbol() throws SQLException {
        String symbol = "AAPL";
        String query = "SELECT * FROM quote WHERE symbol = ?";

        Quote mockQuote = new Quote();
        mockQuote.setTicker("AAPL");
        mockQuote.setOpen(150.0);
        mockQuote.setHigh(155.0);
        mockQuote.setLow(149.0);
        mockQuote.setPrice(153.0);
        mockQuote.setVolume(1000000);
        mockQuote.setLatestTradingDay(new Date(System.currentTimeMillis()));
        mockQuote.setPreviousClose(151.0);
        mockQuote.setChange(2.0);
        mockQuote.setChangePercent("1.32%");

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("symbol")).thenReturn("AAPL");
        when(mockResultSet.getDouble("open")).thenReturn(150.0);
        when(mockResultSet.getDouble("high")).thenReturn(155.0);
        when(mockResultSet.getDouble("low")).thenReturn(149.0);
        when(mockResultSet.getDouble("price")).thenReturn(153.0);
        when(mockResultSet.getInt("volume")).thenReturn(1000000);
        when(mockResultSet.getDate("latest_trading_day")).thenReturn(new Date(System.currentTimeMillis()));
        when(mockResultSet.getDouble("previous_close")).thenReturn(151.0);
        when(mockResultSet.getDouble("change")).thenReturn(2.0);
        when(mockResultSet.getString("change_percent")).thenReturn("1.32%");

        Optional<Quote> result = quoteDao.findById(symbol);

        assertTrue(result.isPresent());
        assertEquals("AAPL", result.get().getTicker());
    }

    @Test
    public void testFindById_InvalidSymbol() throws SQLException {
        String symbol = "INVALID";
        String query = "SELECT * FROM quote WHERE symbol = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Quote> result = quoteDao.findById(symbol);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteById() throws SQLException {
        String symbol = "AAPL";
        String query = "DELETE FROM quote WHERE symbol = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);

        quoteDao.deleteById(symbol);

        verify(mockPreparedStatement).setString(1, "AAPL");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteAll() throws SQLException {
        String query = "DELETE FROM quote";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);

        quoteDao.deleteAll();

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testSave_NullQuote() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            quoteDao.save(null);
        });

        assertEquals("Quote cannot be null", thrown.getMessage());
    }

    @Test
    public void testFindById_NullSymbol() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            quoteDao.findById(null);
        });

        assertEquals("Symbol cannot be null", thrown.getMessage());
    }

    @Test
    public void testDeleteById_NullSymbol() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            quoteDao.deleteById(null);
        });

        assertEquals("Symbol cannot be null", thrown.getMessage());
    }
}
