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

    @Override
    protected Product mapResultSetToModel(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setId(rs.getInt("_id"));
        product.setName(rs.getString("name"));
        product.setCode(rs.getString("code"));
        product.setUnit(rs.getString("unit"));
        product.setPrice(rs.getDouble("price"));
        product.setStockQuantity(rs.getDouble("stock_quantity"));

        Timestamp lastSaleDate = rs.getTimestamp("last_sale_date");
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
