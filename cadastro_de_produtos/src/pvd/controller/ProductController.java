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

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE _id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();

                product.setId(rs.getInt("_id"));
                product.setName(rs.getString("name"));
                product.setUnit(rs.getString("unit"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getDouble("stock_quantity"));

                Timestamp lastSaleDate = rs.getTimestamp("last_sale_date");
                if (lastSaleDate != null) {
                    product.setLastSaleDate(new java.util.Date(lastSaleDate.getTime()));
                }
                
                return product;
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "No record found with ID " + id,
                        "Not Found",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(getClass().getName())
                    .severe("Error while fetching product: " + e.getMessage());

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "An unexpected error occurred while fetching the record.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

        return null;
    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE _id = ?";
        try (java.sql.Connection conn = ConnectionFactory.getConnection(); java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Model model) {
        Product product = (Product) model;

        String sql = "UPDATE products SET name=?, unit=?, price=?, stock_quantity=? WHERE _id=?";
        
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getUnit());
        System.out.println(product.getPrice());
        System.out.println(product.getStockQuantity());

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getUnit());
            stmt.setDouble(3, product.getPrice());
            stmt.setDouble(4, product.getStockQuantity());
            stmt.setInt(5, product.getId());
            
            System.out.println("TESTE");
            int rows = stmt.executeUpdate();
            
            return rows > 0;

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(getClass().getName())
                    .severe("Error while updating product: " + e.getMessage());

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "An unexpected error occurred while updating the record.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

}
