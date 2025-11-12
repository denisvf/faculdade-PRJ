/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.controller;

import java.sql.Connection;
import java.sql.SQLException;
import pvd.ConnectionFactory;
import pvd.model.Model;
import pvd.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author denis
 */
public class ProductController extends Controller<Product> {

    public ProductController() {
        this.tableName = "products";
    }

    public boolean updateLastSaleDateTime(int productId, java.util.Date date) {
        String sql = "UPDATE products SET last_sale_date_time = ? WHERE _id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
            stmt.setInt(2, productId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Erro ao atualizar a data da última venda: " + e.getMessage(),
                    "Erro de Banco de Dados",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    @Override
    protected Product mapResultSetToModel(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setId(rs.getInt("_id"));
        product.setName(rs.getString("name"));
        product.setCode(rs.getString("code"));
        product.setUnit(rs.getString("unit"));
        product.setPrice(rs.getDouble("price"));
        product.setStockQuantity(rs.getDouble("stock_quantity"));

        Timestamp lastSaleDate = rs.getTimestamp("last_sale_date_time");
        if (lastSaleDate != null) {
            product.setLastSaleDate(new Date(lastSaleDate.getTime()));
        }

        return product;
    }

    @Override
    protected void fillPreparedStatementForCreate(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getCode());
        stmt.setString(3, product.getUnit());
        stmt.setDouble(4, product.getPrice());
        stmt.setDouble(5, product.getStockQuantity());
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getUnit());
        stmt.setDouble(3, product.getPrice());
        stmt.setDouble(4, product.getStockQuantity());
        stmt.setString(5, product.getCode());
        stmt.setInt(6, product.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO products (name, code, unit, price, stock_quantity) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE products SET name=?, unit=?, price=?, stock_quantity=?, code=? WHERE _id=?";
    }
}
