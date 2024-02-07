package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.CustomerEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerEntity> {

    @Override
    boolean update(CustomerEntity dto, Connection connection);

    @Override
    boolean delete(String id, Connection connection);

    @Override
    List<CustomerEntity> getAll(Connection connection);

    @Override
    boolean save(CustomerEntity dto, Connection connection) throws SQLException;
}
