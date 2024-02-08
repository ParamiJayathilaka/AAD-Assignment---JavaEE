package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.CustomerEntity;
import entity.ItemEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean update(ItemEntity dto, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET description =?, qtyOnHand=?,unitPrize=? WHERE code=? ");
        preparedStatement.setString(4, dto.getCode());
        preparedStatement.setString(1, dto.getDescription());
        preparedStatement.setInt(2, dto.getQtyOnHand());
        preparedStatement.setDouble(3, dto.getUnitPrice());

        boolean isUpdate = preparedStatement.executeUpdate() > 0;
        return isUpdate;
    }

    @Override
    public boolean delete(String code, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM item WHERE  code=?");
        preparedStatement.setString(1, code);

        boolean isDeleted = preparedStatement.executeUpdate() > 0;

        return isDeleted;
    }

    @Override
    public List<ItemEntity> getAll(Connection connection) throws SQLException {
        List<ItemEntity> itemEntityList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM item");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String description = resultSet.getString(2);
            int qtyOnHand = resultSet.getInt(3);
            double unitPrice = resultSet.getDouble(4);

            ItemEntity item = new ItemEntity(code,description,qtyOnHand,unitPrice);
            itemEntityList.add(item);

        }
        return itemEntityList;
    }
}
