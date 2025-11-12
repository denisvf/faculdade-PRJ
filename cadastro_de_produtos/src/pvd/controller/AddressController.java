package pvd.controller;

import java.sql.*;
import java.util.*;
import pvd.ConnectionFactory;
import pvd.model.Address;

public class AddressController extends Controller<Address> {

    public AddressController() {
        this.tableName = "addresses";
    }

    @Override
    protected Address mapResultSetToModel(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("_id"));
        address.setZipCode(rs.getString("zip_code"));
        address.setStreet(rs.getString("street"));
        address.setNumber(rs.getInt("number"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setComplement(rs.getString("complement"));
        return address;
    }

    @Override
    protected void fillPreparedStatementForCreate(PreparedStatement stmt, Address address) throws SQLException {
        stmt.setString(1, address.getZipCode());
        stmt.setString(2, address.getStreet());
        stmt.setInt(3, address.getNumber());
        stmt.setString(4, address.getCity());
        stmt.setString(5, address.getState());
        stmt.setString(6, address.getComplement());
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement stmt, Address address) throws SQLException {
        stmt.setString(1, address.getZipCode());
        stmt.setString(2, address.getStreet());
        stmt.setInt(3, address.getNumber());
        stmt.setString(4, address.getCity());
        stmt.setString(5, address.getState());
        stmt.setString(6, address.getComplement());
        stmt.setInt(7, address.getId());
    }

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO addresses (zip_code, street, number, city, state, complement) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE addresses SET zip_code=?, street=?, number=?, city=?, state=?, complement=? WHERE _id=?";
    }

    public int createAndReturnId(Address address) {
        String sql = "INSERT INTO addresses (state, city, zip_code, street, number, complement) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, address.getState());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getZipCode());
            stmt.setString(4, address.getStreet());
            stmt.setInt(5, address.getNumber());
            stmt.setString(6, address.getComplement());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // retorna o _id gerado
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Erro ao salvar endereço: " + e.getMessage(),
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

        return -1;
    }

}
