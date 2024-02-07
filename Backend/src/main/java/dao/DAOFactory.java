package dao;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMERDAO, ITEMDAO, ORDERDAO, ORDERDETAILSDAO
    }

    public <T extends SuperDAO> T getDAO (DAOFactory.DAOTypes daoTypes) {
        switch (daoTypes) {
            case CUSTOMERDAO:
                return (T) new CustomerDAOImpl();
            case ITEMDAO:
                return (T) new ItemDAOImpl();
            case ORDERDAO:
                return (T) new OrderDAOImpl();

            default:
                return null;
        }

    }
}
