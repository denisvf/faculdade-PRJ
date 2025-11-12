/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pvd.controller;
import pvd.model.SaleItem;
import java.sql.*;

/**
 *
 * @author denis
 */

public class SaleItemController extends Controller {

    public SaleItemController() {
        this.tableName = "sale_items";
    }

    @Override
    protected SaleItem mapResultSetToModel(ResultSet rs) throws SQLException {
        SaleItem item = new SaleItem();
        item.setId(rs.getInt("_id"));
        item.setSaleId(rs.getInt("sale_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getDouble("quantity"));
        item.setPrice(rs.getDouble("price"));
        item.setTotal(rs.getDouble("total"));
        return item;
    }

    @Override
    protected void fillPreparedStatementForCreate(PreparedStatement stmt, pvd.model.Model model) throws SQLException {
        SaleItem item = (SaleItem) model;
        stmt.setInt(1, item.getSaleId());
        stmt.setInt(2, item.getProductId());
        stmt.setDouble(3, item.getQuantity());
        stmt.setDouble(4, item.getPrice());
        stmt.setDouble(5, item.getTotal());
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, pvd.model.Model model) throws SQLException {
        SaleItem item = (SaleItem) model;
        stmt.setInt(1, item.getSaleId());
        stmt.setInt(2, item.getProductId());
        stmt.setDouble(3, item.getQuantity());
        stmt.setDouble(4, item.getPrice());
        stmt.setDouble(5, item.getTotal());
        stmt.setInt(6, item.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO sale_items (sale_id, product_id, quantity, price, total) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE sale_items SET sale_id=?, product_id=?, quantity=?, price=?, total=? WHERE _id=?";
    }
}
