package api;

import bo.BOFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;
import entity.CustomerEntity;
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

@WebServlet(name = "customerServlet" , value = "/customer")
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO = BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);

    DataSource source;

    @Override
    public void init() {

        try {
            InitialContext initCtx = new InitialContext();
            source = (DataSource)initCtx.lookup("java:comp/env/jdbc/pos");

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");


//        List<CustomerEntity> customerEntityList = new ArrayList<>();
        try ( Connection connection = source.getConnection()) {

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String id = resultSet.getString(1);
//                String name = resultSet.getString(2);
//                String address = resultSet.getString(3);
//
//                CustomerEntity customer = new CustomerEntity(id, name, address);
//                customerEntityList.add(customer);
//
//            }
//
            List<CustomerDTO> allCustomer = customerBO.getAllCustomer(connection);

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allCustomer, resp.getWriter());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
//        CustomerEntity customer = jsonb.fromJson(req.getReader(), CustomerEntity.class);
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        String id = customerDTO.getId();
        String name = customerDTO.getName();
        String address = customerDTO.getAddress();

        try (Connection connection = source.getConnection()){

//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer(id , name , address) VALUES (?,?,?)");
//            preparedStatement.setString(1, id);
//            preparedStatement.setString(2, name);
//            preparedStatement.setString(3, address);
//
//            boolean isSaved = preparedStatement.executeUpdate() > 0;
            boolean isSaved = customerBO.saveCustomer(customerDTO, connection);
            if (isSaved){
                resp.getWriter().println("Customer Saved");
            }else {
                resp.getWriter().println("Customer Saved Fail");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsonb jsonb = JsonbBuilder.create();
        CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        String id = customer.getId();
        String name = customer.getName();
        String address = customer.getAddress();


        try(Connection connection = source.getConnection()) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);


//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=? ");
//            preparedStatement.setString(3, id);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, address);
//
//
//            boolean isUpdate = preparedStatement.executeUpdate() > 0;
            boolean isUpdate = customerBO.updateCustomer(customer, connection);
            if (isUpdate){
                resp.getWriter().println("Customer Update");
            }else {
                resp.getWriter().println("Customer Update Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try (Connection connection = source.getConnection()){
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(url, username, password);

//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM customer WHERE  id=?");
//            preparedStatement.setString(1, id);
            //            boolean isDeleted = preparedStatement.executeUpdate() > 0;


            boolean isDeleted = customerBO.deleteCustomer(id, connection);

            if (isDeleted){
                resp.getWriter().println("Customer Deleted");
            }else {
                resp.getWriter().println("Customer Deleted Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


