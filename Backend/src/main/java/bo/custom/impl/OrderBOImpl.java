package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dto.CustomerDTO;
import dto.OrderDTO;
import entity.CustomerEntity;
import entity.ItemEntity;
import entity.OrderEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    @Override
    public List<OrderDTO> getAllOrder(Connection connection) {
        List<OrderEntity> orderEntityList = orderDAO.getAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        OrderDTO orderDTO;

        for (OrderEntity order : orderEntityList){
            orderDTO = new OrderDTO(order.getOid(), order.getDate(), order.getCustomerId());
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO , Connection connection) {
        OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerId());
        return orderDAO.save(orderEntity);
    }

    @Override
    public boolean updateOrder(OrderDTO orderDTO ,Connection connection) {
        OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerId());
        return orderDAO.update(orderEntity);
    }

    @Override
    public boolean deleteOrder(String oid , Connection connection) {
        return orderDAO.delete(oid);
    }
}
