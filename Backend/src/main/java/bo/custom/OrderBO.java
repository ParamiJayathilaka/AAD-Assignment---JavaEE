package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<OrderDTO> getAllOrder(Connection connection) throws SQLException;
    boolean saveOrder ( OrderDTO orderDTO , Connection connection) throws SQLException;
    boolean updateOrder ( OrderDTO orderDTO , Connection connection) throws SQLException;
    boolean deleteOrder (String oid , Connection connection) throws SQLException;
}
