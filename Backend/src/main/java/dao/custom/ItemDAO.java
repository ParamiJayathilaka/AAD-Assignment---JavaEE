package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.ItemEntity;

import java.sql.Connection;
import java.util.List;

public interface ItemDAO extends CrudDAO<ItemEntity> {

    @Override
    boolean save(ItemEntity dto, Connection connection);

    @Override
    boolean update(ItemEntity dto, Connection connection);

    @Override
    boolean delete(String id, Connection connection);

    @Override
    List<ItemEntity> getAll(Connection connection);
}
