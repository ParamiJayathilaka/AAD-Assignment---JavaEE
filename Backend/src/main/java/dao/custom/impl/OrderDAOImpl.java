package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.OrderEntity;

import java.sql.Connection;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(OrderEntity dto, Connection connection) {
        return false;
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
