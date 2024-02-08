package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean update(OrderEntity dto, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET date =?, customerId =? WHERE oid=? ");
        preparedStatement.setString(3, dto.getOid());
        preparedStatement.setString(1, dto.getDate());
        preparedStatement.setString(3, dto.getCustomerId());

        boolean isUpdate = preparedStatement.executeUpdate() > 0;
        return isUpdate;
    }

    @Override
    public boolean delete(String code, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM orders WHERE  code=?");
        preparedStatement.setString(1, code);

        boolean isDeleted = preparedStatement.executeUpdate() > 0;

        return isDeleted;
    }

    @Override
    public List<OrderEntity> getAll(Connection connection) throws SQLException {

        List<OrderEntity> orderEntityList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM orders");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String oid = resultSet.getString(1);
            String date = resultSet.getString(2);
            String customerId = resultSet.getString(3);


            OrderEntity order = new OrderEntity(oid,date,customerId);
            orderEntityList.add(order);

        }
        return orderEntityList;
    }
}
