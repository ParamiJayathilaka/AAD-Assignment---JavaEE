package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<OrderEntity> {
    @Override
    boolean save(OrderEntity dto, Connection connection) throws SQLException;

    @Override
    boolean update(OrderEntity dto, Connection connection);

    @Override
    boolean delete(String id, Connection connection);

    @Override
    List<OrderEntity> getAll(Connection connection);
}
