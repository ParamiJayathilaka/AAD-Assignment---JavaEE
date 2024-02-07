package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.ItemEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(ItemEntity dto, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item(code , description , qtyOnHand ,unitPrice) VALUES (?,?,?,?)");
        preparedStatement.setString(1, dto.getCode());
        preparedStatement.setString(2, dto.getDescription());
        preparedStatement.setInt(3, dto.getQtyOnHand());
        preparedStatement.setDouble(4, dto.getUnitPrice());

        boolean isSaved = preparedStatement.executeUpdate() > 0;
        return isSaved;
    }

    @Override
    public boolean update(ItemEntity dto, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }

    @Override
    public List<ItemEntity> getAll(Connection connection) {
        return null;
    }
}
