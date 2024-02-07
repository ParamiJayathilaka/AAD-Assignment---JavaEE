package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.ItemEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDAO);
    @Override
    public List<ItemDTO> getAllItem(Connection connection) {
        List<ItemEntity> itemEntityList = itemDAO.getAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO itemDTO;

        for (ItemEntity item : itemEntityList){
            itemDTO = new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice());
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO ,Connection connection) {
        ItemEntity itemEntity = new ItemEntity(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.save(itemEntity);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO , Connection connection) {
        ItemEntity itemEntity = new ItemEntity(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.update(itemEntity);

    }

    @Override
    public boolean deleteItem(String code ,Connection connection) {

        return itemDAO.delete(code);
    }
}
