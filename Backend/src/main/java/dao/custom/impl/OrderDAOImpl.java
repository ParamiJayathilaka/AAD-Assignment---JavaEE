package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(OrderEntity dto, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (oid , date , customerId ) VALUES (?,?,?)");
        preparedStatement.setString(1, dto.getOid());
        preparedStatement.setString(2, dto.getDate());
        preparedStatement.setString(3, dto.getCustomerId());


        boolean isSaved = preparedStatement.executeUpdate() > 0;
        return isSaved;
    }

    @Override
    public boolean update(OrderEntity dto, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }

    @Override
    public List<OrderEntity> getAll(Connection connection) {
        return null;
    }
}
