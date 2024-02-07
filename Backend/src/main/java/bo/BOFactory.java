package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBOFactory(){return (boFactory==null) ? boFactory = new BOFactory() : boFactory;}

    public enum BOTypes {
        CUSTOMERBO, ITEMBO, ORDERBO
    }
    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMERBO:
                return (T) new CustomerBOImpl();
            case ITEMBO:
                return (T) new ItemBOImpl();
            case ORDERBO:
                return (T) new OrderBOImpl();
            default:
                return null;
        }
    }
}
