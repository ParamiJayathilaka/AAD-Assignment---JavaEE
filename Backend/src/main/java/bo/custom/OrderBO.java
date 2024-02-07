package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<OrderDTO> getAllOrder(Connection connection);
    boolean saveOrder ( OrderDTO orderDTO , Connection connection);
    boolean updateOrder ( OrderDTO orderDTO , Connection connection);
    boolean deleteOrder (String oid , Connection connection);
}
