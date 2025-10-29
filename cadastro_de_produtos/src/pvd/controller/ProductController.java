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
public class ProductController extends Controller {

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT _id, name, unit, price, stock_quantity, last_sale_date FROM products";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();

                product.setId(rs.getInt("_id"));
                product.setName(rs.getString("name"));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getDouble("stock_quantity"));

                Date date = rs.getDate("last_sale_date");
                product.setLastSaleDate(date);

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "Error while fetching products: " + e.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

        return products;
    }

    @Override
    public boolean create(Model model) {
        String sql = "INSERT INTO products (name, unit, price, stock_quantity) VALUES (?, ?, ?, ?)";
        Product product = (Product) model;

        String name = product.getName();
        String unit = product.getUnit();
        double price = product.getPrice();
        double stockQuantity = product.getStockQuantity();

        try (Connection conn = ConnectionFactory.getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, unit);
            stmt.setDouble(3, price);
            stmt.setDouble(4, stockQuantity);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean update(Model model) {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

}
