package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.util.LoggerUtil;
import ca.jrvs.apps.stockquote.model.Position;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements CrudDAO<Position, ID> {

    private static final Logger logger = LoggerUtil.getLogger();
    private final Connection connection;

    private static final String SQL_INSERT = "INSERT INTO holdings (" +
            "ticker_symbol, shares_count, total_cost) " +
            "VALUES (?, ?, ?)";

    private static final String SQL_SELECT_BY_ID = "SELECT * " +
            "FROM holdings " +
            "WHERE holding_id = ?";

    private static final String SQL_SELECT_BY_SYMBOL = "SELECT * " +
            "FROM holdings " +
            "WHERE ticker_symbol = ?";

    private static final String SQL_SELECT_ALL = "SELECT * " +
            "FROM holdings";

    private static final String SQL_UPDATE_BY_ID = "UPDATE holdings SET " +
            "ticker_symbol = ?, shares_count = ?, total_cost = ? " +
            "WHERE holding_id = ?";

    private static final String SQL_UPDATE_BY_SYMBOL = "UPDATE holdings SET " +
            "shares_count = ?, total_cost = ? " +
            "WHERE ticker_symbol = ?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM holdings " +
            "WHERE holding_id = ?";

    private static final String SQL_DELETE_BY_SYMBOL = "DELETE FROM holdings " +
            "WHERE ticker_symbol = ?";

    public PositionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Position create(Position dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, dto.getSymbol());
            statement.setInt(2, dto.getNumOfShares());
            statement.setDouble(3, dto.getValuePaid());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            logger.error("Failed to insert new position", e);
        }
        return null;
    }

    @Override
    public Position findById(ID id) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id.getId());
            ResultSet resultSet = statement.executeQuery();
            Position position = new Position();
            while (resultSet.next()) {
                position.setId(new ID(resultSet.getInt("holding_id")));
                position.setSymbol(resultSet.getString("ticker_symbol"));
                position.setNumOfShares(resultSet.getInt("shares_count"));
                position.setValuePaid(resultSet.getDouble("total_cost"));
            }
            return position;
        } catch (SQLException e) {
            logger.error("Failed to find position by ID", e);
        }
        return null;
    }

    public Position findBySymbol(String tickerSymbol) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_BY_SYMBOL)) {
            statement.setString(1, tickerSymbol);
            ResultSet resultSet = statement.executeQuery();
            Position position = null;
            while (resultSet.next()) {
                position = new Position();
                position.setId(new ID(resultSet.getInt("holding_id")));
                position.setSymbol(resultSet.getString("ticker_symbol"));
                position.setNumOfShares(resultSet.getInt("shares_count"));
                position.setValuePaid(resultSet.getDouble("total_cost"));
            }
            return position;
        } catch (SQLException e) {
            logger.error("Failed to find position by ticker symbol", e);
        }
        return null;
    }

    @Override
    public List<Position> findAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Position> positionList = new ArrayList<>();
            while (resultSet.next()) {
                Position tempPosition = new Position();
                tempPosition.setId(new ID(resultSet.getInt("holding_id")));
                tempPosition.setSymbol(resultSet.getString("ticker_symbol"));
                tempPosition.setNumOfShares(resultSet.getInt("shares_count"));
                tempPosition.setValuePaid(resultSet.getDouble("total_cost"));
                positionList.add(tempPosition);
            }
            return positionList;
        } catch (SQLException e) {
            logger.error("Failed to retrieve all positions", e);
        }
        return null;
    }

    @Override
    public Position update(Position dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_BY_SYMBOL)) {
            statement.setInt(1, dto.getNumOfShares());
            statement.setDouble(2, dto.getValuePaid());
            statement.setString(3, dto.getSymbol());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            logger.error("Failed to update position", e);
        }
        return null;
    }

    @Override
    public void delete(ID id) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Failed to delete position by ID", e);
        }
    }

    public void delete(String tickerSymbol) {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE_BY_SYMBOL)) {
            statement.setString(1, tickerSymbol);
            statement.execute();
        } catch (SQLException e) {
            logger.error("Failed to delete position by ticker symbol", e);
        }
    }
}
