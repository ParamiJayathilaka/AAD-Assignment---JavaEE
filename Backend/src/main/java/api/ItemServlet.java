package api;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.ItemEntity;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "itemServlet" , value = "/item")
//,initParams = {
//        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
//        @WebInitParam(name = "username", value = "root"),
//        @WebInitParam(name = "password", value = "1234")
//})
public class ItemServlet extends HttpServlet {
//    String url;
//    String username;
//    String password;

    ItemBO itemBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEMBO);

    DataSource source;

    @Override
    public void init() throws ServletException {
//        url = getServletConfig().getInitParameter("url");
//        username = getServletConfig().getInitParameter("username");
//        password = getServletConfig().getInitParameter("password");

        try {
            InitialContext initCtx = new InitialContext();
            source = (DataSource)initCtx.lookup("java:comp/env/jdbc/pos");
        }catch (NamingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

//        List<ItemEntity> itemEntityList = new ArrayList<>();
        try( Connection connection = source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM item");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String code = resultSet.getString(1);
//                String description = resultSet.getString(2);
//                int qtyOnHand = resultSet.getInt(3);
//                double unitPrice = resultSet.getDouble(4);
//
//                ItemEntity item = new ItemEntity(code, description, qtyOnHand, unitPrice);
//                itemEntityList.add(item);
//
//            }

            List<ItemDTO> allItem = itemBO.getAllItem(connection);

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allItem, resp.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
//        ItemEntity item = jsonb.fromJson(req.getReader(), ItemEntity.class);
        ItemDTO itemDTO  = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = itemDTO.getCode();
        String description = itemDTO.getDescription();
        int qtyOnHand = itemDTO.getQtyOnHand();
        double unitPrice = itemDTO.getUnitPrice();


        try  (Connection connection = source.getConnection()){
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item(code , description , qtyOnHand ,unitPrice) VALUES (?,?,?,?)");
//            preparedStatement.setString(1, code);
//            preparedStatement.setString(2, description);
//            preparedStatement.setInt(3, qtyOnHand);
//            preparedStatement.setDouble(4, unitPrice);
//
//            boolean isSaved = preparedStatement.executeUpdate() > 0;
            boolean isSaved = itemBO.saveItem(itemDTO,connection);
            if (isSaved){
                resp.getWriter().println("Item Saved");
            }else {
                resp.getWriter().println("Item Saved Fail");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
//        ItemEntity item = jsonb.fromJson(req.getReader(), ItemEntity.class);
        ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
        String code = item.getCode();
        String description = item.getDescription();
        int qtyOnHand = item.getQtyOnHand();
        double unitPrice = item.getUnitPrice();


        try (Connection connection = source.getConnection()){
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=? , unitPrice=? WHERE code=? ");
//            preparedStatement.setString(4, code);
//            preparedStatement.setString(1, description);
//            preparedStatement.setInt(2, qtyOnHand);
//            preparedStatement.setDouble(3, unitPrice);


//            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            boolean isUpdate = itemBO.updateItem(item, connection);
            if (isUpdate){
                resp.getWriter().println("Item Update");
            }else {
                resp.getWriter().println("Item Update Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try(Connection connection = source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM item WHERE  code=?");
//            preparedStatement.setString(1, code);

//            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            boolean isDeleted = itemBO.deleteItem(code, connection);
            if (isDeleted){
                resp.getWriter().println("Item Deleted");
            }else {
                resp.getWriter().println("Item Deleted Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
