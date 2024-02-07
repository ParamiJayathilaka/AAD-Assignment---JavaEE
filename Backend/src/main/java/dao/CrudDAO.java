package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{

    boolean save (T dto , Connection connection) throws SQLException;

    boolean update (T dto , Connection connection);

    boolean delete (String id , Connection connection);

    List<T> getAll(Connection connection);


}
