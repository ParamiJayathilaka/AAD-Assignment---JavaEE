package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.CustomerEntity;
import entity.ItemEntity;
import entity.OrderDetailsEntity;
import entity.OrderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDAO);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILSDAO);

    @Override
    public List<OrderDTO> getAllOrder(Connection connection) throws SQLException {
        List<OrderEntity> orderEntityList = orderDAO.getAll(connection);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        OrderDTO orderDTO;

        for (OrderEntity order : orderEntityList){
//            orderDTO = new OrderDTO(order.getOid(),order.getDate(),order.getCustomerId());
//            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO , Connection connection) throws SQLException {

        try{
            connection.setAutoCommit(false);
            OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerId());
            boolean isOrderSave = orderDAO.save(orderEntity, connection);

            if (isOrderSave){
                List<OrderDetailsEntity > orderDetailsEntities = new ArrayList<>();
                for (OrderDetailsDTO orderDetailsDTO: orderDTO.getOrderDetails()) {

                    OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDetailsDTO.getOid(),orderDetailsDTO.getCode(),orderDetailsDTO.getQty(),orderDetailsDTO.getUnitPrice());
                    orderDetailsEntities.add(orderDetailsEntity);
                }
                boolean isOrderDetailSave = orderDetailsDAO.save(orderDetailsEntities, connection);
                if (isOrderDetailSave){
                    connection.setAutoCommit(true);
                    return true;
                }
            }
        }catch (Exception e){
            connection.rollback();
            return false;
        }
        return false;
    }

    @Override
    public boolean updateOrder(OrderDTO orderDTO ,Connection connection) throws SQLException {
        try{
            connection.setAutoCommit(false);
            OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerId());
            boolean isOrderUpdate = orderDAO.update(orderEntity, connection);

            if (isOrderUpdate){
                List<OrderDetailsEntity > orderDetailsEntities = new ArrayList<>();
                for (OrderDetailsDTO orderDetailsDTO: orderDTO.getOrderDetails()) {

                    OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity(orderDetailsDTO.getOid(),orderDetailsDTO.getCode(),orderDetailsDTO.getQty(),orderDetailsDTO.getUnitPrice());
                    orderDetailsEntities.add(orderDetailsEntity);
                }
                boolean isOrderDetailSave = orderDetailsDAO.save(orderDetailsEntities, connection);
                if (isOrderDetailSave){
                    connection.setAutoCommit(true);
                    return true;
                }
            }
        }catch (Exception e){
            connection.rollback();
            return false;
        }
        return false;
//        OrderEntity orderEntity = new OrderEntity(orderDTO.getOid(), orderDTO.getDate(),orderDTO.getCustomerId());
//        return orderDAO.update(orderEntity,connection);
    }

    @Override
    public boolean deleteOrder(String oid , Connection connection) throws SQLException {

        return orderDAO.delete(oid,connection);
    }
}
