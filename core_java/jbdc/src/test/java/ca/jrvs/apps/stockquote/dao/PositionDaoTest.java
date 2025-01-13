package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Position;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PositionDaoTest {

    private Connection mockConnection;
    private PositionDao positionDao;
    private Position mockPosition;

    @Before
    public void setup() throws SQLException {
        // Mock the database connection and prepared statements
        mockConnection = mock(Connection.class);
        positionDao = new PositionDao(mockConnection);

        // Mock a sample position
        mockPosition = new Position();
        mockPosition.setTicker("AAPL");
        mockPosition.setNumOfShares(100);
        mockPosition.setValuePaid(15000.00);
    }

    @Test
    public void testSave() throws SQLException {
        // Mock the PreparedStatement to avoid real DB calls
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);

        // Perform the save operation
        Position savedPosition = positionDao.save(mockPosition);

        // Verify that save method returns the correct object
        assertNotNull(savedPosition);
        assertEquals("AAPL", savedPosition.getTicker());
        assertEquals(100, savedPosition.getNumOfShares());
        assertEquals(15000.00, savedPosition.getValuePaid(), 0.01);

        // Verify that the prepared statement was executed
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    public void testFindById_ValidTicker() throws SQLException {
        // Mock the ResultSet for the findById query
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("symbol")).thenReturn("AAPL");
        when(mockResultSet.getInt("number_of_shares")).thenReturn(100);
        when(mockResultSet.getDouble("value_paid")).thenReturn(15000.00);

        // Mock the PreparedStatement and ResultSet behavior for findById
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        Optional<Position> foundPosition = positionDao.findById("AAPL");

        // Assert the result
        assertTrue(foundPosition.isPresent());
        Position position = foundPosition.get();
        assertEquals("AAPL", position.getTicker());
        assertEquals(100, position.getNumOfShares());
        assertEquals(15000.00, position.getValuePaid(), 0.01);

        // Verify the PreparedStatement execution
        verify(mockStatement, times(1)).setString(1, "AAPL");
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    public void testFindById_InvalidTicker() throws SQLException {
        // Mock the ResultSet for the findById query to return no results
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(false);

        // Mock the PreparedStatement and ResultSet behavior for findById
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        Optional<Position> foundPosition = positionDao.findById("INVALID");

        // Assert the result is empty
        assertFalse(foundPosition.isPresent());
    }

    @Test
    public void testDeleteById() throws SQLException {
        // Mock PreparedStatement for deleteById
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);

        // Perform delete operation
        positionDao.deleteById("AAPL");

        // Verify the statement execution
        verify(mockStatement, times(1)).setString(1, "AAPL");
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSave_NullPosition() {
        // Test saving a null position
        positionDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSave_NullTicker() {
        // Test saving a position with null ticker
        Position invalidPosition = new Position();
        positionDao.save(invalidPosition);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindById_NullTicker() throws SQLException {
        // Test finding a position with null ticker
        positionDao.findById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteById_NullTicker() throws SQLException {
        // Test deleting a position with null ticker
        positionDao.deleteById(null);
    }
}
