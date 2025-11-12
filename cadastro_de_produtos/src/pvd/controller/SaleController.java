package pvd.controller;

import pvd.model.Sale;
import java.sql.*;
import pvd.ConnectionFactory;

public class SaleController extends Controller {

    public SaleController() {
        this.tableName = "sales";
    }

    @Override
    protected Sale mapResultSetToModel(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("_id"));
        sale.setSaleDateTime(rs.getTimestamp("sale_datetime"));
        sale.setTotal(rs.getDouble("total_value"));
        sale.setPaymentMethod(rs.getString("payment_method"));

        int customerId = rs.getInt("customer_id");
        if (!rs.wasNull()) {
            sale.setCustomerId(customerId);
        }

        return sale;
    }

    @Override
    protected void fillPreparedStatementForCreate(PreparedStatement stmt, pvd.model.Model model) throws SQLException {
        Sale sale = (Sale) model;

        stmt.setTimestamp(1, new Timestamp(sale.getSaleDateTime().getTime()));
        stmt.setDouble(2, sale.getTotal());
        stmt.setString(3, sale.getPaymentMethod());

        if (sale.getCustomerId() != null) {
            stmt.setInt(4, sale.getCustomerId());
        } else {
            stmt.setNull(4, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, pvd.model.Model model) throws SQLException {
        Sale sale = (Sale) model;

        stmt.setTimestamp(1, new Timestamp(sale.getSaleDateTime().getTime()));
        stmt.setDouble(2, sale.getTotal());
        stmt.setString(3, sale.getPaymentMethod());

        if (sale.getCustomerId() != null) {
            stmt.setInt(4, sale.getCustomerId());
        } else {
            stmt.setNull(4, java.sql.Types.INTEGER);
        }

        stmt.setInt(5, sale.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO sales (sale_datetime, total_value, payment_method, customer_id) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE sales SET sale_datetime=?, total_value=?, payment_method=?, customer_id=? WHERE _id=?";
    }

    /**
     * Cria uma venda e retorna o ID gerado automaticamente.
     */
    public int createAndReturnId(Sale sale) {
        String sql = "INSERT INTO sales (customer_id, sale_datetime, payment_method, total_value) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (sale.getCustomerId() != null) {
                stmt.setInt(1, sale.getCustomerId());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }

            stmt.setTimestamp(2, new java.sql.Timestamp(sale.getSaleDateTime().getTime()));
            stmt.setString(3, sale.getPaymentMethod());
            stmt.setDouble(4, sale.getTotal());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir venda: nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao inserir venda: nenhum ID retornado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Erro ao criar a venda: " + e.getMessage(),
                    "Erro de Banco de Dados",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

        return -1;
    }
}
