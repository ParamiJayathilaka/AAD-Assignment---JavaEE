package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean update(CustomerEntity dto, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=? ");
        preparedStatement.setString(3, dto.getId());
        preparedStatement.setString(1, dto.getName());
        preparedStatement.setString(2, dto.getAddress());

        boolean isUpdate = preparedStatement.executeUpdate() > 0;
        return isUpdate;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM customer WHERE  id=?");
        preparedStatement.setString(1, id);

        boolean isDeleted = preparedStatement.executeUpdate() > 0;

        return isDeleted;
    }

    @Override
    public List<CustomerEntity> getAll(Connection connection) throws SQLException {
        List<CustomerEntity> customerEntityList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);

            CustomerEntity customer = new CustomerEntity(id, name, address);
            customerEntityList.add(customer);

        }
        return customerEntityList;
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
