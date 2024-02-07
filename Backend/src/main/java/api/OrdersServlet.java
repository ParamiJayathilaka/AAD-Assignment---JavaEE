package api;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.OrderBO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.OrderEntity;
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

@WebServlet(name = "orderServlet" , value = "/orders" ,initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/company"),
        @WebInitParam(name = "username", value = "root"),
        @WebInitParam(name = "password", value = "1234")
})
public class OrdersServlet extends HttpServlet {
    CustomerBO customerBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);
    OrderBO orderBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDERBO);
    String url;
    String username;
    String password;
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
//        OrderEntity order = jsonb.fromJson(req.getReader(), OrderEntity.class);
        OrderDTO orderDTO  = jsonb.fromJson(req.getReader(), OrderDTO.class);
        String oid = orderDTO.getOid();
        String date = orderDTO.getDate();
        String customerId = orderDTO.getCustomerId();
        System.out.println(orderDTO);

        try(Connection connection = source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

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


        } catch (SQLException e) {
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
