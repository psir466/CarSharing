package carsharing;

import java.security.interfaces.RSAPublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataBase {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static String DB_URL = "jdbc:h2:../task/src/carsharing/db/";

    private static Connection connection;

    public static Statement stmt;

    public static PreparedStatement preparedStatement;


    public static void createDb(String dbName) {

        DB_URL = DB_URL + dbName;

        String sql = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE) ;" +

                "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(100) NOT NULL UNIQUE, " +
                " COMPANY_ID INTEGER NOT NULL, " +
                "CONSTRAINT fk FOREIGN KEY (COMPANY_ID) " +
                "REFERENCES COMPANY(ID)) ;" +

                "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(100) NOT NULL UNIQUE, " +
                " RENTED_CAR_ID INTEGER DEFAULT NULL, " +
                "CONSTRAINT fk_cus FOREIGN KEY (RENTED_CAR_ID) " +
                "REFERENCES CAR(ID))";


        doStatCRTDB(sql);

    }

    public static void initId() {

        String sql = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1 ;" +
                "ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1";


        doStatCRTDB(sql);

    }


    private static void doStatCRTDB(String input) {

        crtConnection();


        try {

            //STEP 3: Execute a query

            stmt = connection.createStatement();

            String sql = input;

            stmt.executeUpdate(sql);

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

    }


    public static List<Company> ListComp() {

        String sql = "SELECT ID, NAME FROM COMPANY";


        return doListComp(sql);
    }

    private static List<Company> doListComp(String input) {

        List<Company> listCompany = new ArrayList<>();

        crtConnection();

        try {

            stmt = connection.createStatement();

            //STEP 3: Execute a query

            ResultSet rs = stmt.executeQuery(input);


            while (rs.next()) {

                Company c = new Company();

                String name = rs.getString("NAME");

                c.setName(name);

                int id = rs.getInt("ID");

                c.setId(id);

                listCompany.add(c);

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

        return listCompany;

    }

    public static Company crtCompData(String name) {

        String sql = "INSERT INTO COMPANY (NAME) VALUES(?)";

// en fait cela ne sert à rien il devrait y avoir une seule méthode !!!!!!!!!!!!!
        return doCrtCompData(sql, name);
    }


    private static Company doCrtCompData(String input, String name) {

        Company c = new Company(name);

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(input);

            //STEP 3: Execute a query

            preparedStatement.setString(1, c.getName());

            preparedStatement.executeUpdate();

            System.out.println("The company was created!");

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

        return c;

    }


    public static List<Car> ListCarByComp(Company company) {

        List<Car> listCarByCompany = new ArrayList<>();

        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ?";

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setInt(1, company.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Car car = new Car();

                String name = rs.getString("NAME");

                int id = rs.getInt("ID");

                car.setName(name);

                car.setId(id);

                car.setCompany(company);

                listCarByCompany.add(car);

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try


        return listCarByCompany;
    }


    public static Car crtCarData(String name, Company company) {

        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES(?, ?)";

// en fait cela ne sert à rien il devrait y avoir une seule méthode !!!!!!!!!!!!!
        Car car = new Car(name, company);

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, company.getId());

            preparedStatement.executeUpdate();

            System.out.println("The car was added!");

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

        return car;
    }


    public static void crtConnection() {

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection

            connection = DriverManager.getConnection(DB_URL);

            connection.setAutoCommit(true);

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }


    }

    public static void closeResource() {

        if (preparedStatement != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public static Customer crtCusData(String name) {

        String sql = "INSERT INTO CUSTOMER (NAME) VALUES(?)";

        Customer c = new Customer(name);

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setString(1, c.getName());

            preparedStatement.executeUpdate();

            System.out.println("The customer was created!");

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

        return c;

    }


    public static List<Customer> ListCust() {

        String sql = "SELECT ID, NAME FROM CUSTOMER";


        List<Customer> listCustomer = new ArrayList<>();

        crtConnection();

        try {

            stmt = connection.createStatement();

            //STEP 3: Execute a query

            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {

                Customer c = new Customer();

                String name = rs.getString("NAME");

                c.setName(name);

                int id = rs.getInt("ID");

                c.setId(id);

                listCustomer.add(c);

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try

        return listCustomer;

    }

    public static void updateCustomerRentCar(int idcar, int idcus) {

        Car car = findCarByID(idcar);

        Customer customer = findCustomerById(idcus);

        if(customer.getCar() == null) {

            customer.setCar(car);

            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ? ";

            crtConnection();

            try {

                preparedStatement = connection.prepareStatement(sql);

                //STEP 3: Execute a query

                preparedStatement.setInt(1, idcar);
                preparedStatement.setInt(2, idcus);

                preparedStatement.executeUpdate();

                System.out.println("You rented " + "'" +car.getName() + "'");

                // STEP 4: Clean-up environment
                closeResource();

            } catch (
                    SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                closeResource();
            } //end finally try

        }else{

            System.out.println("You 've already rented a car! ");

        }
    }

    public static Car findCarByID(int id) {

        Car car = null;

        String sql = "SELECT * FROM CAR WHERE ID = ?";

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                car = new Car();

                String name = rs.getString("NAME");

                int idcar = rs.getInt("ID");

                car.setName(name);

                car.setId(idcar);

                car.setCompany(findCompanybyId(rs.getInt("COMPANY_ID")));

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try


        return car;


    }

    public static Customer findCustomerById(int id) {

        Customer customer = null;

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                customer = new Customer();

                String name = rs.getString("NAME");

                int idcus = rs.getInt("ID");

                customer.setName(name);

                customer.setId(idcus);

                rs.getInt("RENTED_CAR_ID");
                if (!rs.wasNull()) {
                    customer.setCar(findCarByID(rs.getInt("RENTED_CAR_ID")));

                } else {

                    customer.setCar(null);
                }
            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try


        return customer;


    }


    public static Company findCompanybyId(int id) {

        Company company = null;

        String sql = "SELECT * FROM COMPANY WHERE ID = ?";

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                company = new Company();

                String name = rs.getString("NAME");

                int idcomp = rs.getInt("ID");

                company.setName(name);

                company.setId(idcomp);

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try


        return company;

    }


    public static void updateCustomerReturnCar(int idcus) {

        Customer customer = findCustomerById(idcus);

        if(customer.getCar() != null) {

            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = null WHERE ID = ? ";

            crtConnection();

            try {

                preparedStatement = connection.prepareStatement(sql);

                //STEP 3: Execute a query

                preparedStatement.setInt(1, idcus);

                preparedStatement.executeUpdate();


                System.out.println("You've returned a rented car!");

                // STEP 4: Clean-up environment
                closeResource();

            } catch (
                    SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                closeResource();
            } //end finally try

        }else{

            System.out.println("You didn't rent a car!");

        }
    }

    public static  boolean findRentedCarById(int id){

        boolean rentedCar = false;

        String sql = "SELECT * FROM CUSTOMER WHERE RENTED_CAR_ID = ?";

        crtConnection();

        try {

            preparedStatement = connection.prepareStatement(sql);

            //STEP 3: Execute a query

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                rentedCar = true;

            }

            // STEP 4: Clean-up environment
            closeResource();

        } catch (
                SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            closeResource();
        } //end finally try


        return rentedCar;

    }

}
