package pvd.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pvd.model.Customer;

public class CustomerController extends Controller<Customer> {

    public CustomerController() {
        this.tableName = "customers";
    }

    @Override
    protected Customer mapResultSetToModel(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("_id"));
        customer.setName(rs.getString("name"));
        customer.setCode(rs.getString("code"));
        customer.setEmail(rs.getString("email"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        customer.setAddressId(rs.getInt("address_id"));
        return customer;
    }

    @Override
    protected void fillPreparedStatementForCreate(PreparedStatement stmt, Customer customer) throws SQLException {
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getCode());
        stmt.setString(3, customer.getEmail());
        stmt.setString(4, customer.getPhoneNumber());
        stmt.setInt(5, customer.getAddressId());
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, Customer customer) throws SQLException {
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getCode());
        stmt.setString(3, customer.getEmail());
        stmt.setString(4, customer.getPhoneNumber());
        stmt.setInt(5, customer.getAddressId());
        stmt.setInt(6, customer.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO customers (name, code, email, phone_number, address_id) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE customers SET name=?, code=?, email=?, phone_number=?, address_id=? WHERE _id=?";
    }
}
