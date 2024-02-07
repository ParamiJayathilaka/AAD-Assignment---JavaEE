package api;

import entity.ItemEntity;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "itemServlet" , value = "/item" ,initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
        @WebInitParam(name = "username", value = "root"),
        @WebInitParam(name = "password", value = "1234")
})
public class ItemServlet extends HttpServlet {
    String url;
    String username;
    String password;

    @Override
    public void init() throws ServletException {
        url = getServletConfig().getInitParameter("url");
        username = getServletConfig().getInitParameter("username");
        password = getServletConfig().getInitParameter("password");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        List<ItemEntity> itemEntityList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String description = resultSet.getString(2);
                int qtyOnHand = resultSet.getInt(3);
                double unitPrice = resultSet.getDouble(4);

                ItemEntity item = new ItemEntity(code, description, qtyOnHand, unitPrice);
                itemEntityList.add(item);

            }

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(itemEntityList, resp.getWriter());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemEntity item = jsonb.fromJson(req.getReader(), ItemEntity.class);
        String code = item.getCode();
        String description = item.getDescription();
        int qtyOnHand = item.getQtyOnHand();
        double unitPrice = item.getUnitPrice();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item(code , description , qtyOnHand ,unitPrice) VALUES (?,?,?,?)");
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, qtyOnHand);
            preparedStatement.setDouble(4, unitPrice);

            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved){
                resp.getWriter().println("Item Saved");
            }else {
                resp.getWriter().println("Item Saved Fail");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        ItemEntity item = jsonb.fromJson(req.getReader(), ItemEntity.class);
        String code = item.getCode();
        String description = item.getDescription();
        int qtyOnHand = item.getQtyOnHand();
        double unitPrice = item.getUnitPrice();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET description=?, qtyOnHand=? , unitPrice=? WHERE code=? ");
            preparedStatement.setString(4, code);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, qtyOnHand);
            preparedStatement.setDouble(3, unitPrice);


            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            if (isUpdate){
                resp.getWriter().println("Item Update");
            }else {
                resp.getWriter().println("Item Update Fail");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM item WHERE  code=?");
            preparedStatement.setString(1, code);

            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted){
                resp.getWriter().println("Item Deleted");
            }else {
                resp.getWriter().println("Item Deleted Fail");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
