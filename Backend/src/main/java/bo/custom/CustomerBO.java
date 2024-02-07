package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomer(Connection connection);
    boolean saveCustomer (CustomerDTO customerDTO , Connection connection) throws SQLException;
    boolean updateCustomer (CustomerDTO customerDTO ,Connection connection);
    boolean deleteCustomer (String id,Connection connection);



}
