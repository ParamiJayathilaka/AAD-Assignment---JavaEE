package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean update(CustomerEntity dto, Connection connection) {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) {
        return false;
    }

    @Override
    public List<CustomerEntity> getAll(Connection connection) {
        return null;
    }

    @Override
    public boolean save(CustomerEntity dto, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer(id , name , address) VALUES (?,?,?)");
        preparedStatement.setString(1, dto.getId());
        preparedStatement.setString(2, dto.getName());
        preparedStatement.setString(3, dto.getAddress());

        boolean isSaved = preparedStatement.executeUpdate() > 0;

        return isSaved;
    }
}
