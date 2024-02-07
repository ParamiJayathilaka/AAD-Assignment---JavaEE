package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.ItemEntity;

import java.sql.Connection;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(ItemEntity dto, Connection connection) {
        return false;
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
