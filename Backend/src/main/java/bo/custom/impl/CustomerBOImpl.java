package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.CustomerEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    @Override
    public List<CustomerDTO> getAllCustomer(Connection connection) {
        List<CustomerEntity> customerEntityList = customerDAO.getAll(connection );
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        CustomerDTO customerDTO;

        for (CustomerEntity customer : customerEntityList){
            customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;

    }


    @Override
    public boolean saveCustomer(CustomerDTO customerDTO , Connection connection) throws SQLException {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress());
        return customerDAO.save(customerEntity , connection);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO ,Connection connection) {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress());
        return customerDAO.update(customerEntity,connection);
    }

    @Override
    public boolean deleteCustomer(String id ,Connection connection) {

        return customerDAO.delete(id , connection);
    }

}
