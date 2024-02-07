package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.ItemEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    @Override
    public List<ItemDTO> getAllItem(Connection connection) {
        List<ItemEntity> itemEntityList = itemDAO.getAll(connection);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO itemDTO;

        for (ItemEntity item : itemEntityList){
            itemDTO = new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO ,Connection connection) throws SQLException {
        ItemEntity itemEntity = new ItemEntity(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.save(itemEntity,connection);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO , Connection connection) {
        ItemEntity itemEntity = new ItemEntity(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.update(itemEntity,connection);

    }

    @Override
    public boolean deleteItem(String code ,Connection connection) {

        return itemDAO.delete(code,connection);
    }
}
