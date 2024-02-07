package api;

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

@WebServlet(name = "orderServlet" , value = "/orders" ,initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
        @WebInitParam(name = "username", value = "root"),
        @WebInitParam(name = "password", value = "1234")
})
public class OrdersServlet extends HttpServlet {
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

        List<OrderEntity> orderEntityList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM orders");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String oid = resultSet.getString(1);
                String date = resultSet.getString(2);
                String customerId = resultSet.getString(3);

                OrderEntity order = new OrderEntity(oid, date, customerId);
                orderEntityList.add(order);

            }

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(orderEntityList, resp.getWriter());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        OrderEntity order = jsonb.fromJson(req.getReader(), OrderEntity.class);
        String oid = order.getOid();
        String date = order.getDate();
        String customerId = order.getCustomerId();
        System.out.println(order);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (oid , date , customerId ) VALUES (?,?,?)");
            preparedStatement.setString(1, oid);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, customerId);


            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved){
                resp.getWriter().println("Order Saved");
            }else {
                resp.getWriter().println("Order Saved Fail");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Jsonb jsonb = JsonbBuilder.create();
        OrderEntity order = jsonb.fromJson(req.getReader(), OrderEntity.class);
        String oid = order.getOid();
        String date = order.getDate();
        String customerId = order.getCustomerId();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET date=?, customerId=? WHERE oid=? ");
            preparedStatement.setString(3, oid);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, customerId);


            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            if (isUpdate){
                resp.getWriter().println("Order Update");
            }else {
                resp.getWriter().println("Order Update Fail");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String oid = req.getParameter("oid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM orders WHERE  oid=?");
            preparedStatement.setString(1, oid);

            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted){
                resp.getWriter().println("Order Deleted");
            }else {
                resp.getWriter().println("Order Deleted Fail");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
