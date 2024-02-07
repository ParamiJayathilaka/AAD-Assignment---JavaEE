package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomer(Connection connection);
    boolean saveCustomer (CustomerDTO customerDTO , Connection connection);
    boolean updateCustomer (CustomerDTO customerDTO ,Connection connection);
    boolean deleteCustomer (String id,Connection connection);



}
