package pvd.controller;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import pvd.ConnectionFactory;
import pvd.model.Model;

public abstract class Controller<T extends Model> {

    protected String tableName;
    protected String idColumn = "_id";

    protected abstract T mapResultSetToModel(ResultSet rs) throws SQLException;

    protected abstract void fillPreparedStatementForCreate(PreparedStatement stmt, T model) throws SQLException;

    protected abstract void fillPreparedStatementForUpdate(PreparedStatement stmt, T model) throws SQLException;

    public boolean create(T model) {
        String sql = getInsertSQL();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillPreparedStatementForCreate(stmt, model);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error inserting record into " + tableName + ": " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public T getById(int id) {
        System.out.println("ID: " + id);
        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToModel(rs);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No record found with ID " + id + " in " + tableName,
                        "Not Found",
                        JOptionPane.WARNING_MESSAGE
                );
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error fetching record from " + tableName + ": " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }

    public boolean update(T model) {
        String sql = getUpdateSQL();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillPreparedStatementForUpdate(stmt, model);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error updating record in " + tableName + ": " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error deleting record from " + tableName + ": " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public List<T> getAll() {
        List<T> models = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName;

        try (Connection conn = pvd.ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                T model = mapResultSetToModel(rs);
                models.add(model);
            }

        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(getClass().getName())
                    .severe("Error fetching from " + this.tableName + ": " + e.getMessage());
        }

        return models;
    }

    protected String getInsertSQL() {
        throw new UnsupportedOperationException("You must override getInsertSQL() in subclass.");
    }

    protected String getUpdateSQL() {
        throw new UnsupportedOperationException("You must override getUpdateSQL() in subclass.");
    }
}
