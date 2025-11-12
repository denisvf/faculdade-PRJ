/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.controller;
import pvd.model.Sale;
import java.sql.*;

/**
 *
 * @author denis
 */
public class SaleController extends Controller {

    public SaleController() {
        this.tableName = "sales";
    }

    @Override
    protected Sale mapResultSetToModel(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("_id"));
        sale.setSaleDateTime(rs.getTimestamp("sale_datetime"));
        sale.setTotalValue(rs.getDouble("total_value"));

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
        stmt.setDouble(2, sale.getTotalValue());

        if (sale.getCustomerId() != null) {
            stmt.setInt(3, sale.getCustomerId());
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, pvd.model.Model model) throws SQLException {
        Sale sale = (Sale) model;

        stmt.setTimestamp(1, new Timestamp(sale.getSaleDateTime().getTime()));
        stmt.setDouble(2, sale.getTotalValue());

        if (sale.getCustomerId() != null) {
            stmt.setInt(3, sale.getCustomerId());
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }

        stmt.setInt(4, sale.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO sales (sale_datetime, total_value, customer_id) VALUES (?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE sales SET sale_datetime=?, total_value=?, customer_id=? WHERE _id=?";
    }
}
