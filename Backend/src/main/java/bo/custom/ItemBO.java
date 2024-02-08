package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    List<ItemDTO> getAllItem(Connection connection) throws SQLException;
    boolean saveItem (ItemDTO itemDTO , Connection connection) throws SQLException;
    boolean updateItem (ItemDTO itemDTO , Connection connection) throws SQLException;
    boolean deleteItem (String code ,Connection connection) throws SQLException;
}
